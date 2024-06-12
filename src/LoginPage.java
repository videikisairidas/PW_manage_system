import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

public class LoginPage {
    private static LoginPage instance;
    public static LoginPage getInstance() {
        if (instance == null) {
            instance = new LoginPage();
        }
        return instance;
    }

    private JLabel usernameLabel;
    private JTextField username;
    private JLabel passwordLabel;
    private JPasswordField password;
    JPanel loginPanel;
    private JButton loginButton;

    public LoginPage() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameText = username.getText();
                String passwordText = new String(password.getPassword());
                Map<String, User> usersMap = Main.getMyMapInstance();
                User foundUser = usersMap.get(usernameText);
                if (foundUser != null ) {
                    try {
                        if (foundUser.checkPassword(passwordText)) {
                            System.out.println("password Correct");
                            Main.setLoginUser(foundUser);

                            Main.getMainPage().getContentPane().removeAll();
                            Main.getMainPage().setContentPane(UserPage.getInstance().getPanel());
                            Main.getMainPage().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            Main.getMainPage().pack();
                            Main.getMainPage().setVisible(true);
//                            System.out.println(Main.getMyMapInstance());
                        } else {
                            System.out.println("password Incorrect");
                        }
                    } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    username.setText("");
                    password.setText("");
                    JOptionPane.showMessageDialog(null, "try again" );
                }
//                JOptionPane.showMessageDialog(null, "username:\nName: " + usernameText + "\npassword: " + passwordText );
            }
        });
    }

    public JPanel getPanel() {
        return loginPanel;
    }
}

