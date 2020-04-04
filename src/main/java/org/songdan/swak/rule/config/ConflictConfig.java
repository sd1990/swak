package org.songdan.swak.rule.config;

/**
 * tag冲突解决的配置
 *
 * @author: Songdan
 * @create: 2020-04-04 22:26
 **/
public class ConflictConfig {

    private String className;

    private String method;

    private String reducer;

    private String sequence;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getReducer() {
        return reducer;
    }

    public void setReducer(String reducer) {
        this.reducer = reducer;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
}
