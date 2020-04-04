package org.songdan.swak.ruduce;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;

/**
 * 以字符串的形式拼接的reducer
 * @author: Songdan
 * @create: 2020-04-04 22:11
 **/
@Component
public class ToStringJoiner implements Reducer<String> {

    @Override
    public String reduce(List<String> list) {
        return list.stream().filter(Objects::nonNull).reduce((s, s2) -> s+","+s2).orElse("");
    }
}