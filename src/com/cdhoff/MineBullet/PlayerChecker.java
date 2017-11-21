package com.cdhoff.MineBullet;

import com.github.sheigutn.pushbullet.Pushbullet;
import com.github.sheigutn.pushbullet.items.push.sendable.SendablePush;
import com.github.sheigutn.pushbullet.items.push.sendable.defaults.SendableNotePush;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.ViaAPI;

public class PlayerChecker {

        public static void withViaVersion (PlayerJoinEvent joinEvent, Player player, Pushbullet pushbullet){
        //Try using ViaVersion to get version
        //Various Declarations
        Player p = joinEvent.getPlayer();
        ViaAPI api = Via.getAPI();
        int ver = api.getPlayerVersion(player);
        //Change Join Message to include the version
        MineBullet.joinMessage = MineBullet.joinMessage + " with version " + Translator.translate(ver);
        //Define the note for pushing
        SendablePush note = new SendableNotePush("Minecraft", player.getName() + MineBullet.joinMessage);
        //Push the note
        pushbullet.push(note);

    }

    public static void withoutViaVersion(PlayerJoinEvent joinEvent, Player player, Pushbullet pushbullet) {
        //Define the note for pushing
        SendablePush note = new SendableNotePush("Minecraft", player.getName() + MineBullet.joinMessage);
        //Push the note
        pushbullet.push(note);
    }
}
