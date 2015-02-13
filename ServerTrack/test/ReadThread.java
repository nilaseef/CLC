package com.company.Algorithms.Problems.ServerTrack.test;

import com.company.Algorithms.Problems.ServerTrack.src.ServerTracker;

public class ReadThread implements Runnable {
    public String serverName;
    @Override
    public void run() {
        try {
            ServerTracker.getInstance().display(serverName);
        }
        catch(InterruptedException ex) {
            System.out.println("Exception occurred");
        }
    }
}
