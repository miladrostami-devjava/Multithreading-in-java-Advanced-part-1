package com.threading.daemon;


import java.math.BigInteger;

public class DaemonThread {

    public static void main(String[] args) {
        Thread thread = new Thread(new LongComputationTask(new BigInteger("200000"),new BigInteger("4555465686545")));
        thread.start();
        thread.setDaemon(true);    }



    private static class LongComputationTask implements Runnable{
        private BigInteger base;
        private BigInteger power;

        public LongComputationTask(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }


        @Override
        public void run() {
            System.out.println(base + "^" + power + "=" + pow(base,power));
        }

        private BigInteger pow(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;
            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0;
                 i.add(BigInteger.ONE)){
                if (Thread.currentThread().isInterrupted()){
                    System.out.println("Prematurely interrupted computation!!!");
                    return BigInteger.ZERO;
                }
                result = i.multiply(base);
            }
            return  result;
        }



    }
}
