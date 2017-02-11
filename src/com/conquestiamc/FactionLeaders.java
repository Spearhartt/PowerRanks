package com.conquestiamc;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.entity.MPlayerColl;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by Spearhartt on 2/11/2017.
 */
public class FactionLeaders {
    public static ArrayList<MPlayer> leaders = new ArrayList<>();
    public static Logger log = PowerRanks.log;

    public void loadLeaders() {
        leaders = new ArrayList<>();
        for (MPlayer mPlayer : MPlayerColl.get().getAll()) {
            if (mPlayer.getRole() == Rel.LEADER) {
                leaders.add(mPlayer);
            }

            if (mPlayer.getRole() != Rel.LEADER && leaders.contains(mPlayer)) {
                leaders.remove(mPlayer);
            }
        }
    }

    public static void removeLeader(MPlayer mPlayer) {
        if (leaders.contains(mPlayer)) {
            leaders.remove(mPlayer);
        }
    }

    public static ArrayList<MPlayer> getLeaders() {
        return leaders;
    }

    public static void addLeader(MPlayer mPlayer) {
        if (!leaders.contains(mPlayer)) {
            leaders.add(mPlayer);
        }
    }

    public static boolean isLeader(MPlayer mPlayer) {
        return leaders.contains(mPlayer);
    }
}
