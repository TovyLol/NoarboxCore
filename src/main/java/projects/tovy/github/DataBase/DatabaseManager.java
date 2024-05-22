package projects.tovy.github.DataBase;

import projects.tovy.github.DataBase.WarpDatabase;

public class DatabaseManager {

    private static final WarpDatabase wdb = new WarpDatabase();
    // Add more databases as needed
    /**
     * @return The {@link WarpDatabase} instance
     */
    public WarpDatabase getWarpDatabase() {
        return wdb;
    }


}
