package Services;

import Repository.UserRepository;

import java.sql.SQLException;

/**
 * Created by Cosmin on 3/28/2017.
 */
public class UserService {
    UserRepository urep;
    public UserService(UserRepository urep) {
        this.urep = urep;
    }
    public boolean canLogIn(String loginCode) throws SQLException {
        return urep.canLogIn(loginCode);
    }
}
