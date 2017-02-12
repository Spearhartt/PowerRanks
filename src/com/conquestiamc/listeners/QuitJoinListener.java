package com.conquestiamc.listeners;

import com.conquestiamc.FactionLeaders;
import com.conquestiamc.config.ConfigUtils;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

/**
 * Created by Spearhartt on 2/12/2017.
 */
public class QuitJoinListener implements Listener {

    @EventHandler
    public void onLogoutEvent (PlayerQuitEvent event) {
        if (MPlayer.get(event.getPlayer()).getRole().equals(Rel.LEADER)) {
            ConfigUtils.removeAllPerms(MPlayer.get(event.getPlayer()));
            FactionLeaders.removeLeader(MPlayer.get(event.getPlayer()));
        }
    }

    @EventHandler
    public void onLoginEvent (PlayerJoinEvent event) {
        if (MPlayer.get(event.getPlayer()).getRole().equals(Rel.LEADER)) {
            FactionLeaders.addLeader(MPlayer.get(event.getPlayer()));
            ConfigUtils.addPermissions(MPlayer.get(event.getPlayer()));
        }
    }
}
