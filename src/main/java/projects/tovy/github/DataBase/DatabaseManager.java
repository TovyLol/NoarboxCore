package projects.tovy.github.DataBase;

public class DatabaseManager {

    private static final WarpDatabase wdb = new WarpDatabase();
    private static final ShulkerDataBase sdb = new ShulkerDataBase();
    private static final DsDataBase dsdb = new DsDataBase();
    private static final KEDataBase ked = new KEDataBase();

    /**
     * @return The {@link WarpDatabase} instance
     */
    public WarpDatabase getWarpDatabase() {
        return wdb;
    }

    /**
     * @return The {@link ShulkerDataBase} instance
     */
    public ShulkerDataBase getShulkerDataBase() {
        return sdb;
    }

    /**
     * @return The {@link DsDataBase} instance
     */
    public DsDataBase getDsDataBase() {
        return dsdb;
    }
    /**
            * @return The {@link KEDataBase} instance
    */
    public KEDataBase getKEDatabase(){
        return ked;
    }
}
