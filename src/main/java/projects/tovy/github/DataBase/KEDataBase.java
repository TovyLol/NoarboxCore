package projects.tovy.github.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KEDataBase {
    private static final String DB_URL = "jdbc:sqlite:killeffects.db"; //path

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public void createTable() {

    }
}
