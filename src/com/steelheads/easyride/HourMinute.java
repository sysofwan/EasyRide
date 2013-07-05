package com.steelheads.easyride;

/**
 * Created by SySofwan
 * Date: 7/3/13
 * Time: 7:33 PM
 */

public class HourMinute {

    private int minutes;
    private static final int HOURS_PER_DAY = 24;
    private static final int MINUTES_PER_HOUR = 60;
    private static final int MINUTES_PER_DAY = HOURS_PER_DAY * MINUTES_PER_HOUR;
    private static final int HOURS_HALF_DAY = 12;
    private static final int MINUTE_IN_HALF_DAY = HOURS_HALF_DAY * MINUTES_PER_HOUR;

    public HourMinute() {
        minutes = 0;
    }

    public HourMinute(int minute) {
        this.minutes = minute % (MINUTES_PER_DAY);
    }

    public int getInMinutes() {
        return minutes;
    }

    public int getMinuteOfDay() {
        return minutes % MINUTES_PER_HOUR;
    }

    public int getHourOfDay() {
        return minutes / MINUTES_PER_HOUR;
    }

    public int getHourFormatted() {
        if (minutes >= MINUTE_IN_HALF_DAY + MINUTES_PER_HOUR) {
            return getHourOfDay() - 12;
        } else if (getHourOfDay() == 0) {
            return 12;
        }
        return getHourOfDay();
    }

    public String getAMPM() {
        if (minutes >= MINUTE_IN_HALF_DAY) {
            return "pm";
        }
        return "am";
    }

    public String toString() {
        return getHourFormatted() + ":" + String.format("%02d", getMinuteOfDay()) + getAMPM();
    }
}
