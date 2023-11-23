package com.example.e2ekernelengine.crawler.util;

import org.springframework.context.ApplicationContext;

public class BeanUtil {
	
	public static Object getBean(Class<?> classType) {
		ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
		return applicationContext.getBean(classType);
	}
}
