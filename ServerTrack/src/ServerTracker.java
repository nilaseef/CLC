package com.company.Algorithms.Problems.ServerTrack.src;

import java.text.DecimalFormat;
import java.util.*;
/*
    Class that holds our resource (in memory)
        This class is a singleton.
        At any point we need only one instance of the resource that is being worked upon by multiple workers.

        The two public api's to read and write data is protected via a MultiReadSingleWrite lock.

 */
public class ServerTracker {

    // The one and only one instance
    private static ServerTracker instance = null;

    // private to disallow explicit creation of ServerTracker instance
    private ServerTracker() { }

    // public method to get hold of the single instance
    public static synchronized ServerTracker getInstance() {
        if(instance == null) {
            instance = new ServerTracker();
        }
        return  instance;
    }

    // Our basic data structure to hold the in-memory model for the server monitoring data
    // In essence the data structure I chose was to have a Map of Map.
    //  Server1
    //      CPU
    //          MinuteHistory
    //              History59, History58, History57,........History0
    //          HourHistory
    //              History59, History58, History57,........History0
    //      RAM
    //          MinuteHistory
    //              .....
    //          HourHistory
    //              .....
    //  Server2
    //      CPU
    //          ....
    //      RAM
    //          ....
    //  ...
    private Map<String, Map<MetricType, UnitCounters>> serverTrackerMap = new LinkedHashMap<String, Map<MetricType, UnitCounters>>();

    // lock to protect our resource against concurrent acess. Simultaneous reads are ok.
    MultiReadSingleWriteLock m = new MultiReadSingleWriteLock();


    /**
     * Records server load for specified counters
     * @param serverName - name of the server
     * @param cpuLoad - the CPU load on this server
     * @param ramLoad - the RAM load on this server
     * @throws InterruptedException
     */
    public void recordLoad(final String serverName, final double cpuLoad, final double ramLoad) throws InterruptedException {
        m.lockWrite();
        recordLoad(serverName, cpuLoad, MetricType.CPU);
        recordLoad(serverName, ramLoad, MetricType.RAM);
        m.unlockWrite();
    }

    private void recordLoad(final String serverName, final double load, MetricType metricType) {
        Map<MetricType, UnitCounters> countersMetricTypeMap = new HashMap<MetricType, UnitCounters>();
        if (!serverTrackerMap.containsKey(serverName)) {
            UnitCounters unitCounters = new UnitCounters(metricType);
            for (UnitCounter c : unitCounters.getCounters()) {
                c.updateRunningAverage(c.getSlot(), load);
            }
            countersMetricTypeMap.put(metricType, unitCounters);
        }
        else {
            countersMetricTypeMap = serverTrackerMap.get(serverName);
            if (!countersMetricTypeMap.containsKey(metricType)) {
                UnitCounters unitCounters = new UnitCounters(metricType);
                for (UnitCounter c : unitCounters.getCounters()) {
                    c.updateRunningAverage(c.getSlot(), load);
                }
                countersMetricTypeMap.put(metricType, unitCounters);
            } else {
                UnitCounters unitCounters = countersMetricTypeMap.get(metricType);
                for (UnitCounter c : unitCounters.getCounters()) {
                    c.updateRunningAverage(c.getSlot(), load);
                }
            }
        }

        serverTrackerMap.put(serverName, countersMetricTypeMap);
    }


    /**
     * Prints out the history of RAM and CPU loads for a given server.
     * @param serverName - name of the server
     * @throws InterruptedException
     */
    public void display(final String serverName) throws InterruptedException {
        m.lockRead();
        printHistory(serverName);
        m.unlockRead();
    }

    private void printHistory(final String serverName) {
        if(!serverTrackerMap.containsKey(serverName)) {
            System.out.println("No loads collected for server " + serverName);
        }
        else {
            System.out.println("---------------------------------");
            System.out.println("Found loads for server: " + serverName);
            System.out.println("---------------------------------");
            Map<MetricType, UnitCounters> countersMetricTypeMap = serverTrackerMap.get(serverName);
            for(Map.Entry<MetricType, UnitCounters> entry : countersMetricTypeMap.entrySet()) {
                System.out.println("\tMetric Type: " + entry.getKey());
                System.out.println("\t----------------");
                for (UnitCounter counter : entry.getValue().getCounters()) {
                    System.out.println("\t\tAverage load by " + counter.getCollectionUnit() + ". [sampleCount, average]");
                    System.out.print("\t\t");
                    for (int i = counter.getRunningAverage().size()-1; i >= 0; i--) {
                        RunningAverage runningAverage = counter.getRunningAverage().get(i);
                        DecimalFormat twoDForm = new DecimalFormat("#.##");
                        System.out.print("[" +  runningAverage.getSampleCount() + ", "
                                + Double.valueOf(twoDForm.format(runningAverage.getAverage())) + "]" + "\t\t");
                    }
                    System.out.println();
                }
            }
        }
    }
}
