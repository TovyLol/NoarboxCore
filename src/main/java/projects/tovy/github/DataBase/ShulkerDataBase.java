package projects.tovy.github.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ShulkerDataBase {

    private static final String DB_URL = "jdbc:sqlite:shulker.db"; //path

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}
