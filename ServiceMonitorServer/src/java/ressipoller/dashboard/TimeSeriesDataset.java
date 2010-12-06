/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ressipoller.dashboard;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ktuomain
 */
public class TimeSeriesDataset {
  String label;
  List<Double> values = new ArrayList();
  List<Date> times = new ArrayList();

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Date> getTimes() {
        return times;
    }

    public void setTimes(List<Date> times) {
        this.times = times;
    }

    public List<Double> getValues() {
        return values;
    }

    public void setValues(List<Double> values) {
        this.values = values;
    }
  
}
