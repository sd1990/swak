package org.songdan.swak.ruduce;

import java.util.List;

/**
 * 策略的规约关系
 */
public interface Reducer<T> {

    /**
     * 对集合进行规约
     * @param list
     * @return
     */
    T reduce(List<T> list);

}
