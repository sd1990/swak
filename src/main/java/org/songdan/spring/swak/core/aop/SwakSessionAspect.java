package org.songdan.spring.swak.core.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.songdan.spring.swak.core.SwakSession;
import org.springframework.stereotype.Component;

/**
 * @author: Songdan
 * @create: 2020-03-28 17:16
 **/
@Aspect
@Component
public class SwakSessionAspect {

    @Pointcut("@annotation(org.songdan.spring.swak.annotations.SwakSessionAop)")
    public void checkPoint() {
    }

    @Around("checkPoint()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        SwakSession.putTag("moneyTag");
        SwakSession.putTag("activityTag");
        try {
            return joinPoint.proceed();
        }finally {
            SwakSession.clearTags();
        }
    }

}
