package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Cosmin on 3/27/2017.
 */
public class Database {
    private Connection connection;

    public void execute(String sql) throws SQLException {
        //System.out.println("Executing sql " + sql);
        connection.prepareStatement(sql);
    }

    public Database() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:" + "mpp.db");
    }

    public Connection getConnection() {
        return connection;
    }
}
