package projects.tovy.github.ServerUsage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import projects.tovy.github.Main;
import projects.tovy.github.PlayerUsage.Warps.WarpManager;

import java.util.Arrays;
import java.util.List;

public class ChatFilter implements Listener {
    private final List<String> bannedWords;
    private final FileConfiguration config;
    private final String p;
    private final String error;

    public ChatFilter(Main main) {

        this.config = main.getPluginConfig();
        this.p = config.getString("prefix");
        this.error = config.getString("errormsg");

        String[] bannedWordsArray = {
                "arse", "arsehead", "arsehole", "ass", "asshole", "asshead", "piss", "prick", "pussy",
                "slut", "sisterfucker", "spaz", "spastic", "turd", "twat", "wanker", "bastard", "bitch",
                "bloody", "bollocks", "brotherfucker", "bugger", "bullshit", "goddamn", "godsdamn", "hell",
                "shit", "motherfucker", "nigger", "nigga", "childfucker", "cock", "cocksucker", "crap", "cunt",
                "damn", "dick", "dickhead", "dyke", "fatherfucker", "frigger", "fuck", "fucker", "faggot", "fag",
                "kys", "k y s", "k.y.s", "fucking", "anal", "cum", "jizz", "cumlord", "niggas", "n1gger", "shite",
                "卐", "卍", "࿕࿖࿗࿘"
        };
//add more if needed
        bannedWords = Arrays.asList(bannedWordsArray);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player pl = e.getPlayer();
        String message = e.getMessage();


        for (String word : bannedWords) {
            if (message.toLowerCase().contains(word)) {
                e.setCancelled(true);
                pl.sendMessage(p + "You may not say that word");
                //may need to add a admin function to this
            }
        }
    }
}
