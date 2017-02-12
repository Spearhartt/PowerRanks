package com.conquestiamc.utils;

import com.conquestiamc.PowerRanks;
import com.conquestiamc.config.Config;
import org.bukkit.Bukkit;

import java.util.logging.Logger;

/**
 * Created by Spearhartt on 2/12/2017.
 */
public class prLogger {
    private static Config config = null;
    private static Logger log = Bukkit.getLogger();
    public static String label = "[PowerRanks] ";

    public prLogger getLogger() {
        config = PowerRanks.config;
        return this;
    }

    public static void info(String message) {
        log.info(label + message);
    }

    public static void debug(String message) {
        if (config.getConfig().getBoolean("debug")) {
            log.info(label + message);
        }
    }

    public static void severe(String message) {
        log.severe(label + message);
    }
}
