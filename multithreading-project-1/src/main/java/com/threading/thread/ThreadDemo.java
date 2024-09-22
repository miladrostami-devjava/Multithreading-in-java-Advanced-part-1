package com.threading.thread;

public class ThreadDemo {
    public static void main(String[] args) {

        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                //important:  code that you will run in this new thread
                System.out.println("WE are now in thread" +  " " + Thread.currentThread().getName());
                System.out.println("the priority the current thread :" + Thread.currentThread().getPriority());
            }
        });

        thread.setName("Worker Thread in run");
        thread.setPriority(Thread.MAX_PRIORITY);
        System.out.println("we are in thread :" + Thread.currentThread().getName() + " " + " After create new thread!");
        thread.start();
        System.out.println("we are in thread :" + Thread.currentThread().getName() + " "+ "  Before create new thread!");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
