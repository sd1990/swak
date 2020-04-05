package org.songdan.swak.ruduce;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * 策略的规约关系
 */
public interface Reducer<T> {

    /**
     * 对集合进行规约
     * @param list
     * @return
     */
    T reduce(List<Callable<T>> list);

}
