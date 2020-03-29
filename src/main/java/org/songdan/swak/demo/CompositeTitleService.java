package org.songdan.swak.demo;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: Songdan
 * @create: 2020-03-28 16:27
 **/
//@Component("titleService")
public class CompositeTitleService implements TitleService {
/*
    public CompositeTitleService(List<TitleService> titleServiceList) {
        this.titleServiceList = titleServiceList;
    }*/

    @Autowired
    private List<TitleService> titleServiceList;

    @Override
    public String fetchTitle() {
        StringBuilder title = new StringBuilder();
        for (TitleService titleService : titleServiceList) {
            title.append(titleService.fetchTitle());
            title.append(",");
        }
        return title.substring(0,title.length()-1);
    }
}
