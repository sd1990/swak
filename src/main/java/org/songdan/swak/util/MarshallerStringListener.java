package org.songdan.swak.util;

import javax.xml.bind.Marshaller;
import java.lang.reflect.Field;

/**
 * 字符串NULL监听器
 * 
 * @author Songdan
 */
public class MarshallerStringListener extends Marshaller.Listener {

    /** 默认值 */
    public static final String BLANK_CHAR = "";

    @Override
    public void beforeMarshal(Object source) {
        super.beforeMarshal(source);
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            try {
                if (f.getType() == String.class && f.get(source) == null) {
                    f.set(source, BLANK_CHAR);
                }
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
