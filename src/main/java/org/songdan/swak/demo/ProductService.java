package org.songdan.swak.demo;

import org.songdan.swak.annotations.SwakSessionAop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: Songdan
 * @create: 2020-03-28 16:20
 **/
@Component
public class ProductService {

    @Autowired
    private TitleService titleService;

    @SwakSessionAop
    public String getProductTitle() {
        return titleService.fetchTitle();
    }

}
