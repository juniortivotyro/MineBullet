package com.cdhoff.MineBullet;

import org.bukkit.plugin.java.JavaPlugin;

public class MineBullet extends JavaPlugin{

    @Override
    public void onEnable(){
        getLogger().severe("Plugin Loaded... v2");
    }
    @Override
    public void onDisable(){
        //Fired when the server stops and disables all plugins
    }

}


