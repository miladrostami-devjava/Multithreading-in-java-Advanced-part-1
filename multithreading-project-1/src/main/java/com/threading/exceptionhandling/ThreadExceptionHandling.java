package com.threading.exceptionhandling;

public class ThreadExceptionHandling {

    public static void main(String[] args) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //important:  code that you will run in this new thread
                throw new RuntimeException("Intentional Exception happen!!!");
            }
        });
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("A Critical error happen in thread " + t.getName()
                        + " " + "the error is ;" + e.getMessage());
            }
        });
        thread.start();
    }
}
