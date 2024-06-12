import java.io.Serializable;

public class Password implements Serializable {
    String name;
    String password;
    String link;
    String comment;

    public Password(String name, String password, String link, String comment) {
        this.name = name;
        this.password = password;
        this.link = link;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return name + " " + password + " " + link + " " + comment + "\n";
    }
}
