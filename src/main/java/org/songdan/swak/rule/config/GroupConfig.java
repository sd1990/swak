package org.songdan.swak.rule.config;

import java.util.List;
import java.util.Set;

/**
 * 一组规则的配置
 *
 * @author: Songdan
 * @create: 2020-04-04 22:25
 **/
public class GroupConfig {

    private String name;

    private List<ConflictConfig> conflictConfigs;

    private Set<String> tagSet;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ConflictConfig> getConflictConfigs() {
        return conflictConfigs;
    }

    public void setConflictConfigs(List<ConflictConfig> conflictConfigs) {
        this.conflictConfigs = conflictConfigs;
    }

    public Set<String> getTags() {
        return tagSet;
    }

    public void setTagSet(Set<String> tagSet) {
        this.tagSet = tagSet;
    }
}
