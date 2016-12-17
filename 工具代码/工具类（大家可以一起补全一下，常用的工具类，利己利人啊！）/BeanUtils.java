package com.itheima.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;

public class BeanUtils {

	/**
	 * 将表单提交的数据，自动的封装到指定的javabean对象中
	 * @author lt
	 * @param bean ，javabean实例
	 * @param parameterMap ，所有数据
	 */
	public static void populate(Object bean, Map<String, String[]> parameterMap) {
		//使用反射进行数据处理，内省Introspector
		try {
			// 1 通过内省获得javabean的描述信息对象
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass(),Object.class);
			// 2 获得javabean的所有的属性描述对象
			PropertyDescriptor[] allPD = beanInfo.getPropertyDescriptors();
			// 3 遍历所有的属性描述对象
			for(PropertyDescriptor pd : allPD){
				// 4 获得javabean当前属性的修改器 ，获得setter方法
				System.out.println(pd.getName());
				Method writeMethod = pd.getWriteMethod();
				Method readMethod = pd.getReadMethod();
				Class returnClass = readMethod.getReturnType();
				// 5 判断 有setter
				if(writeMethod != null){
					//6 通过属性名称获得表单中对应数据
					String[] values = parameterMap.get(pd.getName());
					// 6.1忽略没有数据内容
					if(values == null) {
						continue;
					}
					/*
					if(values != null){
						if(values.length > 1){ //数组
							writeMehotd.invoke(bean, (Object)values);
						} else { //一个值
							//7 执行
							writeMehotd.invoke(bean, values[0]);
						}
					}
					*/
					Object val = values[0];	//默认使用第一个值
					if(returnClass == Integer.class){
						val = Integer.parseInt(values[0]);	//将第一个值修改成整形
					}
					if(returnClass == String[].class){	//字符串
						val = values;
					}
					writeMethod.invoke(bean, val);
					
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 给指定javabean，的指定属性，封装值
	 * @author lt
	 * @param bean
	 * @param property
	 * @param propertyValue
	 * @throws Exception 
	 */
	public static void setProperty(Object bean, String property,
			Object propertyValue) throws Exception {
		PropertyDescriptor pd = new PropertyDescriptor(property,bean.getClass());
		//获得写方法
		Method writerMethod = pd.getWriteMethod();
		//获得读方法getter
		Method readMethod = pd.getReadMethod();
		// * 获得类型
		Class returnClass = readMethod.getReturnType();
		if(writerMethod != null){
			/*if(propertyValue instanceof Object[]){
				Object[] objArr = (Object[]) propertyValue;
				writerMethod.invoke(bean, (Object)propertyValue);
			} else {
			}*/
			
			/*
			String str = (String) propertyValue; //jack ,2,3
			String arr[] = str.split(",");
			if(arr.length > 1){
				propertyValue = arr;
			}
			*/
			if(returnClass == Integer.class){
				propertyValue = Integer.parseInt((String)propertyValue);
			}
			if(returnClass == String[].class){
				String arr[] = ((String)propertyValue).split(",");
				propertyValue = arr;
			}
			
			writerMethod.invoke(bean, propertyValue);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
