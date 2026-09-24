package net.xabyxd.ServerUtils.utils;

public class JailedPlayer {
    // Data structure for jailed players
    public String uuid;
    public String playerName;
    public String reason;
    public long releaseTimestamp;

    public JailedPlayer(String uuid, String playerName, String reason, long releaseTimestamp) {
        this.uuid = uuid;
        this.playerName = playerName;
        this.reason = reason;
        this.releaseTimestamp = releaseTimestamp;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() >= releaseTimestamp;
    }

    public long getRemainingSeconds() {
        return Math.max(0L, (releaseTimestamp - System.currentTimeMillis()) / 1000L);
    }
}