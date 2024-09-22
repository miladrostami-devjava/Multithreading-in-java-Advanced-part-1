package com.threading.createthread;

public class ThreadCreate {
    public static void main(String[] args) {
        Thread thread = new NewThread();
thread.start();


    }

    public static class NewThread extends Thread {
        @Override
        public void run() {
            //important:  code that you will run in this new thread
            System.out.println("the thread :" + Thread.currentThread().getName());
            super.run();
        }
    }

}
