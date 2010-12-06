/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ressipoller;

/**
 *
 * @author ktuomain
 */
public abstract class TimeEvent implements Comparable<TimeEvent> {
   public abstract int compareTo(TimeEvent timeEvent);
}
