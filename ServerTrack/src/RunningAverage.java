package com.company.Algorithms.Problems.ServerTrack.src;

/**
 * Simple class to hold attributes associated with RunningAverage.
 */
public class RunningAverage {

    private int slot;
    private int sampleCount;
    private double average;

    public RunningAverage(int slot, int sampleCount, double average) {
        this.slot = slot;
        this.sampleCount = sampleCount;
        this.average = average;
    }

    public int getSlot() {
        return this.slot;
    }

    public int getSampleCount() {
        return this.sampleCount;
    }

    public double getAverage() {
        return this.average;
    }
}