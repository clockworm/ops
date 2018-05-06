package com.yasinyt.admin.thread;

public class TicketThreadTest extends Thread{

    private int ticket = 100;

    public void run(){
        for(int i =0,size= ticket;i<size;i++){
        	synchronized(this) {
        		if(this.ticket>0){
	                try {
	                    sleep(100);
	                    System.out.println(Thread.currentThread().getName()+"卖票---->"+(this.ticket--));
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	            }else {
	            	System.out.println(Thread.currentThread().getName()+"卖票终止");
	            	Thread.currentThread().interrupt();
				}
        	}
        }
    }

    public static void main(String[] arg){
    	TicketThreadTest t1 = new TicketThreadTest();
        new Thread(t1,"线程1").start();
        new Thread(t1,"线程2").start();
        new Thread(t1,"线程3").start();
        //也达到了资源共享的目的，此处网上有各种写法，很多写法都是自圆其说，举一些特殊例子来印证自己的观点，然而事实却不尽如此。
    }
}