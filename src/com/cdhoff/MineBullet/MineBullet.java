package com.cdhoff.MineBullet;

import com.github.sheigutn.pushbullet.Pushbullet;
import com.github.sheigutn.pushbullet.items.push.sendable.SendablePush;
import com.github.sheigutn.pushbullet.items.push.sendable.defaults.SendableNotePush;
import com.google.common.base.Joiner;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.logging.Level;

public class MineBullet extends JavaPlugin implements Listener {

    private String apiToken = this.getConfig().getString("Access Token");

    private void createConfig() {

            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                getLogger().info("Config.yml not found, creating!");
                saveDefaultConfig();
            } else {
                getLogger().info("Config.yml found, loading!");
        }


    }
    @Override
    public void onEnable() {
        getLogger().info("Plugin Enabled");
        getServer().getPluginManager().registerEvents(this, this);
        createConfig();

    }



    @Override
    public void onDisable(){
        //Fired when the server stops and disables all plugins
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if(cmd.getName().equalsIgnoreCase("alert"))
        {
            if (args.length < 1)
                return false;

            if (sender.hasPermission("minebullet.alert"))
            {
                Pushbullet pushbullet = new Pushbullet(this.apiToken);
                StringBuilder builder = new StringBuilder();
                for (String arg : args) {
                    builder.append(arg + " ");
                }
                String msg = builder.toString();


                SendablePush note = new SendableNotePush("Minecraft", sender.getName() + " has sent a message: " + msg);
                pushbullet.push(note);
                sender.sendMessage("Your Message has been sent to an admin!");
            } else sender.sendMessage("§cYou don't have permission to broadcast!");
            return true;
        }
        return false;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent joinEvent)
    {
        if(this.getConfig().getBoolean("Push when player joins the game?")) {
            Player player = joinEvent.getPlayer();
                if(this.getConfig().getBoolean("debug"))
                    player.sendMessage("Hello, " + player.getName() + ". Minebullet is in debug mode!.");
            getLogger().log(Level.INFO, player.getName() + "  has joined, and Minebullet push on player leave enabled, pushing.");

            Pushbullet pushbullet = new Pushbullet(this.apiToken);
            SendablePush note = new SendableNotePush("Minecraft", player.getName() + " has joined.");

            try {
                pushbullet.push(note);
                if(this.getConfig().getBoolean("debug"))
                    getLogger().log(Level.INFO, "Successful Push");
            } catch (Exception e) {
                //getLogger().log(Level.SEVERE, ChatColor.RED + "An error has occured while trying to send the push. Have you updated the confing file yet?");
                getServer().getConsoleSender().sendMessage(ChatColor.RED + "An error has occured while trying to send the push. Have you updated the confing file yet?");
            }
        }
    }
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent leaveEvent) {
        if (this.getConfig().getBoolean("Push when player leaves the game?")) {
            Player player = leaveEvent.getPlayer();
            getLogger().log(Level.INFO, player.getName() + "  has left, and Minebullet push on player leave enabled, pushing.");
            Pushbullet pushbullet = new Pushbullet(this.apiToken);
            SendablePush note = new SendableNotePush("Minecraft", player.getName() + " has left.");
            try {
                pushbullet.push(note);
                if (this.getConfig().getBoolean("debug"))
                    getLogger().log(Level.INFO, "Successful Push");
            } catch (Exception e) {
                //getLogger().log(Level.SEVERE, ChatColor.RED + "An error has occured while trying to send the push. Have you updated the confing file yet?");
                getServer().getConsoleSender().sendMessage(ChatColor.RED + "An error has occured while trying to send the push. Have you updated the confing file yet?");
            }
        }
    }

}
