package com.cdhoff.MineBullet;

import com.github.sheigutn.pushbullet.Pushbullet;
import com.github.sheigutn.pushbullet.items.push.sendable.SendablePush;
import com.github.sheigutn.pushbullet.items.push.sendable.defaults.SendableNotePush;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.ViaAPI;


import java.io.File;
import java.util.logging.Level;

public class MineBullet extends JavaPlugin implements Listener {


    private String apiToken = this.getConfig().getString("token");
    private Pushbullet pushbullet = new Pushbullet(this.apiToken);



    private void createConfig() {

            if (!getDataFolder().exists())
                getDataFolder().mkdirs();

            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                getLogger().info("Config.yml not found, creating!");
                saveDefaultConfig();
            } else
                getLogger().info("Config.yml found, loading!");



    }
    @Override
    public void onEnable() {
        createConfig();
        getLogger().info("Plugin Enabled");
        getServer().getPluginManager().registerEvents(this, this);

    }


    @Override
    public void onDisable(){
        SendablePush note = new SendableNotePush("Minecraft", "Server is stopping!");
        pushbullet.push(note);
    }

    private void onCommandFail(Player player){
        player.sendMessage(ChatColor.AQUA + "MineBullet Help");
        player.sendMessage(ChatColor.AQUA + "------------");
        player.sendMessage(ChatColor.DARK_AQUA + "/minebullet " + ChatColor.AQUA +  " Main MineBullet Command / Help Menu");
        player.sendMessage(ChatColor.DARK_AQUA + "/minebullet reload " + ChatColor.AQUA +  " Reloads MineBullet Config");
        player.sendMessage(ChatColor.DARK_AQUA + "/alert <message>" + ChatColor.AQUA +  " Alerts an admin about an important matter");

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if(cmd.getName().equalsIgnoreCase("alert"))
        {

            if (args.length < 1) {
                Player p = (Player) sender;
                onCommandFail(p);
                return true;
            }
            if (sender.hasPermission("minebullet.alert"))
            {
                Pushbullet pushbullet = new Pushbullet(this.apiToken);
                StringBuilder builder = new StringBuilder();
                for (String arg : args) {
                    builder.append(arg + " ");
                }
                String msg = builder.toString();


                SendablePush note = new SendableNotePush(sender.getName() + " has sent a message",msg);
                pushbullet.push(note);
                sender.sendMessage("Your Message has been sent to an admin!");
            } else sender.sendMessage("§cYou don't have permission to alert an admin!!");
            return true;
        }
        if(args.length == 1) {
            if (label.equalsIgnoreCase("minebullet")) {
                /*if (args.length == 0) {
                    Player p = (Player) sender;
                    onCommandFail(p);
                    return false;
                }*/
                if (args[0].equalsIgnoreCase("reload")) {
                    try{
                        Bukkit.getPluginManager().getPlugin("MineBullet").reloadConfig();
                        sender.sendMessage(ChatColor.AQUA + "Config Reloaded");
                    }catch(Exception e){
                        sender.sendMessage(ChatColor.RED + "An error has occured");
                    }

                    return true;
                }
            }
        }
        Player p = (Player) sender;
        onCommandFail(p);
        return true;
    }


    public static String joinMessage = " has joined";


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent joinEvent) {
        //If joinPush is set to true...
        if (this.getConfig().getBoolean("joinPush")) {
            Player player = joinEvent.getPlayer();

                    if (getConfig().getBoolean("joinStatsPush")) {

                        try{
                            PlayerChecker.withViaVersion(joinEvent, player, pushbullet);
                        }catch (NoClassDefFoundError e){
                            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "An error has occured while trying to send the push. Is Viaversion installed? If not, either update the config or install ViaVersion");
                            try{
                                PlayerChecker.withoutViaVersion(joinEvent,player,pushbullet);
                            }catch (Exception f){
                                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "An error has occured while trying to send the push. Have you updated the confing file yet?");
                            }
                        }
                    }else{
                        try{
                            PlayerChecker.withoutViaVersion(joinEvent,player,pushbullet);
                        }catch (Exception f){
                            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "An error has occured while trying to send the push. Have you updated the confing file yet?");
                        }
                    }

        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent leaveEvent) {
        if (this.getConfig().getBoolean("leavePush")) {
            Player player = leaveEvent.getPlayer();
            getLogger().log(Level.INFO, player.getName() + "  has left, and Minebullet push on player leave enabled, pushing.");
            if(!player.isBanned()) {
                SendablePush note = new SendableNotePush("Minecraft", player.getName() + " has left.");
                try {
                    pushbullet.push(note);
                    if (this.getConfig().getBoolean("debug"))
                        getLogger().log(Level.INFO, "Successful Push");
                } catch (Exception e) {
                    getServer().getConsoleSender().sendMessage(ChatColor.RED + "An error has occured while trying to send the push. Have you updated the confing file yet?");
                }
            }else{
                if(this.getConfig().getBoolean("banPush")) {
                    SendablePush note = new SendableNotePush("Minecraft", player.getName() + " has been banned!");
                    try {
                        pushbullet.push(note);
                        if (this.getConfig().getBoolean("debug"))
                            getLogger().log(Level.INFO, "Successful Push");
                    } catch (Exception e) {
                        getServer().getConsoleSender().sendMessage(ChatColor.RED + "An error has occured while trying to send the push. Have you updated the confing file yet?");
                    }
                }
            }
        }
    }

}
