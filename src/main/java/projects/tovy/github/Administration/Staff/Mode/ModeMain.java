package projects.tovy.github.Administration.Staff.Mode;

import com.ibm.icu.text.Normalizer2;
import projects.tovy.github.Main;

public class ModeMain {

    private static final ModeMain instance = new ModeMain();
    private static final Main main = new Main();
    private static final ModeEvents me = new ModeEvents(instance, main);
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
