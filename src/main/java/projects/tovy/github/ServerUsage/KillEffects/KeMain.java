package projects.tovy.github.ServerUsage.KillEffects;

import projects.tovy.github.EasyGuiBorder;
import projects.tovy.github.ItemHandeling;

public class KeMain {
    public ItemHandeling item;
    public EasyGuiBorder border;

    public KeMain (ItemHandeling item, EasyGuiBorder border) {
        this.border = border;
        this.item = item;
    }
    private final Effects eff = new Effects(this);
    private final GUI gui = new GUI(item, border);
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
