package org.songdan.swak.rule;

import java.util.List;
import java.util.Set;

/**
 * @author: Songdan
 * @create: 2020-04-04 22:56
 **/
public class RuleGroup {

    private String name;

    private List<Conflict> conflictList;

    private Set<String> tags;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Conflict> getConflictList() {
        return conflictList;
    }

    public void setConflictList(List<Conflict> conflictList) {
        this.conflictList = conflictList;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }
}
