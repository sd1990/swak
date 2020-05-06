package org.songdan.swak.core.spring;

import org.songdan.swak.annotations.SwakMethod;
import org.songdan.swak.annotations.SwakTag;
import org.songdan.swak.core.SwakSession;
import org.songdan.swak.rule.Conflict;
import org.songdan.swak.rule.RuleGroup;
import org.songdan.swak.rule.RuleGroupRegistry;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

/**
 * 生成组合代理对象
 *
 * @author: Songdan
 * @create: 2020-03-28 22:13
 **/
public class SwakProxyFactoryBean<T> implements FactoryBean<T>, InitializingBean,BeanFactoryAware {

    private Class<T> proxyClass;

    private List<String> instancesList;

    private Map<String, T> instanceMap;

    private BeanFactory beanFactory;

    private RuleGroupRegistry ruleGroupRegistry;

    @Override
    public T getObject() throws Exception {
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{proxyClass}, new InvocationHandler() {

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                SwakMethod annotation = method.getAnnotation(SwakMethod.class);
                if (method.getDeclaringClass().equals(Object.class)) {
                    return method.invoke(instancesList, args);
                }
                if (annotation == null) {
                    return method.invoke(instancesList.get(0), args);
                }
                Set<String> sessionTags = SwakSession.getTags();
                if (sessionTags.size()==1) {
                    T t = instanceMap.get(sessionTags.iterator().next());
                    return method.invoke(t, args);
                }
                String groupName = SwakSession.getGroupName();
                RuleGroup group = ruleGroupRegistry.getGroup(groupName);
                List<Conflict> conflictList = group.getConflictList();
                Optional<Conflict> optional = conflictList.stream()
                        .filter(conflict -> conflict.getSwakCls().equals(proxyClass))
                        .filter(conflict -> conflict.getSwakMethod().equals(method))
                        .findFirst();
                if (optional.isPresent()) {
                    Conflict conflict = optional.get();
                    List<String> sequenceTags = conflict.getTags();
                    List<Callable<Object>> resultList = sequenceTags.stream()
                            .filter(tag -> sessionTags.contains(tag))
                            .map(tag -> instanceMap.get(tag))
                            .filter(Objects::nonNull)
                            .map(t -> (Callable<Object>) () -> method.invoke(t, args))
                            .collect(Collectors.toList());
                    return conflict.getReducer().reduce(resultList);
                }
                throw new IllegalStateException("swak配置出错");
            }
        });
    }

    @Override
    public Class<?> getObjectType() {
        return proxyClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, T> instanceMap = new HashMap<>();
        for (String name : instancesList) {
            T t = beanFactory.getBean(name, proxyClass);
            SwakTag swakTag = t.getClass().getAnnotation(SwakTag.class);
            for (String tag : swakTag.tags()) {
                instanceMap.put(tag, t);
            }
        }
        this.instanceMap = instanceMap;
        ruleGroupRegistry = beanFactory.getBean(RuleGroupRegistry.class);
    }

    public Class<T> getProxyClass() {
        return proxyClass;
    }

    public void setProxyClass(Class<T> proxyClass) {
        this.proxyClass = proxyClass;
    }


    public List<String> getInstancesList() {
        return instancesList;
    }

    public void setInstancesList(List<String> instancesList) {
        this.instancesList = instancesList;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
