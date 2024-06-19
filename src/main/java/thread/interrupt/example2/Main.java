package thread.interrupt.example2;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new longComputationTask(new BigInteger("20000"), new BigInteger("100000")));

        /**
         * We can set Daemon to true for the computationally expensive thread.
         * Doing so means that the application terminates once the main thread
         * terminates, and the computation will take place in the background
         * It's an alternative to gracefully stopping the thread using interrupt
         */
        thread.setDaemon(true);
        thread.start();
        Thread.sleep(100);
        thread.interrupt(); // Simply interrupting won't be enough for computationally intensive function
    }

    private static class longComputationTask implements Runnable {
        private BigInteger base;
        private BigInteger power;

        public longComputationTask(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println(base + "^" + power + " = " + pow(base, power));
        }

        private BigInteger pow(BigInteger base, BigInteger power){
            BigInteger result = BigInteger.ONE;

            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)){
//                if (Thread.currentThread().isInterrupted()){ // Check if the current thread has been interrupted externally
//                    System.out.println("Prematurely interrupted computation");
//                    return BigInteger.ZERO;
//                }
                result = result.multiply(base);
            }

            return result;
        }
    }
}
