package org.songdan.swak.ruduce;

import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * @author: Songdan
 * @create: 2020-04-04 22:11
 **/
@Component
public class FirstNotNull<T> implements Reducer<T> {

    @Override
    public T reduce(List<Callable<T>> list) {
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
        }).findFirst().orElseGet(new Supplier<T>() {
            @Override
            public T get() {
                System.out.println("nothing to be reduced");
                return null;
            }
        });
    }
}
