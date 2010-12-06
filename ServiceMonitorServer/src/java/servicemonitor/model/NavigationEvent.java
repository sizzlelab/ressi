/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servicemonitor.model;

import java.util.Date;

/**
 *
 * @author kmtuomai
 */
public class NavigationEvent {
    String fromService, fromPage, toService, toPage;
    int count;
    Date time;

    public NavigationEvent(String fromService, String fromPage, String toService, String toPage, int count, Date time) {
        this.fromService = fromService;
        this.fromPage = fromPage;
        this.toService = toService;
        this.toPage = toPage;
        this.count = count;
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getFromPage() {
        return fromPage;
    }

    public void setFromPage(String fromPage) {
        this.fromPage = fromPage;
    }

    public String getFromService() {
        return fromService;
    }

    public void setFromService(String fromService) {
        this.fromService = fromService;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getToPage() {
        return toPage;
    }

    public void setToPage(String toPage) {
        this.toPage = toPage;
    }

    public String getToService() {
        return toService;
    }

    public void setToService(String toService) {
        this.toService = toService;
    }
    
}
