package org.songdan.swak.annotations;

import java.lang.annotation.*;

/**
 * 声明需要被swak框架处理的方法
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface SwakMethod {
}
