package net.xabyxd.ServerUtils.utils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.xabyxd.ServerUtils.config.Config;

public final class JailManager {

    public static final double JAIL_RADIUS = 5.0; // blocks a jailed player may roam from the jail center

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Type MAP_TYPE = new TypeToken<HashMap<String, JailedPlayer>>() {}.getType();

    private static Map<String, JailedPlayer> jailed = new HashMap<>();
    private static boolean loaded = false;

    private static JailLocation jailLocation = null;
    private static boolean locationLoaded = false;

    private JailManager() {}

    // ---- Jailed players ----

    private static void ensureLoaded() {
        if (loaded) return;
        loaded = true;

        if (!Config.JailedPlayersFile.exists()) {
            jailed = new HashMap<>();
            return;
        }

        try (FileReader reader = new FileReader(Config.JailedPlayersFile)) {
            Map<String, JailedPlayer> data = GSON.fromJson(reader, MAP_TYPE);
            jailed = (data != null) ? data : new HashMap<>();
        } catch (IOException e) {
            e.printStackTrace();
            jailed = new HashMap<>();
        }
    }

    private static void save() {
        Config.JailedPlayersFile.getParentFile().mkdirs();
        try (FileWriter writer = new FileWriter(Config.JailedPlayersFile)) {
            GSON.toJson(jailed, MAP_TYPE, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void jail(String uuid, String playerName, String reason, long seconds) {
        ensureLoaded();
        long releaseAt = System.currentTimeMillis() + (seconds * 1000L);
        jailed.put(uuid, new JailedPlayer(uuid, playerName, reason, releaseAt));
        save();
    }

    public static synchronized void unjail(String uuid) {
        ensureLoaded();
        if (jailed.remove(uuid) != null) {
            save();
        }
    }

    public static synchronized boolean isJailed(String uuid) {
        ensureLoaded();
        JailedPlayer jp = jailed.get(uuid);
        if (jp == null) return false;
        if (jp.isExpired()) {
            jailed.remove(uuid);
            save();
            return false;
        }
        return true;
    }

    public static synchronized JailedPlayer get(String uuid) {
        ensureLoaded();
        return jailed.get(uuid);
    }

    // ---- Jail location ----

    private static void ensureLocationLoaded() {
        if (locationLoaded) return;
        locationLoaded = true;

        if (!Config.JailLocationFile.exists()) {
            jailLocation = null;
            return;
        }

        try (FileReader reader = new FileReader(Config.JailLocationFile)) {
            jailLocation = GSON.fromJson(reader, JailLocation.class);
        } catch (IOException e) {
            e.printStackTrace();
            jailLocation = null;
        }
    }

    public static synchronized void setJailLocation(double x, double y, double z) {
        ensureLocationLoaded();
        jailLocation = new JailLocation(x, y, z);
        Config.JailLocationFile.getParentFile().mkdirs();
        try (FileWriter writer = new FileWriter(Config.JailLocationFile)) {
            GSON.toJson(jailLocation, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized JailLocation getJailLocation() {
        ensureLocationLoaded();
        return jailLocation;
    }
}