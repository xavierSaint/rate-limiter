package org.example.slidingWindow;

import lombok.Getter;
import lombok.Setter;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Getter
@Setter
public class SlidingWindowRateLimiter implements  RateLimiter {
    Queue<Long> slidingWindow;
    int timeWindowInSeconds;
    int bucketCapacity;

    public SlidingWindowRateLimiter(int timeWindowInSeconds, int bucketCapacity) {
        this.timeWindowInSeconds = timeWindowInSeconds;
        this.bucketCapacity = bucketCapacity;
        this.slidingWindow = new ConcurrentLinkedQueue<>();
    }


    /**
     * This method checks whether the current request is allowed or not
     *
     * @return true or false
     */
    @Override
    public boolean isRequestAllowed() {
        //clean up older entries
        long currentTimeInSeconds = System.currentTimeMillis() / 1000;
        cleanUpOlderEntries(currentTimeInSeconds);

        //add current request into window
        if (slidingWindow.size() <= bucketCapacity) {
            slidingWindow.offer(currentTimeInSeconds);
            return true;
        }
        return false;
    }

    /**
     * this function removes stale requests
     */
    private void cleanUpOlderEntries(long currentTimeInSeconds) {
        long cutOffTime = currentTimeInSeconds - timeWindowInSeconds;
        while(!slidingWindow.isEmpty() && slidingWindow.peek() < cutOffTime) {
            slidingWindow.poll();
        }
    }
}
