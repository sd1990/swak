package org.songdan.spring.swak.annotations;

import java.lang.annotation.*;

/**
 * 业务标签
 *
 * @author: Songdan
 * @create: 2020-03-28 16:11
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface SwakTag {

    /**
     * 业务tag
     */
    String[] tags();

}
