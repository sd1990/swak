package org.songdan.swak.rule;

import org.songdan.swak.rule.config.ConfigFamily;
import org.songdan.swak.rule.config.Factory;
import org.songdan.swak.util.JaxbUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * xml的配置解析
 *
 * @author: Songdan
 * @create: 2020-04-04 22:41
 **/
//@Component
public class XmlGroupParser implements GroupParser {

    @Autowired
    private Factory factory;

    @Override
    public GroupFamily parse(Resource resource) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(resource.getInputStream())));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        ConfigFamily groupConfig = JaxbUtil.convertToJavaBean(builder.toString(), ConfigFamily.class);
        return factory.build(groupConfig);
    }


}
