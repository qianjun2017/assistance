/**
 * 
 */
package com.cc.common.tools;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * @author Administrator
 *
 */
public class XmlTools {

	/**
	 * xml字符串转换成object
	 * @param xml
	 * @param clazz
	 * @return
	 */
	public static <T> T toObject(String xml, Class<T> clazz){
		if(StringTools.isNullOrNone(xml)){
			return null;
		}
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			T t = (T)unmarshaller.unmarshal(new StringReader(xml));
			return t;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * object转换成xml字符串
	 * @param clazz
	 * @param object
	 * @return
	 */
	public static <T> String toXml(Class<T> clazz, Object object){
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			Writer writer = new StringWriter();
			marshaller.marshal(object, writer);
			return writer.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
}
