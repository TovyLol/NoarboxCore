package projects.tovy.github.Administration.Staff.Mode;

public class ModeMain {
    private static final ModeMain instance = new ModeMain();
    private static final ModeEvents me = new ModeEvents(instance);
    private static final ModeCommands mc = new ModeCommands();

    public static ModeMain getInstance() {
        return instance;
    }

    public ModeEvents getEvents() {
        return me;
    }

    public ModeCommands getCommands() {
        return mc;
    }
}
