package org.songdan.spring.swak.annotations;

import java.lang.annotation.*;

/**
 * swak上下文处理的aop
 * @author: Songdan
 * @create: 2020-03-28 17:06
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SwakSessionAop {
}
