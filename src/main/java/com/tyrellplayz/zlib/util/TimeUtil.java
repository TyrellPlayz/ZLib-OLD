package com.tyrellplayz.zlib.util;

public class TimeUtil {

    private TimeUtil() {}

    public static int ticksToSeconds(int ticks) {
        return ticks*20;
    }

    public static int secondsToTicks(int seconds) {
        return seconds/20;
    }

}
