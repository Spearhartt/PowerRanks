package com.conquestiamc.listeners;

import com.conquestiamc.FactionLeaders;
import com.conquestiamc.PowerRanks;
import com.conquestiamc.config.ConfigUtils;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsRankChange;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.ArrayList;

/**
 * Created by Spearhartt on 2/6/2017.
 */
public class RankChangeListener implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void onRankChangeEvent (EventFactionsRankChange event) {
        if (event.getNewRank() == Rel.LEADER) {
            ConfigUtils.setPermissions(event.getMPlayer(), Integer.MAX_VALUE);
            FactionLeaders.addLeader(event.getMPlayer());
            return;
        } else if (FactionLeaders.getLeaders().contains(event.getMPlayer())) {
            ConfigUtils.removeAllPerms(event.getMPlayer());
            FactionLeaders.removeLeader(event.getMPlayer());
        }
    }
}
