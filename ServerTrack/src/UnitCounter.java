package com.company.Algorithms.Problems.ServerTrack.src;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract Counter type. Any counter that we need to track/monitor needs to extend this base.
 *   currently we track Hour and and Minute units of time.
 */
public abstract class UnitCounter {

    protected List<RunningAverage> runningAverage = null; // array of averages for last N time units

    /**
     * create RunningAverage array of the specified capacity and initialize it to 0.
     * @param capacity - how long back in time to go to and see running average history.
     */
    UnitCounter(int capacity) {
        runningAverage = new ArrayList<RunningAverage>(capacity);
        for (int i = 0; i < capacity; i++) {
            runningAverage.add(new RunningAverage(getSlot() - i, 0, 0));
        }
    }

    public List<RunningAverage> getRunningAverage() {
        return this.runningAverage;
    }


    /**
     *
     * @param slot - place holder in our ArrayList that tracks time ticks and updates right bucket to calculate average.
     * @param load - amount of load that has happened at this tick.
     */
    public void updateRunningAverage(int slot, double load) {
        RunningAverage runningAverageArray = runningAverage.get(runningAverage.size()-1);
        if(runningAverageArray.getSlot() != slot) {
            // This is a new time tick. Insert a value at the end.
            // However before that also remove the expired tick cell from the start.
            runningAverage.remove(0);
            runningAverage.add(new RunningAverage(slot, 1, load));
        }
        else {
            // there is an existing cell with this time tick. update its running average
            int newCount = runningAverageArray.getSampleCount()+1;
            double newAvgLoad = (runningAverageArray.getAverage() + load)/newCount;
            runningAverage.set(runningAverage.size()-1, new RunningAverage(slot, newCount, newAvgLoad));
        }
    }

    public abstract int getSlot();

    public abstract CollectionUnit getCollectionUnit();

}
