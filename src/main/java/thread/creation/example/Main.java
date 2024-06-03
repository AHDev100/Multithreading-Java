package thread.creation.example;

public class Main {
    public static void main(String[] args) throws InterruptedException {
       Thread thread = new Thread(() -> {
            System.out.println("We are now in thread " + Thread.currentThread().getName());
            System.out.println("Current thread priority is " + Thread.currentThread().getPriority());
            throw new RuntimeException("Intentional Exception");
       });

       //Method to set the Thread's name
       thread.setName("New Worker Thread");

       //Can programmatically set the static component of the thread's dynamic priority for scheduling (from 1-10)
       thread.setPriority(Thread.MAX_PRIORITY);

       System.out.println("We are in thread: " + Thread.currentThread().getName() + " before starting a new thread");
       Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
           @Override
           public void uncaughtException(Thread t, Throwable e) {
               System.out.println("A critical error happened in thread " + t.getName()
                        + " the error is " + e.getMessage());
           }
       });
       //This method creates a new thread and tells the JVM to pass it onto the OS
       thread.start();
       System.out.println("We are in thread: " + Thread.currentThread().getName() + " after starting a new thread");

       /*This method puts the current thread to sleep (don't schedule this thread to be run until after the time passes)
       for the given number of ms - won't take up any CPU usage until after the sleep time passes*/
       Thread.sleep(10000);
    }
}