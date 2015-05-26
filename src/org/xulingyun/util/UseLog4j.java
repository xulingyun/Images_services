package org.xulingyun.util;

import org.apache.log4j.Logger;

public class UseLog4j {

	public static void debug(Class<?> clazz, String message) {
		Logger logger = Logger.getLogger(clazz.getName());
		logger.debug(message);
	}

	public static void warm(Class<?> clazz, String message) {
		Logger logger = Logger.getLogger(clazz.getName());
		logger.warn(message);
	}

	public static void info(Class<?> clazz, String message) {
		Logger logger = Logger.getLogger(clazz.getName());
		logger.info(message);
	}

	public static void error(Class<?> clazz, String message) {
		Logger logger = Logger.getLogger(clazz.getName());
		logger.error(message);
	}

	public static void fatal(Class<?> clazz, String message) {
		Logger logger = Logger.getLogger(clazz.getName());
		logger.fatal(message);
	}

}
