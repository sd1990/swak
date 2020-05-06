package org.songdan.swak.rule;

import org.springframework.core.io.Resource;

import java.io.IOException;

public interface GroupParser {

    GroupFamily parse(Resource resource) throws IOException;

}
