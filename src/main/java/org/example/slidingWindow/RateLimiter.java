package org.example.slidingWindow;

public interface RateLimiter {
    /**
     * This method checks whether the current request is allowed or not
     *
     * @return true or false
     */
    boolean isRequestAllowed();
}
