package Services;

import Domain.Person;
import Repository.PersonRepository;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Cosmin on 3/28/2017.
 */
public class PersonService {
    PersonRepository prep;
    public PersonService(PersonRepository prep) {
        this.prep = prep;
    }
    public List<Person> getAll() throws SQLException {
        return prep.getAll();
    }
    public void add(String name, String extra) throws SQLException {
        prep.add(name, extra);
    }
    public void delete(int id) throws SQLException {
        prep.delete(id);
    }
    public void update(int id, String name, String extra) throws SQLException {
        prep.update(id, name, extra);
    }
    public Person getPerson(String name) throws SQLException {
        return prep.getPerson(name);
    }
    public Person getPerson(int id) throws SQLException {
        return prep.getPerson(id);
    }
}
