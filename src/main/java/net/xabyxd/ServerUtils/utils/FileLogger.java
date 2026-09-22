package net.xabyxd.ServerUtils.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileLogger {

    private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static synchronized void append(File file, String message) {
        try {
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
                writer.println("[" + TIMESTAMP_FORMAT.format(new Date()) + "] " + message);
            }
        } catch (IOException e) {
            LogHelper.info("Failed to write log: " + e.getMessage());
            LogHelper.debug("Chat logging exception:", e);
        }
    }
}