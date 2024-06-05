package projects.tovy.github.ServerUsage.KillEffects;

import org.bukkit.plugin.java.JavaPlugin;
import projects.tovy.github.EasyGuiBorder;
import projects.tovy.github.ItemHandeling;
import projects.tovy.github.Main;

public class KeMain {
    public ItemHandeling item;
    public EasyGuiBorder border;
    public Main main;

    public KeMain (ItemHandeling item, EasyGuiBorder border, Main main) {
        this.border = border;
        this.item = item;
        this.main = main;
    }
    private final Effects eff = new Effects(this, main);
    private final GUI gui = new GUI(border);
    private final KeEvents e = new KeEvents();

    public Effects getEffects() {
        return eff;
    }
    public GUI getGUI(){
        return gui;
    }
    public KeEvents getEvents() {
        return e;
    }
}
