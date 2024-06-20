package projects.tovy.github.Administration.Staff.Logging;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import projects.tovy.github.Administration.Staff.Mode.ModeMain;
import projects.tovy.github.Main;

import java.util.Arrays;
import java.util.List;

public class CommandLogging implements Listener {
    private Main main;
    private ModeMain mmain;
    private List<String> coms = Arrays.asList("/op", "/sm", "/staffmode", "/gamemode creative");

    public CommandLogging(Main main, ModeMain mmain) {
        this.main = main;
        this.mmain = mmain;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String c = e.getMessage();

        for (String command : coms) {
            if (c.startsWith(command)) {
                String webhookUrl = "https://discord.com/api/webhooks/1251270168131141685/65vr3RRpv4wAvb_kMf7STEt_HTXMlchrB3sXlFMQZBz84j1dgB9-PzdHEgkblMYI4kf1";
                String title = "";
                String message = "";

                if (command.equals("/sm") || command.equals("/staffmode")) {
                    if (mmain != null && !mmain.getModeCommands().isEnabled(p)) {
                        title = "Staffmode Alert";
                        message = p.getName() + " went into Staffmode";
                    }
                } else if (command.equals("/op")) {
                    if (!p.getName().equals("2b2tbase_alt")) {
                        title = "Operator Alert";
                        message = p.getName() + " got op";
                    }
                } else if (command.equals("/gamemode creative")) {
                    title = "Gamemode Creative Alert";
                    message = p.getName() + " changed to Creative mode";
                }

                if (!title.isEmpty() && !message.isEmpty()) {
                    main.sendWebHook(webhookUrl, message, title, "undefined", "");
                }
                break;
            }
        }
    }
}