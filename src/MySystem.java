import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySystem {
    private static final String fileName = "users.csv";
    public static Map<String, User> userMap;

    public static Map<String, User> getUserMap() {
        return userMap;
    }

    public static void loadUsers() {
        File file = new File(fileName);

        try {
//            SecretKeySpec key = AES.getKeyFromPassword(AES_password, salt);
            if (!file.exists()) {
                file.createNewFile();
                MySystem.userMap = new HashMap<>();
                User myuser = new User("airidas", "videikis");
                userMap.put(myuser.getUsername(), myuser);
                File file0 = new File("users/airidas.csv");
                file0.createNewFile();
                AES.encryptMapToFile(userMap, Main.getMyKey(), file);
            } else {
                try {
                    Map<String, User> decryptedMap = AES.decryptMapFromFile(Main.getMyKey(), file);
//                    System.out.println("Decrypted Map: " + decryptedMap);
                    MySystem.userMap = decryptedMap;
                    System.out.println(Main.myUsersList);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception _) {}


    }

//    public static void login(String username, String password) {
//        // Search for a user by username and password
//        String usernameToSearch = "airidas";
//        String passwordToCheck = "videikis";
//        User foundUser = userMap.get(usernameToSearch);
//
//        if (foundUser != null && foundUser.isPasswordCorrect(passwordToCheck)) {
//            System.out.println("Login successful: " + foundUser);
//        } else {
//            System.out.println("Invalid username or password.");
//        }
//    }

    public static void saveUsers() {
        File file = new File(fileName);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("users.csv"))) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadInfo() {
        String usernameFile = "users/" + Main.getLoginUser().getUsername() + ".csv";
        File file = new File(usernameFile);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            if (br.readLine() == null) {
                System.out.println("Empty");
            } else {
                System.out.println("NoT EMPTY");
                Main.setMyPasswordsList(AES.decryptPasswordsList(Main.getMyKey(), file));
                System.out.println(Main.getMyPasswordsList());
//                AES.encryptMapToFile(userMap, Main.getMyKey(), file);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        Main.getMyPasswordInstance();
//        MySystem.userMap = decryptedMap;

    }


}
