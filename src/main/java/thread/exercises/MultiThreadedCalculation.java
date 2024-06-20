package thread.exercises;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class MultiThreadedCalculation {
    public BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2) throws InterruptedException {
        BigInteger result = BigInteger.ZERO;
        /*
            Calculate result = ( base1 ^ power1 ) + (base2 ^ power2).
            Where each calculation in (..) is calculated on a different thread
        */
        List<PowerCalculatingThread> threads = Arrays.asList(new PowerCalculatingThread(base1, power1), new PowerCalculatingThread(base2, power2));
        for (PowerCalculatingThread thread : threads){
            thread.start();
        }

        for (PowerCalculatingThread thread : threads){
            thread.join(2000);
            result = result.add(thread.getResult());
        }

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
           /*
           Implement the calculation of result = base ^ power
           */
            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)){
                result = result.multiply(base);
            }
        }

        public BigInteger getResult() { return result; }
    }
}

/**
 * Michael's Solution (pretty much the same):
 * import java.math.BigInteger;
 *
 * public class ComplexCalculation {
 *     public BigInteger calculateResult(BigInteger base1,
 *                                       BigInteger power1,
 *                                       BigInteger base2,
 *                                       BigInteger power2) {
 *         BigInteger result;
 *         PowerCalculatingThread thread1 = new PowerCalculatingThread(base1, power1);
 *         PowerCalculatingThread thread2 = new PowerCalculatingThread(base2, power2);
 *
 *         thread1.start();
 *         thread2.start();
 *
 *         try {
 *             thread1.join();
 *             thread2.join();
 *         } catch (InterruptedException e) {
 *             e.printStackTrace();
 *         }
 *
 *         result = thread1.getResult().add(thread2.getResult());
 *         return result;
 *     }
 *
 *     private static class PowerCalculatingThread extends Thread {
 *         private BigInteger result = BigInteger.ONE;
 *         private BigInteger base;
 *         private BigInteger power;
 *
 *         public PowerCalculatingThread(BigInteger base, BigInteger power) {
 *             this.base = base;
 *             this.power = power;
 *         }
 *
 *         @Override
 *         public void run() {
 *             for(BigInteger i = BigInteger.ZERO;
 *                 i.compareTo(power) !=0;
 *                 i = i.add(BigInteger.ONE)) {
 *                 result = result.multiply(base);
 *             }
 *         }
 *
 *         public BigInteger getResult() {
 *             return result;
 *         }
 *     }
 * }
 */