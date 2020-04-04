package org.songdan.swak.rule;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

public interface GroupParser {

    GroupFamily parse(Resource resource) throws IOException;

}
