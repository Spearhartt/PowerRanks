package com.conquestiamc;

import com.conquestiamc.config.Config;
import com.conquestiamc.config.ConfigUtils;
import com.conquestiamc.listeners.MembershipChangeListener;
import com.conquestiamc.listeners.PopulationChangeListener;
import com.conquestiamc.listeners.PowerChangeListener;
import com.conquestiamc.listeners.RankChangeListener;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.entity.MPlayerColl;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by Spearhartt on 2/6/2017.
 */
public class PowerRanks extends JavaPlugin {
    public static Logger log = Bukkit.getLogger();
    public static String label = "[PowerRanks] ";
    public static Permission perms = null;
    public static Plugin plugin = null;
    public static Config config = null;
    public static ConfigUtils configUtils = null;

    public void onEnable() {
        if(!setupPermissions()) {
            log.severe(label + "Disabled due to no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        plugin = this;
        config = new Config(this, "config");

        log.info(label + "Loading configuration.");
        config.reloadConfig();

        //Setup config information
        log.info(label + "Setting up configuration utilities.");
        configUtils = new ConfigUtils();
        configUtils.setupConfig();

        //Register Listeners
        log.info(label + "Registering listeners.");
        getServer().getPluginManager().registerEvents(new PopulationChangeListener(), this);
        getServer().getPluginManager().registerEvents(new PowerChangeListener(), this);
        getServer().getPluginManager().registerEvents(new RankChangeListener(), this);
        getServer().getPluginManager().registerEvents(new MembershipChangeListener(), this);

        //Add leaders to ArrayList
        new FactionLeaders().loadLeaders();
    }

    public void onDisable() {

    }

    public boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
}
