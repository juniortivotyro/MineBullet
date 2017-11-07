package com.cdhoff.MineBullet;

import com.github.sheigutn.pushbullet.Pushbullet;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class MineBullet extends JavaPlugin implements Listener{

    private String apiToken = "o.KscEoYBLXucrfbM0zdokuyVKi7QglIwd";

    @Override
    public void onEnable(){
        getLogger().info("Plugin Enabled 1");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable(){
        //Fired when the server stops and disables all plugins
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        player.sendMessage("Hello, " + player.getName() + ". This is MineBullet testing.");
        getLogger().log(Level.INFO, "Player Has Joined");
            
        Pushbullet pushbullet = new Pushbullet(this.apiToken);
        SendablePush note = SendableNotePush("Minecraft", player.getName() + " has joined."); 
        pushbullet.push(note);
    }

}
