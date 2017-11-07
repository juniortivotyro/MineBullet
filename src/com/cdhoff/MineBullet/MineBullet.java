package com.cdhoff.MineBullet;

import com.github.sheigutn.pushbullet.Pushbullet;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class MineBullet extends JavaPlugin implements Listener{


    @Override
    public void onEnable(){
        getLogger().info("Plugin Enabled 1");
        getServer().getPluginManager().registerEvents(this, this);
    }
    String apiToken = "o.KscEoYBLXucrfbM0zdokuyVKi7QglIwd\n";
    Pushbullet pushbullet = new Pushbullet(apiToken);

    @Override
    public void onDisable(){
        //Fired when the server stops and disables all plugins
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        getLogger().log(Level.INFO, "Player Has Joined");
        player.sendMessage("Hello, " + player.getName()+". This is MineBullet testing.");
        pushbullet.pushNote("Minecraft", " has joined");
    }

}