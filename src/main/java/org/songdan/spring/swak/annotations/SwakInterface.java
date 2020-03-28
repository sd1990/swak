package org.songdan.spring.swak.annotations;

import java.lang.annotation.*;

/**
 * 标示某一个接口需要被swak框架处理
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface SwakInterface {

    /**
     * 接口描述
     * @return
     */
    String desc();

}
