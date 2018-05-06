package com.yasinyt.admin.thread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest extends Thread{
	
	private Map<Integer, String> map = null;
	
	
	public ConcurrentHashMapTest(Map<Integer, String> map) {
		this.map = map;
	}
	public static void main(String[] args) {
		Map<Integer, String> mapParam = new ConcurrentHashMap<Integer, String>();
		for (int i = 0; i < 1000; i++) {
			mapParam.put(i, "value"+i);
		}
		ConcurrentHashMapTest t = new ConcurrentHashMapTest(mapParam);
		
		Thread t1 = new Thread(t,"线程1");
		Thread t2 = new Thread(t,"线程2");
		Thread t3 = new Thread(t,"线程3");
		
		t1.start();
		t2.start();
		t3.start();
		
	}
	@Override
	public void run() {
		for (int i = 0; i < 1000; i++) {
			synchronized (this) {
				if(map.size()>0) {
					try {
					sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					map.remove(i);
					System.out.println(Thread.currentThread().getName()+":"+map.size());
					continue;
				}else {
					break;
				}
			}
		}
	}
	
}
