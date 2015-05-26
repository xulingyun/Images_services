package org.xulingyun.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingletonThreadPool{
	private static ExecutorService  uniqueInstance = null;
	private SingletonThreadPool() {
	       // Exists only to defeat instantiation.
	    }
	
	public static ExecutorService  getInstance() {
	       if (uniqueInstance == null) {
	           uniqueInstance = Executors.newCachedThreadPool();
	       }
	       return uniqueInstance;
	    }

}
