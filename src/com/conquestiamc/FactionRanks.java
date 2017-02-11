package com.conquestiamc;

import com.conquestiamc.config.ConfigUtils;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Spearhartt on 2/7/2017.
 */
public class FactionRanks {
    public static ArrayList<Faction> preRanking = new ArrayList<>();
    public static ArrayList<Faction> factions = new ArrayList<>();

    public static ArrayList<Faction> getPopulation() {
        reloadFactions();
        if (factions.size() != 0) {
            Collections.sort(factions, new Comparator<Faction>() {
                @Override
                public int compare(Faction o1, Faction o2) {
                    return o2.getMPlayers().size() - o1.getMPlayers().size();
                }
            });
            return factions;
        }
        return null;
    }

    public static ArrayList<Faction> getPower() {
        reloadFactions();
        if (factions.size() != 0) {
            Collections.sort(factions, new Comparator<Faction>() {
                @Override
                public int compare(Faction o1, Faction o2) {
                    return (int)(100 * (o2.getPower() - o1.getPower()));
                }
            });
            return factions;
        }
        return null;
    }

    public static void reloadFactions() {
        factions = new ArrayList<>();
        for (Faction faction : FactionColl.get().getAll()) {
            if (!ConfigUtils.getDisabledFactions().contains(ChatColor.stripColor(faction.getName()))) {
                factions.add(faction);
            }
        }
    }

    public static int getOldRank(Faction faction, ArrayList<Faction> oldRanking) {
        if (oldRanking.contains(faction)) {
            return oldRanking.indexOf(faction);
        } else {
            return ConfigUtils.maxRanks + 1;
        }
    }

    public static boolean isDifferent(ArrayList<Faction> f1, ArrayList<Faction> f2) {
        return f1.equals(f2);
    }

}
