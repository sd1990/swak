package org.songdan.swak.core.spring;

import org.songdan.swak.annotations.SwakInterface;
import org.songdan.swak.annotations.SwakTag;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 注册所有swak相关的bean，并为swakInterface生成动态代理对象
 *
 * @author: Songdan
 * @create: 2020-03-28 17:21
 **/
@Component
public class SwakBeanFactoryPostProcessor implements BeanDefinitionRegistryPostProcessor {


    private Map<Class<?>, Set<BeanDefinition>> swakInstancesMap = new ConcurrentHashMap<>();

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        /*
        1. 检测出来注册的bean中实现的接口里面有@SwakInterface注解
        2. 对这样bean动态创建一个SwakFactoryBean的BeanDefinition然后注册到BeanFactory
         */
        String[] beanDefinitionNames = registry.getBeanDefinitionNames();
        Map<Class, List<BeanDefinition>> map = new HashMap<>();
        Map<BeanDefinition, String> beanDefinitionMap = new HashMap<>();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = registry.getBeanDefinition(beanDefinitionName);
            beanDefinitionMap.put(beanDefinition,beanDefinitionName);
            String beanClassName = beanDefinition.getBeanClassName();
            Class<?> cls;
            try {
                cls = Class.forName(beanClassName);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException(e);
            }
            SwakTag swakTag = cls.getAnnotation(SwakTag.class);
            if (swakTag == null) {
                continue;
            }
            Class<?>[] interfaces = cls.getInterfaces();
            for (Class<?> anInterface : interfaces) {
                SwakInterface swakInterface = anInterface.getAnnotation(SwakInterface.class);
                if (swakInterface != null) {
                    List<BeanDefinition> list = map.getOrDefault(anInterface, new ArrayList<>());
                    list.add(beanDefinition);
                    map.put(anInterface, list);
                }
            }
        }

        for (Map.Entry<Class, List<BeanDefinition>> entry : map.entrySet()) {
            GenericBeanDefinition definition = new GenericBeanDefinition();
            definition.setBeanClass(SwakProxyFactoryBean.class);
            definition.setPrimary(true);
            definition.getPropertyValues().add("proxyClass", entry.getKey().getName());
            List<String> beanNameList = entry.getValue().stream().map(beanDefinitionMap::get).collect(Collectors.toList());
            definition.getPropertyValues().add("instancesList", beanNameList);
            String simpleName = entry.getKey().getSimpleName();
            registry.registerBeanDefinition(simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1),definition);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
