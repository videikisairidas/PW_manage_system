import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Random;
import java.util.stream.Collectors;

public class UserPage {
    private static UserPage instance;
    public static UserPage getInstance() {
        if (instance == null) {
            instance = new UserPage();
        }
        return instance;
    }

    private JPanel rightSide;
    private JTabbedPane UserPageTABS;
    private JPanel UserPanel;
    private JPanel createPasswordTAB;
    private JPanel deletePasswordTAB;
    private JPanel renewPasswordTAB;
    private JPanel search1TAB;
    private JPanel search2TAB;
    private JPanel generatePasswordTAB;

    private JButton generatePassword;
    private JButton addNewPasswordButton;
    private JTextField createName;
    private JTextField createPassword;
    private JTextField createLink;
    private JTextField createComment;
    private JButton deleteButton;
    private JTextField deleteByName;
    private JButton renewButton;
    private JTextField renewName;
    private JTextField renewPassword;
    private JTextField renewLink;
    private JTextField renewComment;
    private JTextField search1Name;
    private JTextField search1Password;
    private JTextField search1Link;
    private JTextField search1Comment;
    private JButton search1Button;
    private JTextField search2Name;
    private JButton search2Button;
    private JPasswordField search2Password;
    private JTextField search2Link;
    private JTextField search2Comment;
    private JButton search2ShowButton;
    private JButton search2CopyButton;


    public UserPage() {

        System.out.println(Main.getLoginUser());
        MySystem.loadInfo();

        addNewPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = createName.getText();
                String password = createPassword.getText();
                String link = createLink.getText();
                String comment = createComment.getText();
//                Main.getMyPasswordsList();
                if (!name.isEmpty() && !password.isEmpty() && !link.isEmpty()) {
                    Password createdPassword = new Password(name, password, link, comment);
                    Main.getMyPasswordsList().put(name, createdPassword);

                    String usernameFile = "users/" + Main.getLoginUser().getUsername() + ".csv";
                    File file = new File(usernameFile);

                    try {
                        AES.encryptPasswordsList(Main.getMyPasswordsList(), Main.getMyKey(), file);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }

                    createName.setText("");
                    createPassword.setText("");
                    createLink.setText("");
                    createLink.setText("");

                    System.out.println(Main.getMyPasswordsList());
                } else {
                    JOptionPane.showMessageDialog(UserPageTABS, "Please enter all the fields", "Error", JOptionPane.ERROR_MESSAGE);
                }

//                MySystem.loadInfo();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = deleteByName.getText();
                if (Main.getMyPasswordsList().get(name) != null) {
                    int confirmed = JOptionPane.showConfirmDialog(null,
                            "Are you sure you want Delete?", "Delete",
                            JOptionPane.YES_NO_OPTION);
                    if (confirmed == JOptionPane.YES_OPTION) {
                        Main.getMyPasswordsList().remove(name);

                        String usernameFile = "users/" + Main.getLoginUser().getUsername() + ".csv";
                        File file = new File(usernameFile);

                        try {
                            AES.encryptPasswordsList(Main.getMyPasswordsList(), Main.getMyKey(), file);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
//                        dispose();
                    }

                }
                System.out.println(Main.getMyPasswordsList());
            }
        });
        renewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = renewName.getText();
                String password = renewPassword.getText();
                String link = renewLink.getText();
                String comment = renewComment.getText();
                if (Main.getMyPasswordsList().get(name) != null && password != null && !name.isEmpty()) {
                    System.out.println("yes yra");
                    try {
                        Password myRenewUser = new Password(name, password, link, comment);
                        Main.getMyPasswordsList().put(name, myRenewUser);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }

                    int confirmed = JOptionPane.showConfirmDialog(null,
                            "Are you sure you RENEW", "Renew",
                            JOptionPane.YES_NO_OPTION);
                    if (confirmed == JOptionPane.YES_OPTION) {

                        String usernameFile = "users/" + Main.getLoginUser().getUsername() + ".csv";
                        File file = new File(usernameFile);

                        try {
                            AES.encryptPasswordsList(Main.getMyPasswordsList(), Main.getMyKey(), file);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(UserPageTABS, "Not Found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        search1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = search1Name.getText();
                if (Main.getMyPasswordsList().get(name) != null) {
                    String password0 = Main.getMyPasswordsList().get(name).password;
                    String link = Main.getMyPasswordsList().get(name).link;
                    String comment = Main.getMyPasswordsList().get(name).comment;
//                    System.out.println(link);
//                    System.out.println(comment);
                    search1Password.setText(password0);
                    search1Link.setText(link);
                    search1Comment.setText(comment);

                }

            }
        });
        search2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = search2Name.getText();
                if (Main.getMyPasswordsList().get(name) != null) {
                    String password = Main.getMyPasswordsList().get(name).password;
                    String link = Main.getMyPasswordsList().get(name).link;
                    String comment = Main.getMyPasswordsList().get(name).comment;
                    search2Password.setText(password);
                    search2Link.setText(link);
                    search2Comment.setText(comment);



                }
            }
        });
        search2ShowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = search2Name.getText();
                String password = Main.getMyPasswordsList().get(name).password;
                JOptionPane.showMessageDialog(null, "your password: " + password);
            }
        });
        search2CopyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = search2Name.getText();
                String password = Main.getMyPasswordsList().get(name).password;
                Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
                StringSelection strse1 = new StringSelection(password);
                clip.setContents(strse1, strse1);
                JOptionPane.showMessageDialog(null, "TEXTS ARE COPIED!");
            }
        });
        generatePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = new Random().ints(20, 33, 122).mapToObj(i -> String.valueOf((char)i)).collect(Collectors.joining());
                System.out.println(password);
//                JOptionPane.showMessageDialog(null, "password: " + password );
            }
        });



    }
    public JPanel getPanel() {
        return UserPanel;
    }
}
