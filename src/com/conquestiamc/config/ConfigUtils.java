package com.conquestiamc.config;

import com.conquestiamc.FactionLeaders;
import com.conquestiamc.FactionRanks;
import com.conquestiamc.PowerRanks;
import com.conquestiamc.utils.prLogger;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Spearhartt on 2/6/2017.
 */
public class ConfigUtils {
    private static FileConfiguration config = PowerRanks.config.getConfig();
    public static HashMap<Integer, List<String>> powerRankPerms = new HashMap<>();
    public static HashMap<Integer, List<String>> populationRankPerms = new HashMap<>();
    public static FactionRanks ranks = new FactionRanks();
    public static int maxRanks = 20;
    private static prLogger log = PowerRanks.log;
    private static String label = PowerRanks.label;

    public void setupConfig() {
        if (config.getConfigurationSection("powerRanks").getKeys(false).size() != 0) {
            log.debug("Loading Power ranks.");
            for (String rank : config.getConfigurationSection("powerRanks").getKeys(false)) {
                powerRankPerms.put(Integer.parseInt(rank), config.getStringList("powerRanks." + rank));
            }
        }

        if (config.getConfigurationSection("populationRanks").getKeys(false).size() != 0) {
            log.debug("Loading Population ranks.");
            for (String rank : config.getConfigurationSection("populationRanks").getKeys(false)) {
                populationRankPerms.put(Integer.parseInt(rank), config.getStringList("populationRanks." + rank));
            }
        }

        if (populationRankPerms.size() > powerRankPerms.size()) {
            maxRanks = populationRankPerms.size();
        } else {
            maxRanks = powerRankPerms.size();
        }

        log.debug("Max ranks set to: " + maxRanks);
    }

    /** Gets factions that are to be excluded from the power ranks */
    public static List<String> getDisabledFactions() {
        return config.getStringList("DisabledFactions");
    }

    //Sets permissions for player
    public static void setPermissions(ArrayList<Faction> preRanking) {
        runnablePermsCheck(preRanking);
    }

    public static void runnablePermsCheck(ArrayList<Faction> preRanking) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(PowerRanks.plugin, new Runnable() {
            @Override
            public void run() {
                log.debug("====== BEGINNING PERMISSIONS SET ======");
                for (MPlayer mPlayer : FactionLeaders.getLeaders()) {
                    log.debug("Checking player " + mPlayer.getName());
                    removePermissions(mPlayer, ranks.getOldRank(mPlayer.getFaction(), preRanking));
                    addPermissions(mPlayer);
                }
                log.debug("====== ENDING OF PERMISSIONS SET ======");
            }
        }, 4L);
    }

    //Adds permissions for player
    public static void addPermissions(MPlayer mPlayer) {
        for (int i = 0; i < maxRanks; i++) {
            if (!powerRankPerms.isEmpty()) {
                if (ranks.getPower().get(i) == mPlayer.getFaction()) {
                    log.debug("Adding power perms " + powerRankPerms.get(i+1) + " for rank " + (i+1) + " to " + mPlayer.getName());
                    addInWorlds(mPlayer, powerRankPerms.get(i+1));
                }
            }

            if (!populationRankPerms.isEmpty()) {
                if (ranks.getPopulation().get(i) == mPlayer.getFaction()) {
                    log.debug("Adding population perms " + populationRankPerms.get(i+1) + " for rank " + (i+1) + " to " + mPlayer.getName());
                    addInWorlds(mPlayer, populationRankPerms.get(i+1));
                }
            }
        }
    }

    /**Adds permissions to player in all worlds
     *
     * @param mPlayer player to add to
     * @param perms perms to add
     */
    public static void addInWorlds(MPlayer mPlayer, List<String> perms) {
        for (World world : Bukkit.getWorlds()) {
            for (String perm : perms) {
                PowerRanks.perms.playerAdd(world.getName(), mPlayer.getPlayer(), perm);
            }
        }
    }

    /**Removes permissions from player using their old rank as reference
     *
     * @param mPlayer the player to remove from
     * @param oldRank the old rank to use
     */
    public static void removePermissions(MPlayer mPlayer, int oldRank) {
        oldRank++;
        log.debug("Attempting to remove permissions for rank: " + oldRank);
        if (powerRankPerms.get(oldRank) != null) {
            log.debug("Removing power permissions " + powerRankPerms.get(oldRank) + " for rank " + oldRank);
            removeInWorlds(mPlayer, powerRankPerms.get(oldRank));
        }

        if (populationRankPerms.get(oldRank) != null) {
            log.debug("Removing population permissions " + populationRankPerms.get(oldRank) + " for rank " + oldRank);
            removeInWorlds(mPlayer, populationRankPerms.get(oldRank));
        }
    }

    /** Removes all permissions from this plugin from player in all worlds
     *
     *  @param mPlayer the player you want to remove from
     */
    public static void removeAllPerms(MPlayer mPlayer) {
        for (List<String> perms : powerRankPerms.values()) {
            removeInWorlds(mPlayer, perms);
        }

        for (List<String> perms : populationRankPerms.values()) {
            removeInWorlds(mPlayer, perms);
        }
    }

    /**Removes all permissions on list from player in all worlds
     *
     * @param mPlayer the player you want to remove from
     * @param perms all permissions you want to remove
     */
    public static void removeInWorlds(MPlayer mPlayer, List<String> perms) {
        for (World world : Bukkit.getWorlds()) {
            for (String perm : perms) {
                PowerRanks.perms.playerRemove(world.getName(), mPlayer.getPlayer(), perm);
            }
        }
    }
}
