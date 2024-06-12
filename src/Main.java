import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static HashMap<String, User> myUsersList = new HashMap<>();
    public static Map<String, Password> myPasswordsList = new HashMap<>();
    private static final JFrame mainPage = new JFrame("Mano Main");
    private static User LoginUser;

    public Main() throws Exception {}

    public static Map<String, User> getMyMapInstance() {
        return myUsersList;
    }
    public static void setMyPasswordsList(Map<String, Password> myPasswordsList) {
        Main.myPasswordsList = myPasswordsList;
    }
    public static Map<String, Password> getMyPasswordsList() {
        return myPasswordsList;
    }
    public static JFrame getMainPage() {
        return mainPage;
    }
    public static void setLoginUser(User loginUser) {
        LoginUser = loginUser;
    }
    public static User getLoginUser() {
        return LoginUser;
    }

    private static final String AES_password = "Mypasswordyouwontguess";
    private static final String salt = "MySalt";
    private static final String USER_DIR = "users";

    // globally key
    private static final SecretKeySpec myKey;
    static {
        try {
            myKey = AES.getKeyFromPassword(AES_password, salt);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static SecretKeySpec getMyKey() {
        return myKey;
    }

    public static void main(String[] args) {

        File myDir = new File(USER_DIR);
        if (!myDir.exists()) {
            new File(USER_DIR).mkdir();
        }

        MySystem.loadUsers();
        // check all users
        myUsersList = new HashMap<>(MySystem.userMap);


        JFrame frame = new JFrame("Password Manager");
        mainPage.setSize(400, 300);
        mainPage.setContentPane(PW_Manage_Page.getInstance().getPanel(frame));
        mainPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPage.pack();
        mainPage.setVisible(true);
        System.out.println(myUsersList);


    }
}