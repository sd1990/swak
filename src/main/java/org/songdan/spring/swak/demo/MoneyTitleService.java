package org.songdan.spring.swak.demo;

import org.songdan.spring.swak.annotations.SwakTag;
import org.springframework.stereotype.Component;

/**
 * 标题用金额表示
 *
 * @author: Songdan
 * @create: 2020-03-28 16:16
 **/
@SwakTag(tags = {"moneyTag"})
@Component
public class MoneyTitleService implements TitleService {

    @Override
    public String fetchTitle() {
        return "商品是58元";
    }
}
