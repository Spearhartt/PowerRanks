package com.conquestiamc.listeners;

import com.conquestiamc.FactionLeaders;
import com.conquestiamc.config.ConfigUtils;
import com.massivecraft.factions.event.EventFactionsMembershipChange;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by Spearhartt on 2/11/2017.
 */
public class MembershipChangeListener implements Listener {

    @EventHandler
    public void onMembershipChange (EventFactionsMembershipChange event) {
        if (event.getReason() == EventFactionsMembershipChange.MembershipChangeReason.LEAVE) {
            if (FactionLeaders.isLeader(event.getMPlayer())) {
                ConfigUtils.removeAllPerms(event.getMPlayer());
            }
        }

        if (event.getReason() == EventFactionsMembershipChange.MembershipChangeReason.KICK) {
            if (FactionLeaders.isLeader(event.getMPlayer())) {
                ConfigUtils.removeAllPerms(event.getMPlayer());
            }
        }

        if (event.getReason() == EventFactionsMembershipChange.MembershipChangeReason.DISBAND) {
            if (FactionLeaders.isLeader(event.getMPlayer())) {
                ConfigUtils.removeAllPerms(event.getMPlayer());
            }
        }
    }
}
