/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ressipoller;

import java.util.*;

/**
 *
 * @author ktuomain
 */
public class TimeSeriesMux extends TimeSeries {
    private ArrayList<TimeSeries> list = new ArrayList();

    public void addTimeSeries(TimeSeries timeSeries) throws Exception {
        if (isIncreasing() != timeSeries.isIncreasing()) {
            throw new Exception("One timeseries is increasing another descending");
        } else {
            list.add(timeSeries);
        }
    }

    private int getNextIndex() {
        if (list.isEmpty()) {
            return -1;
        }

        int result = 0;
        for (int i = 1; i < list.size(); i++) {
            TimeEvent current = list.get(i).peekNextEvent();
            TimeEvent best = list.get(result).peekNextEvent();

            if (current == null) {
                result = i;
            } else {
                int cmp = best.compareTo(current);

                //TODO: check logic
                if (isIncreasing() && (cmp > 0)) {
                    result = i;
                }
            }
        }

        return result;
    }

    public TimeEvent peekNextEvent() {
        int index = getNextIndex();
        return (index >= 0) ? list.get(index).peekNextEvent() : null;
    }

    public TimeEvent getNextEvent() {
        int index = getNextIndex();
        return (index >= 0) ? list.get(index).getNextEvent() : null;
    }

    public TimeSeriesMux(boolean increasing) {
        super(increasing);
    }
}
