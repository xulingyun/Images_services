package org.xulingyun.util;

public class MyThread extends Thread {

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName()+"&"+Thread.currentThread().getThreadGroup().activeCount());
		try {
			Thread.sleep(3000);
			System.out.println(Thread.currentThread().getName()+"*"+Thread.currentThread().getThreadGroup().activeGroupCount());
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
