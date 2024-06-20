package projects.tovy.github.Administration.Staff.Mode;

import projects.tovy.github.Main;

public class ModeMain {

    private final ModeEvents modeEvents;
    private final ModeCommands modeCommands;

    public ModeMain(Main main) {
        this.modeEvents = new ModeEvents(this, main);
        this.modeCommands = new ModeCommands();
    }

    public ModeEvents getModeEvents() {
        return modeEvents;
    }

    public ModeCommands getModeCommands() {
        return modeCommands;
    }
}