package org.songdan.swak.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * xml转换工具类
 * 
 * @author Songdan
 */
public final class JaxbUtil {

    /**
     * 防止被构建
     */
    private JaxbUtil() {
        throw new IllegalAccessError();
    }

    /**
     * JavaBean转换成xml 默认编码UTF-8 不输入头信息
     * 
     * @param obj
     *            JavaBean
     * @return xml字符串
     */
    public static String convertToXml(Object obj, Marshaller.Listener... listeners) {
        return convertToXml(obj, false, listeners);
    }

    /**
     * JavaBean转换成xml 默认编码UTF-8
     * 
     * @param obj
     *            JavaBean
     * @param withHeader
     *            是否输出头部信息
     * @return xml字符串
     */
    public static String convertToXml(Object obj, boolean withHeader, Marshaller.Listener... listeners) {
        if (withHeader) {
            return convertToXmlWithHeader(obj, "UTF-8", listeners);
        }
        else {
            return convertToXmlWithoutHeader(obj, "UTF-8", listeners);
        }
    }

    /**
     * JavaBean转换成xml
     * 
     * @param obj
     *            JavaBean对象
     * @param encoding
     *            编码格式
     * @return xml字符串
     */
    private static String convertToXmlWithHeader(Object obj, String encoding, Marshaller.Listener... listeners) {
        String result = null;
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller jaxbMarshaller = context.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            // 用来指定将放置在已编组 XML 输出中的 xsi:schemaLocation 属性值的属性名称
            String schemaLocation = "http://www.chinatax.gov.cn/tirip/dataspec/interfaces.xsd";
            jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, schemaLocation);
            for (Marshaller.Listener listener : listeners) {
                jaxbMarshaller.setListener(listener);
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
            XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(baos, encoding);
            xmlStreamWriter.writeStartDocument(encoding, "1.0");
            jaxbMarshaller.marshal(obj, xmlStreamWriter);
            xmlStreamWriter.writeEndDocument();
            xmlStreamWriter.close();
            return new String(baos.toByteArray());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * JavaBean转换成xml
     * 
     * @param obj
     *            JavaBean对象
     * @param encoding
     *            编码格式
     * @return xml字符串
     */
    private static String convertToXmlWithoutHeader(Object obj, String encoding, Marshaller.Listener... listeners) {
        String result = null;
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);

            for (Marshaller.Listener listener : listeners) {
                marshaller.setListener(listener);
            }

            StringWriter writer = new StringWriter();
            marshaller.marshal(obj, writer);
            result = writer.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * xml转换成JavaBean
     * 
     * @param xml
     *            xml报文
     * @param clazz
     *            转换目标类
     * @return JavaBean
     */
    @SuppressWarnings("unchecked")
    public static <T> T convertToJavaBean(String xml, Class<T> clazz) {
        T bean = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            bean = (T) unmarshaller.unmarshal(new StringReader(xml));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }
}
