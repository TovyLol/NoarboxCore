package projects.tovy.github.ServerUsage.KillEffects;

import org.bukkit.plugin.java.JavaPlugin;
import projects.tovy.github.DataBase.KEDataBase;
import projects.tovy.github.EasyGuiBorder;
import projects.tovy.github.ItemHandeling;
import projects.tovy.github.Main;

public class KeMain {
    public ItemHandeling item;
    public EasyGuiBorder border;
    public Main main;
    public KEDataBase ked;

    public KeMain (ItemHandeling item, EasyGuiBorder border, Main main, KEDataBase ked) {
        this.border = border;
        this.item = item;
        this.main = main;
        this.ked = ked;
    }
    private final Effects eff = new Effects(this, main);
    private final GUI gui = new GUI(border, ked);
    private final KeEvents e = new KeEvents(ked, eff);

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
