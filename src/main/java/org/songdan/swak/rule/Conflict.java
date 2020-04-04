package org.songdan.swak.rule;

import org.songdan.swak.ruduce.Reducer;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author: Songdan
 * @create: 2020-04-04 22:57
 **/
public class Conflict {

    private Class swakCls;

    private Method swakMethod;

    private Reducer reducer;

    private List<String> tags;

    public Class getSwakCls() {
        return swakCls;
    }

    public void setSwakCls(Class swakCls) {
        this.swakCls = swakCls;
    }

    public Method getSwakMethod() {
        return swakMethod;
    }

    public void setSwakMethod(Method swakMethod) {
        this.swakMethod = swakMethod;
    }

    public Reducer getReducer() {
        return reducer;
    }

    public void setReducer(Reducer reducer) {
        this.reducer = reducer;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
