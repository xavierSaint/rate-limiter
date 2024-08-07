package org.example.slidingWindow;

import java.util.HashMap;
import java.util.Map;

public class UserBucketCreator {

    Map<Integer, SlidingWindowRateLimiter> bucket;

    public UserBucketCreator(int id) {
        bucket = new HashMap<>();
        bucket.put(id, new SlidingWindowRateLimiter(1, 5));
    }

    public void accessApplication(int id) {
        if(bucket.get(id).isRequestAllowed()) {
            System.out.println(Thread.currentThread().getName() + " --> request allowed");
        } else {
            System.out.println(Thread.currentThread().getName() + " --> too many requests, please try after some time");
        }
    }
}
