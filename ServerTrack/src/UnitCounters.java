package com.company.Algorithms.Problems.ServerTrack.src;

import java.util.*;


public class UnitCounters {

    private Set<UnitCounter> counters ; // Set, because we expect unique Counter Types.

    public UnitCounters(MetricType metricType) {
        counters = new HashSet<UnitCounter>();
        counters.add(new MinuteUnitCounter(Constants.MINUTE_HISTORY));
        counters.add(new HourUnitCounter(Constants.HOUR_HISTORY));
    }

    public Set<UnitCounter> getCounters() {
        return this.counters;
    }

}
