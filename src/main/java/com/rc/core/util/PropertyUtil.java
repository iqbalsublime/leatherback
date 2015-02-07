package com.rc.core.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
	public static Properties getProperties(String propertyFileName) throws IOException {
		Properties properties = new Properties();
		InputStream inputStream = PropertyUtil.class.getClassLoader().getResourceAsStream(propertyFileName);
		if (null == inputStream) {
			throw new FileNotFoundException("Property file '" + propertyFileName + "' not found in the classpath");
		}
		properties.load(inputStream);

		return properties;
	}
}
