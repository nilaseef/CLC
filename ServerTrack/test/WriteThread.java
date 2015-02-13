package com.company.Algorithms.Problems.ServerTrack.test;

import com.company.Algorithms.Problems.ServerTrack.src.ServerTracker;

public class WriteThread implements Runnable {
    public String serverName;
    @Override
    public void run() {
        try {

            ServerTracker st = ServerTracker.getInstance();
            st.recordLoad(serverName, randInt(1,9), randInt(1,9));
        }
        catch(InterruptedException ex) {
            System.out.println("Exception occurred");
        }
    }



    public static int randInt(int min, int max) {
        java.util.Random rand = new java.util.Random();

        return rand.nextInt((max - min) + 1) + min;
    }

}
