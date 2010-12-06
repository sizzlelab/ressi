/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ressipoller.dashboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 *
 * @author ktuomain
 */
public class LocationDataset {
  List<Double> latitudes = new ArrayList();
  List<Double> longitudes = new ArrayList();
  List<Date> time= new ArrayList();
  List<String> ids = new ArrayList(); //userId

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public List<Double> getLatitudes() {
        return latitudes;
    }

    public void setLatitudes(List<Double> latitudes) {
        this.latitudes = latitudes;
    }

    public List<Double> getLongitudes() {
        return longitudes;
    }

    public void setLongitudes(List<Double> longitudes) {
        this.longitudes = longitudes;
    }

    public List<Date> getTime() {
        return time;
    }

    public void setTime(List<Date> time) {
        this.time = time;
    }

  
}
