import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class RegisterPage {
    private static RegisterPage instance;
    public static RegisterPage getInstance() {
        if (instance == null) {
            instance = new RegisterPage();
        }
        return instance;
    }

    private JFrame mainFrame;

    private JPanel RegisterPanel;
    private JButton registerButton;
    private JPasswordField password;
    private JTextField username;


    private JLabel usernameLabel;

//    public RegisterPanel {
//        myPanel= new JPanel();
//    }

    public RegisterPage() {
//        RegisterPanel = new JPanel();
//        RegisterPanel.add
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameText = username.getText();
                String passwordText = new String(password.getPassword());

                Map<String, User> usersMap = Main.getMyMapInstance();
                User foundUser = usersMap.get(usernameText);

                if (foundUser != null ) {
                    System.out.println("This User already exist try again: " + usernameText);
                    JOptionPane.showMessageDialog(null, "This User already exist try again: " + usernameText );
                    username.setText("");
                    password.setText("");
                } else {
                    // add user to list
                    String encryptedPassword = passwordText;
                    User createdUser = null;
                    try {
                        createdUser = new User(usernameText,encryptedPassword);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    usersMap.put(usernameText, createdUser);
                    Map<String, User> userMap = null;
                    // encrypt file users folder
                    String fileName = "users/" + usernameText + ".csv";
                    File csvFile = new File(fileName);
                    try {
                        csvFile.createNewFile();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
//                    try {
//                        AES.encryptMapToFile(userMap, Main.getMyKey(), csvFile);
//                    } catch (Exception ex) {
//                        throw new RuntimeException(ex);
//                    }
                    // encrypt system file
                    File systemFile = new File("users.csv");
                    try {
                        AES.encryptMapToFile(usersMap, Main.getMyKey(), systemFile);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }

                    JOptionPane.showMessageDialog(null, "registering was succesfulf as " + usernameText );
                    System.out.println("registered user: " + usernameText);

//                    Map<String, User> usersMap = Main.getMyMapInstance();
                    foundUser = usersMap.get(usernameText);
                    Main.setLoginUser(foundUser);
                    Main.getMainPage().getContentPane().removeAll();
                    Main.getMainPage().setContentPane(UserPage.getInstance().getPanel());
                    Main.getMainPage().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    Main.getMainPage().pack();
                    Main.getMainPage().setVisible(true);
//                    System.out.println(Main.getMyMapInstance());
                }


            }
        });
    }
    public JPanel getPanel() {
        return RegisterPanel;
    }

}
