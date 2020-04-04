package org.songdan.swak.rule.config;

import org.songdan.swak.annotations.SwakMethod;
import org.songdan.swak.ruduce.Reducer;
import org.songdan.swak.rule.Conflict;
import org.songdan.swak.rule.GroupFamily;
import org.songdan.swak.rule.RuleGroup;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: Songdan
 * @create: 2020-04-04 23:06
 **/
public class Factory implements BeanFactoryAware {

    private BeanFactory beanFactory;

    public GroupFamily build(ConfigFamily configFamily) {

        List<RuleGroup> groupList = configFamily.getGroupConfigs().stream().map(groupConfig -> {
            RuleGroup ruleGroup = new RuleGroup();
            ruleGroup.setName(groupConfig.getName());
            ruleGroup.setTags(groupConfig.getTags());
            List<ConflictConfig> conflictConfigs = groupConfig.getConflictConfigs();
            ruleGroup.setConflictList(conflictConfigs.stream().map(this::build).collect(Collectors.toList()));
            return ruleGroup;
        }).collect(Collectors.toList());
        GroupFamily family = new GroupFamily();
        family.setRuleGroups(groupList);
        return family;

    }

    public Conflict build(ConflictConfig conflictConfig){
        Conflict conflict = new Conflict();
        conflict.setReducer(beanFactory.getBean(conflictConfig.getReducer(), Reducer.class));
        try {
            conflict.setSwakCls(Class.forName(conflictConfig.getClassName()));
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
        Method[] methods = conflict.getSwakCls().getMethods();
        Optional<Method> methodOp = Stream.of(methods).filter(method -> method.getName().equals(conflictConfig.getMethod()))
                .filter(method -> method.getAnnotation(SwakMethod.class) != null)
                .findFirst();
        conflict.setSwakMethod(methodOp.orElse(null));
        conflict.setTags(Stream.of(conflictConfig.getSequence().split(",")).collect(Collectors.toList()));
        return conflict;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
