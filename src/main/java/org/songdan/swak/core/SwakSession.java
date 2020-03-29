package org.songdan.swak.core;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * swak回话上下文
 *
 * @author: Songdan
 * @create: 2020-03-28 17:12
 **/
public class SwakSession {

    /**
     * 上下文中的tag信息
     */
    private static ThreadLocal<Set<String>> tagListTl = ThreadLocal.withInitial(LinkedHashSet::new);

    public static boolean putTag(String tag) {
        return tagListTl.get().add(tag);
    }

    public static Set<String> getTags() {
        return tagListTl.get();
    }

    public static void clearTags() {
        tagListTl.remove();
    }

}
