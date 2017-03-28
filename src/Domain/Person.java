package Domain;

/**
 * Created by Musafir on 3/12/2017.
 */
public class Person {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    int id;
    String name;
    String extra;

    public Person(int id, String name, String extra) {
        this.id = id;
        this.name = name;
        this.extra = extra;
    }

    @Override
    public String toString() {
        return "Domain.Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", extra='" + extra + '\'' +
                '}';
    }
}
