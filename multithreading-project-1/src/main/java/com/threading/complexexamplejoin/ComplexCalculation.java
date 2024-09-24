package com.threading.complexexamplejoin;

import java.math.BigInteger;


/**
 *
 * 1. Thread Creation - how to create and start a thread using the
 * Thread class and the start() method.
 *
 * 2. Thread Join - how to wait for another thread using the Thread.join() method.
 *
 *
 * In this exercise we will efficiently calculate
 * the following result = base1 ^ power1 + base2 ^ power2
 *
 * Where a^b means: a raised to the power of b.
 *
 * For example 10^2 = 100
 *
 * We know that raising a number to a power
 * is a complex computation, so we we like to execute:
 *
 * result1 = x1 ^ y1
 *
 * result2 = x2 ^ y2
 *
 * In parallel.
 *
 * and combine the result in the end : result = result1 + result2
 *
 * This way we can speed up the entire calculation.
 *
 *
 * Note :
 *
 * base1 >= 0, base2 >= 0, power1 >= 0, power2 >= 0
 * */



public class ComplexCalculation {
    public BigInteger calculateResult(BigInteger base1,
                                      BigInteger power1,
                                      BigInteger base2,
                                      BigInteger power2) {
        BigInteger result;
        PowerCalculatingThread thread1 = new PowerCalculatingThread(base1, power1);
        PowerCalculatingThread thread2 = new PowerCalculatingThread(base2, power2);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        result = thread1.getResult().add(thread2.getResult());
        return result;
    }

    private static class PowerCalculatingThread extends Thread {
        private BigInteger result = BigInteger.ONE;
        private BigInteger base;
        private BigInteger power;

        public PowerCalculatingThread(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            for(BigInteger i = BigInteger.ZERO;
                i.compareTo(power) !=0;
                i = i.add(BigInteger.ONE)) {
                result = result.multiply(base);
            }
        }

        public BigInteger getResult() {
            return result;
        }
    }
}
