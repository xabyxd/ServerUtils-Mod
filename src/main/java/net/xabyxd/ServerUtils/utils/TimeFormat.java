package net.xabyxd.ServerUtils.utils;

public final class TimeFormat {
    // Utility class for formatting time
    private TimeFormat() {
    }

    public static String formatTicks(int ticks) {
        return formatTicks((long) ticks);
    }

    public static String formatTicks(long ticks) {

        long seconds = Math.max(0L, ticks) / 20;

        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long remainingSeconds = seconds % 60;


    return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    }
}