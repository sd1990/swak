package org.songdan.swak.demo;

import org.songdan.swak.annotations.SwakInterface;
import org.songdan.swak.annotations.SwakMethod;

@SwakInterface(desc = "标题展示服务")
public interface TitleService {

    @SwakMethod
    String fetchTitle();

}
