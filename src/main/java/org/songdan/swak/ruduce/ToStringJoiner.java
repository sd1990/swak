package org.songdan.swak.ruduce;

import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * 以字符串的形式拼接的reducer
 * @author: Songdan
 * @create: 2020-04-04 22:11
 **/
@Component
public class ToStringJoiner implements Reducer<String> {

    @Override
    public String reduce(List<Callable<String>> list) {
        return list.stream().filter(Objects::nonNull).map(call->{
            try {
                return call.call();
            } catch (Exception e) {
                if (e instanceof InvocationTargetException) {
                    throw new RuntimeException(e.getCause());
                } else {
                    throw new RuntimeException(e);
                }
            }
        }).reduce((s, s2) -> s+","+s2).orElse("");
    }
}
