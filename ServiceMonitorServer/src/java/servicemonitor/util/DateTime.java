/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicemonitor.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ktuo
 */
public class DateTime extends Date {
    public static final long MINUTE = 60 * 1000;

    public static final long HOUR = 60 * MINUTE;

    public static final long DAY = 24 * HOUR;

    public static final long WEEK = 7 * DAY;

    public static final long MONTH = 30 * DAY;

    public static final long YEAR = 365 * DAY;

    /** E, dd MMM yyyy ('W'w) HH:mm:ss Z --> Tue, 29 Jan 2002 (W05) 22:14:02 +0200 */
    public static final DateFormat LONG_DATE_WEEK_TIME = new SimpleDateFormat("E, dd MMM yyyy ('W'ww) HH:mm:ss Z");
    
    /** E, dd MMM yyyy HH:mm:ss Z --> Tue, 29 Jan 2002 22:14:02 +0200 */
    public static final DateFormat LONG_DATE_TIME = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");

    /** E, dd MMM yyyy HH:mm:ss z --> Tue, 29 Jan 2002 22:14:02 GMT */
    public static final DateFormat LONG_DATE_TIME2 = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
    
    /** IETF RFC 3339 format (UTC): yyyy-MM-dd'T'HH:mm:ss --> 2002-01-29T22:14:02 -0500*/
    public static final DateFormat INTERNET_TIME = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'") {{ setTimeZone(TimeZone.getTimeZone("UTC")); }};
    
    /** ISO 8601 format: yyyy-'W'ww-FF --> 2002-W05-05 */
    public static final DateFormat WEEK_DATE = new SimpleDateFormat("yyyy-'W'ww-FF");

    /** ISO 8601 format: yyyy-MM-dd HH:mm:ss --> 2002-01-29 22:14:02 */
    public static final DateFormat DATE_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    /** ISO 8601 format: yyyy-MM-dd --> 2002-01-29 */
    public static final DateFormat SHORT_DATE = new SimpleDateFormat("yyyy-MM-dd");
    
    /** yyyy-MMM-dd --> 2002-Jan-29 */
    public static final DateFormat SHORT_DATE2 = new SimpleDateFormat("yyyy-MMM-dd");

    /** ISO 8601 format: HH:mm:ss --> 22:14:02 */
    public static final DateFormat SHORT_TIME = new SimpleDateFormat("HH:mm:ss");

    public DateTime() {
    }

    public DateTime(long date) {
        super(date);
    }

    public DateTime(Date date) {
        super(date.getTime());

    }

    public static DateTime now() {
        return new DateTime(new Date());
    }

    public static DateTime today() {
        return now().toDate();
    }
    
    public DateTime on(int year, int month, int dayOfMonth) {
        Calendar cal = getCalendar();
        cal.set(year, month - 1, dayOfMonth);
        return new DateTime(cal.getTimeInMillis());
    }
    
    public DateTime at(int hour, int minute, int second) {
        Calendar cal = getCalendar();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        return new DateTime(cal.getTimeInMillis());
    }

    // Or maybe: startOfDay() or stripTime() (my favourite)
    public DateTime toDate() {
        long d = getTime() / DAY * DAY;
        return new DateTime(d);
    }

    /** 1..31 */
    public int getDayOfMonth() {
        return getCalendar().get(Calendar.DAY_OF_MONTH);
    }

    /** 1..12 */
    @Override
    public int getMonth() {
        return getCalendar().get(Calendar.MONTH) + 1;
    }

    /** 1979 */
    @Override
    public int getYear() {
        return getCalendar().get(Calendar.YEAR);
    }
    
    public DateTime add(long milliseconds) {
        long d = getTime();
        d += milliseconds;
        return new DateTime(d);
    }
    
    public DateTime addSeconds(int amount) {
        return add(Calendar.SECOND, amount);
    }
    
    public DateTime addMinutes(int amount) {
        return add(Calendar.MINUTE, amount);
    }

    public DateTime addHours(int amount) {
        return add(Calendar.HOUR, amount);
    }
    
    public DateTime addDays(int amount) {
        return add(Calendar.DAY_OF_MONTH, amount);
    }
    
    public DateTime addMonths(int amount) {
        return add(Calendar.MONTH, amount);
    }

    public DateTime addYears(int amount) {
        return add(Calendar.YEAR, amount);
    }
    
    private DateTime add(int field, int amount) {
        Calendar cal = getCalendar();
        cal.add(field, amount);
        return new DateTime(cal.getTimeInMillis());
    }
    
    private Calendar getCalendar() {
        Calendar cal = Calendar.getInstance();        
        cal.setTime(this);
        return cal;
    }
    
    public static DateTime fromString(String source, DateFormat format) {
        try {
            return new DateTime(format.parse(source));
        } catch (ParseException ex) {
            Logger.getLogger(DateTime.class.getName()).log(Level.SEVERE, "source = '" + source + "'", ex);
            return null;
        }
    }
    
    public static DateTime fromString(String source, String format) {
        try {
            return new DateTime(new SimpleDateFormat(format).parse(source));
        } catch (ParseException ex) {
            Logger.getLogger(DateTime.class.getName()).log(Level.SEVERE, "source = '" + source + "'", ex);
            return null;
        }
    }
    
    public String toString(DateFormat format) {
        return format.format(this);
    }

    public String toString(String format) {
        return new SimpleDateFormat(format).format(this);
    }
    
    public static void main(String[] args) {
        System.out.println("now = " + DateTime.now());
        System.out.println("yesterday at the same time = " + DateTime.now().add(-DAY));
        System.out.println("today = " + DateTime.today());
        System.out.println("yesterday = " + DateTime.today().add(-DAY));
        System.out.println("internet time = " + DateTime.now().toString(INTERNET_TIME));
        System.out.println("long date/time = " + DateTime.fromString("2002-01-29 22:14:02", DATE_TIME).toString(LONG_DATE_TIME));
        System.out.println("plain date/time = " + DateTime.now().on(2002, 01, 29).at(22, 14, 02).toString(DATE_TIME));
        System.out.println("week date = " + DateTime.fromString("2002-01-29 22:14:02", DATE_TIME).toString(WEEK_DATE));
        System.out.println("Sat, 17 Jan 2009 23:30:23 GMT = " + DateTime.fromString("Sat, 17 Jan 2009 23:30:23 GMT", "E, dd MMM yyyy HH:mm:ss z").toString(DATE_TIME) + " (local time)");
        System.out.println("short date 2 = " + DateTime.fromString("2002-01-29 22:14:02", DATE_TIME).toString(SHORT_DATE2));
    }

}
