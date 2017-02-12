package com.conquestiamc.listeners;

import com.conquestiamc.FactionLeaders;
import com.conquestiamc.config.ConfigUtils;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.event.EventFactionsRankChange;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

/**
 * Created by Spearhartt on 2/6/2017.
 */
public class RankChangeListener implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void onRankChangeEvent (EventFactionsRankChange event) {
        if (event.getNewRank() == Rel.LEADER) {
            FactionLeaders.addLeader(event.getMPlayer());
            ConfigUtils.setPermissions(null);
            return;
        } else if (FactionLeaders.getLeaders().contains(event.getMPlayer())) {
            ConfigUtils.removeAllPerms(event.getMPlayer());
            FactionLeaders.removeLeader(event.getMPlayer());
        }
    }
}
