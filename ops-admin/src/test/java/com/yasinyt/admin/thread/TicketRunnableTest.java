package com.yasinyt.admin.thread;

public class TicketRunnableTest implements Runnable{

    private int ticket = 100;

    @Override
    public void run() {
        for(int i =0;i<ticket;i++){
            //添加同步快
            synchronized (this){
                if(this.ticket>0){
                    try {
                        //通过睡眠线程来模拟出最后一张票的抢票场景
                        System.out.println(Thread.currentThread().getName()+"卖票---->"+(this.ticket--));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] arg){
    	TicketRunnableTest t1 = new TicketRunnableTest();
        new Thread(t1, "线程1").start();
        new Thread(t1, "线程2").start();
        new Thread(t1, "线程3").start();
    }
}