package thread.syncrhonization.exercises;

public class MinMaxMetrics {

    // Add all necessary member variables
    private volatile long MIN;
    private volatile long MAX;

    /**
     * Initializes all member variables
     */
    public MinMaxMetrics(){
        this.MIN = Long.MAX_VALUE;
        this.MAX = Long.MIN_VALUE;
    }

    /**
     * Adds a new sample to our metrics.
     */
    public synchronized void addSample(long newSample) {
        this.MIN = Math.min(this.MIN, newSample);
        this.MAX = Math.max(this.MAX, newSample);
    }

    /**
     * Returns the smallest sample we've seen so far.
     */
    public long getMin() {
        return this.MIN;
    }

    /**
     * Returns the biggest sample we've seen so far.
     */
    public long getMax() {
        return this.MAX;
    }
}
