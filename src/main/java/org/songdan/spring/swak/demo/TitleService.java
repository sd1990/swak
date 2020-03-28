package org.songdan.spring.swak.demo;

import org.songdan.spring.swak.annotations.SwakInterface;
import org.songdan.spring.swak.annotations.SwakMethod;

@SwakInterface(desc = "标题展示服务")
public interface TitleService {

    @SwakMethod
    String fetchTitle();

}
