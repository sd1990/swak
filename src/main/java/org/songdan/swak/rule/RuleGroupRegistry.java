package org.songdan.swak.rule;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: Songdan
 * @create: 2020-04-04 22:28
 **/
@Component
public class RuleGroupRegistry implements InitializingBean {

    @Autowired
    private GroupParser groupParser;

    private String fileName = "rule.xml";

    private Map<String, RuleGroup> ruleGroupMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        GroupFamily groupFamily = groupParser.parse(new ClassPathResource(fileName));
        ruleGroupMap.putAll(groupFamily.getRuleGroups().stream().collect(Collectors.toMap(RuleGroup::getName, ruleGroup -> ruleGroup)));
    }

    public RuleGroup getGroup(String name) {
        return ruleGroupMap.get(name);
    }
}
