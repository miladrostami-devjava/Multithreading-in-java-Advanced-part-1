package com.threading.coordination;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JoinInThread {
    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNumbers = Arrays.asList(0L,12L,23L,64L,70L,52L,99L,898L);
    List<FactorialThread> threads = new ArrayList<>();
    for (long inputNumber:inputNumbers){
        threads.add(new FactorialThread(inputNumber));
    }
for (Thread thread:threads){
    thread.setDaemon(true);
    thread.start();
}

        for (Thread thread:threads){
            thread.join(4000);
        }

        for (int i = 0; i < inputNumbers.size(); i++) {
            FactorialThread factorialThread = threads.get(i);
            if (factorialThread.isFinished){
                System.out.println("factorial of :" + inputNumbers.get(i)  + " " + "is :" + factorialThread.getResult());
            }else {
                System.out.println("the calculation for :" + inputNumbers.get(i)  + " is still in progress!!!");
            }
        }

    }


    private static class  FactorialThread extends Thread {
        private Long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private Boolean isFinished  = false;

        public FactorialThread(Long inputNumber) {
            this.inputNumber = inputNumber;
        }

        @Override
        public void run() {
            this.result = factorial(inputNumber);
            this.isFinished = true;
            super.run();
        }

        private BigInteger factorial(Long inputNumber) {
            BigInteger tempResult = BigInteger.ONE;
            for(long i = inputNumber;i > 0 ; i --){
                tempResult = tempResult.multiply(new BigInteger(Long.toString(i)));
            }
            return tempResult;
        }

        public BigInteger getResult() {
            return result;
        }

        public Boolean isFinished() {
            return isFinished;
        }
    }
}
