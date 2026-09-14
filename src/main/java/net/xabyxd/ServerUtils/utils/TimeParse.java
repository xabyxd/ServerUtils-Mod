package net.xabyxd.ServerUtils.utils;

public final class TimeParse {
    // Utility class for parsing formatted time back into ticks
    private TimeParse() {
    }

    public static int parseTicksAsInt(String time) {
        return (int) parseTicks(time);
    }

    public static long parseTicks(String time) {

        String[] parts = time.split(":");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid time format, expected HH:MM:SS");
        }

        long hours = Long.parseLong(parts[0]);
        long minutes = Long.parseLong(parts[1]);
        long seconds = Long.parseLong(parts[2]);

        long totalSeconds = hours * 3600 + minutes * 60 + seconds;

        return totalSeconds * 20;
    }
}