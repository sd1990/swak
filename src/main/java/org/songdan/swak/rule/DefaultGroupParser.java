package org.songdan.swak.rule;

import org.songdan.swak.demo.TitleService;
import org.songdan.swak.ruduce.FirstNotNull;
import org.songdan.swak.ruduce.ToStringJoiner;
import org.songdan.swak.rule.config.ConfigFamily;
import org.songdan.swak.rule.config.Factory;
import org.songdan.swak.util.JaxbUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * xml的配置解析
 *
 * @author: Songdan
 * @create: 2020-04-04 22:41
 **/
@Component
public class DefaultGroupParser implements GroupParser {

    @Override
    public GroupFamily parse(Resource resource) throws IOException {
        GroupFamily groupFamily = new GroupFamily();
        RuleGroup ruleGroup = new RuleGroup();
        ruleGroup.setName("firstGroup");
        Set<String> tags = new HashSet<String>();
        tags.add("moneyTag");
        tags.add("activityTag");
        ruleGroup.setTags(tags);
        Conflict conflict = new Conflict();
        List<String> sequenceSet = new ArrayList<String>();
        sequenceSet.add("activityTag");
        sequenceSet.add("moneyTag");
        conflict.setTags(sequenceSet);
        conflict.setSwakCls(TitleService.class);
        try {
            conflict.setSwakMethod(TitleService.class.getMethod("fetchTitle"));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        conflict.setReducer(new FirstNotNull());
        List<Conflict> conflicts = new ArrayList<>();
        conflicts.add(conflict);
        ruleGroup.setConflictList(conflicts);
        List<RuleGroup> groupList = new ArrayList<>();
        groupList.add(ruleGroup);
        groupFamily.setRuleGroups(groupList);
        return groupFamily;
    }


}
