/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ressipoller;

/**
 *
 * @author ktuomain
 */
public abstract class TimeSeries {
    boolean increasing;
    TimeEvent prevEvent = null;

    public boolean isIncreasing() {
        return increasing;
    }

    public TimeSeries(boolean increasing) {
        this.increasing = increasing;
    }

    public abstract TimeEvent peekNextEvent();
    public abstract TimeEvent getNextEvent();
}

