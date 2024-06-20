package projects.tovy.github.ServerUsage.KillEffects;

import projects.tovy.github.DataBase.KEDataBase;
import projects.tovy.github.EasyGuiBorder;
import projects.tovy.github.Main;

public class KeMain {
    private final Main main;
    private final KEDataBase ked;
    private final Effects effects;
    private final GUI gui;
    private final KeEvents keEvents;

    public KeMain(Main main, KEDataBase ked) {
        this.main = main;
        this.ked = ked;
        this.effects = new Effects(this, main);
        this.gui = new GUI(new EasyGuiBorder(), ked);
        this.keEvents = new KeEvents(ked, effects);
    }

    public Effects getEffects() {
        return effects;
    }

    public GUI getGUI() {
        return gui;
    }

    public KeEvents getEvents() {
        return keEvents;
    }
}
