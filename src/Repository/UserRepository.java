package Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
/**
 * Created by Musafir on 3/12/2017.
 */
public class UserRepository {
    Connection connection;
    public UserRepository(Connection connection) {
        this.connection = connection;
    }
    public boolean canLogIn(String loginCode) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from user where code=?");
        preparedStatement.setString(1, loginCode);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }
}
