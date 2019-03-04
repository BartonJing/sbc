package com.barton.sbc.scheduled;

/**
 * @author barton
 */
public class MyScheduled1 implements Runnable {
    @Override
    public void run() {
        System.out.println("scheduled_1");
    }
}
