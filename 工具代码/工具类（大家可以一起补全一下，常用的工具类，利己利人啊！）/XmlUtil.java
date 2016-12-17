package com.itheima.ebs.utils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.itheima.ebs.factory.Bean;

public class XmlUtil {

	/**
	 * 将数据解析成bean
	 * @author lt
	 * @param xmlIs
	 * @return
	 */
	public static Map<String, Bean> parserBeanXml(InputStream xmlIs) {
		try {
			//0提供缓冲区域
			Map<String, Bean> data = new HashMap<String, Bean>();
			
			
			//1解析文件，并获得Document
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(xmlIs);
			
			//2 获得根元素
			Element rootElement = document.getRootElement();
			
			//3获得所有的bean元素
			List<Element> allBeanElement = rootElement.elements();
			
			//4遍历
			for (Element beanElement : allBeanElement) {
				//5 将解析的结果封装到bean中
				// 5.1 bean名称 
				String beanName = beanElement.attributeValue("name");
				// 5.2 bean实现类
				String beanClass = beanElement.attributeValue("class");
				
				// 5.3 封装到Bean对象
				Bean bean = new Bean(beanName,beanClass);
				
				data.put(beanName, bean);
			}
			return data;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
