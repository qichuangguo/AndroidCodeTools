package com.ithm.lotteryhm30.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.ithm.lotteryhm30.engine.UserEngine;

/**
 * 工厂
 * 
 * @author l
 * 
 */
public class BeanFactory {
	// 依据配置文件加载需要的内容
	private static Properties properties;
	static {
		properties = new Properties();
		InputStream is = BeanFactory.class.getClassLoader().getResourceAsStream("bean.properties");
		try {
			properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取制定接口的实现类对象
	 * @param clazz
	 * @return
	 */
	public static<T> T getImpl(Class<T> clazz) {
		String key = clazz.getSimpleName();
		String className = properties.getProperty(key);
		try {
			return (T) Class.forName(className).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
