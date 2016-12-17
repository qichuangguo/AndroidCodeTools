package com.itheima.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.itheima.domain.User;

/**
 * dom4j 工具类
 * @author lt
 */
public class XmlUtils {
	
	private static String path = "D:/java/tomcat/apache-tomcat-7.0.42/webapps/day13/WEB-INF/db/usersdb.xml";
	
	/**
	 * 设置路径
	 * @author lt
	 * @param path
	 */
	public static void setPath(String path){
		XmlUtils.path = path;
	}

	/**
	 * 获得document对象
	 * @author lt
	 * @return
	 * @throws DocumentException 
	 */
	public static Document getDocument() throws DocumentException{
		SAXReader saxReader = new SAXReader();
		return saxReader.read(new File(path));
	}

	/**
	 * 将元素中的数据封装到javabean
	 * @author lt
	 * @param ele
	 * @param bean
	 * @return
	 * @throws Exception 
	 */
	public static Object addElementToBean(Element ele, Class beanClass) throws  Exception {
		//#1 创建实例
		Object beanObj = beanClass.newInstance();
		
		//1 处理属性 -- 获得所有属性 id
		List<Attribute> attrList = ele.attributes();
		for (Attribute attribute : attrList) {
			// 获得属性名称 id
			String attrName = attribute.getName();
			// 获得值
			String attrValue = attribute.getValue();
			// 封装到javabean
			BeanUtils.setProperty(beanObj,attrName,attrValue);
		}
		
		//2处理孩子
		List<Element> eleList = ele.elements();
		for (Element childElement : eleList) {
			//元素名称
			String eleName = childElement.getName();
			//元素文本内容
			String eleText= childElement.getText();
			// 封装到javabean
			BeanUtils.setProperty(beanObj,eleName,eleText);
		}
		return beanObj;
	}

	/**
	 * 回写，保存document
	 * @author lt
	 * @param document
	 * @throws IOException 
	 */
	public static void saveXml(Document document) throws IOException {
		FileOutputStream out = new FileOutputStream(new File(path));
		XMLWriter xmlWriter = new XMLWriter(out,OutputFormat.createPrettyPrint());
		xmlWriter.write(document);
		xmlWriter.close();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
