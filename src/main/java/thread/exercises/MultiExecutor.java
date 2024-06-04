import java.util.List;

import java.util.ArrayList; 

public class MultiExecutor {

    // Add any necessary member variables here
    private final List<Runnable> tasks;  
    /* 
     * @param tasks to executed concurrently
     */
    public MultiExecutor(List<Runnable> tasks) {
        // Complete your code here
        this.tasks = tasks; 
    }

    /**
     * Starts and executes all the tasks concurrently
     */
    public void executeAll() {
        // complete your code here
        List<Thread> threads = new ArrayList(tasks.size()); 
        
        for (Runnable run : tasks){
            Thread thread = new Thread(run); 
            threads.add(thread); 
        }
        
        for (Thread thread : threads){
            thread.start(); 
        }
    }
}
