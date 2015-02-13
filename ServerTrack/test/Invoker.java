package com.company.Algorithms.Problems.ServerTrack.test;

public class Invoker {

    public void runTests() throws InterruptedException{
        int i = 200;
        while(i > 0) {

            if(i%5 == 0) {
                WriteThread w = new WriteThread();
                w.serverName = "Server1";
                new Thread(w).start();
            }
            else if(i%7 == 0) {
                WriteThread w = new WriteThread();
                w.serverName = "Server2";
                new Thread(w).start();
            }
            else if(i%11 == 0) {
                WriteThread w = new WriteThread();
                w.serverName = "Server3";
                new Thread(w).start();
            }
            else if(i%3 == 0) {
                ReadThread r = new ReadThread();
                r.serverName = "Server3";
                new Thread(r).start();
            }
            else if(i%2 == 0) {
                ReadThread r = new ReadThread();
                r.serverName = "Server2";
                new Thread(r).start();
            }
            else {
                ReadThread r = new ReadThread();
                r.serverName = "Server1";
                new Thread(r).start();
            }

            i--;
            Thread.sleep(300);
        }
    }
}

