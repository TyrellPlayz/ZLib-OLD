package com.tyrellplayz.zlib.util;

public class TimeUtil {

    private TimeUtil() {}

    public static int ticksToSeconds(int ticks) {
        return ticks*20;
    }

    public static int secondsToTicks(int seconds) {
        return seconds/20;
    }

    public static String getFormattedTime(long ticks) {
        int hours = (int) ((Math.floor(ticks / 1000.0) + 6) % 24);
        int minutes = (int) Math.floor((ticks % 1000) / 1000.0 * 60);
        return String.format("%02d:%02d", hours, minutes);
    }

    public static String getFormattedTimeAmPm(long ticks) {
        int hours = (int) ((Math.floor(ticks / 1000.0) + 6) % 24);
        int minutes = (int) Math.floor((ticks % 1000) / 1000.0 * 60);
        return String.format("%02d:%02d " + ((hours>=12) ? "PM" : "AM"), hours%12, minutes);
    }

}
