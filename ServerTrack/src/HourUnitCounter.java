package com.company.Algorithms.Problems.ServerTrack.src;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Implements Hour Unit Counter.
 */
public class HourUnitCounter extends UnitCounter {
    HourUnitCounter(int capacity) {
        super(capacity);
    }

    @Override
    public int getSlot() {
        Calendar calendar = new GregorianCalendar();
        return calendar.get(Calendar.HOUR);
    }

    @Override
    public CollectionUnit getCollectionUnit() {
        return CollectionUnit.HOUR;
    }
}
