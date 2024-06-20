package thread.coordination.join;

import java.math.BigInteger;
import java.util.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // We want to calculate 0!, 3435!, factorials of others all in parallel
        List<Long> inputNumbers = Arrays.asList(0L, 3435L, 35435L, 2324L, 4656L, 23L, 2435L, 5566L);

        List<FactorialThread> threads = new ArrayList<>();

        for(long inputNumber : inputNumbers){
            threads.add(new FactorialThread(inputNumber));
        }

        for(Thread thread : threads){
            thread.start();
        }

        /**
         * For every thread the .join() method will only return
         * once that thread has been terminated. By the time the main
         * thread finishes this loop, all these threads are guaranteed
         * to have finished.
         */
        for(Thread thread : threads){
            thread.join(2000); // When using .join(), we need to decide how long we are willing to wait for worker threads, especially for heavy computational workers
        }

        for(int i = 0; i < inputNumbers.size(); i++){
            FactorialThread factorialThread = threads.get(i);
            if(factorialThread.isFinished()){
                System.out.println("Factorial of " + inputNumbers.get(i) + " is " + factorialThread.getResult());
            } else {
                System.out.println("The calculation for " + inputNumbers.get(i) + " is still in progress");
            }
        }
    }

    private static class FactorialThread extends Thread {
        private long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;

        public FactorialThread(long inputNumber){
            this.inputNumber = inputNumber;
        }

        @Override
        public void run(){
            this.result = factorial(inputNumber);
            this.isFinished = true;
        }

        public BigInteger factorial(long n){
            BigInteger tempResult = BigInteger.ONE;

            for(long i = n; i > 0; i--){
                tempResult = tempResult.multiply(new BigInteger(Long.toString(i)));
            }

            return tempResult;
        }

        public boolean isFinished() {
            return isFinished;
        }

        public BigInteger getResult() {
            return result;
        }
    }
}
