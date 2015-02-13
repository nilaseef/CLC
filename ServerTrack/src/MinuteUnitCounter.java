package com.company.Algorithms.Problems.ServerTrack.src;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Implements Minute Unit Counter
 */
public class MinuteUnitCounter extends UnitCounter {
    MinuteUnitCounter(int capacity) {
        super(capacity);
    }

    @Override
    public int getSlot() {
        Calendar calendar = new GregorianCalendar();
        int slot = calendar.get(Calendar.MINUTE);
        return slot;
    }

    @Override
    public CollectionUnit getCollectionUnit() {
        return CollectionUnit.MINUTE;
    }
}
