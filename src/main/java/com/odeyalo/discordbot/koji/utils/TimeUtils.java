package com.odeyalo.discordbot.koji.utils;

import java.util.concurrent.TimeUnit;

public class TimeUtils {

    public static String msToBeatifulString(long milliseconds) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) -
                TimeUnit.MINUTES.toSeconds(minutes);

        return String.format("%d:%02d", minutes, seconds);
    }
}
