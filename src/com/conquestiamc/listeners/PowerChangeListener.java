package com.conquestiamc.listeners;

import com.conquestiamc.FactionLeaders;
import com.conquestiamc.PowerRanks;
import com.conquestiamc.FactionRanks;
import com.conquestiamc.config.ConfigUtils;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsPowerChange;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by Spearhartt on 2/6/2017.
 */
public class PowerChangeListener implements Listener {
    public FactionRanks ranks = new FactionRanks();
    public ArrayList<Faction> fRanked = ranks.getPower();
    public ArrayList<Faction> preRanking = ranks.getPower();
    private static String label = PowerRanks.label;
    private static Logger log = PowerRanks.log;

    @EventHandler
    public void onFactionPowerChange (EventFactionsPowerChange event) {
        fRanked = ranks.getPower();

        if(!ranks.isDifferent(preRanking, fRanked)) {
            log.info("Ranks changed");
            for (MPlayer mPlayer : FactionLeaders.getLeaders()) {
                ConfigUtils.setPermissions(mPlayer, ranks.getOldRank(mPlayer.getFaction(), preRanking));
            }
            preRanking = fRanked;
        }

        log.info("Power listener");
    }
}
