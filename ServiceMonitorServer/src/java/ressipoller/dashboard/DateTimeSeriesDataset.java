/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ressipoller.dashboard;

import java.util.*;

/**
 *
 * @author ktuomain
 */
public class DateTimeSeriesDataset {
    private Map<Date, Map<String, Double>> dataset = new HashMap();
    private SortedSet<String> dimensions = new TreeSet();

    public void addDatapoint(Date date, String dimension, Double value) {
        Map<String, Double> dateDataset = dataset.get(date);
        if (dateDataset == null) {
            dateDataset = new HashMap();
            dataset.put(date, dateDataset);
        }
        dimensions.add(dimension);
        dateDataset.put(dimension, value);
    }

    public Double getDataPoint(Date date, String dimension) {
        Map<String, Double> dateDataset = dataset.get(date);
        if (dateDataset == null)
            return null;
        return dateDataset.get(dimension);
    }

    public SortedSet<String> getDimensions() {
        return dimensions;
    }

    public SortedSet<Date> getDates() {
        SortedSet result = new TreeSet();
        result.addAll(dataset.keySet());
        return result;
    }

}
