import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PW_Manage_Page extends JPanel {
    private static PW_Manage_Page instance;
    public static PW_Manage_Page getInstance() {
        if (instance == null) {
            instance = new PW_Manage_Page();
        }
        return instance;
    }

//    private JFrame mainFrame;

    private JPanel mainPanel;
    private JButton registerButton;
    private JButton loginButton;
    private JButton button1;
    private JPanel leftSide;


    public PW_Manage_Page() {

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.getMainPage().getContentPane().removeAll();
                Main.getMainPage().setContentPane(RegisterPage.getInstance().getPanel());
                Main.getMainPage().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Main.getMainPage().pack();
                Main.getMainPage().setVisible(true);
//                JOptionPane.showMessageDialog(null, "uggggg" );
            }

        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.getMainPage().getContentPane().removeAll();
                Main.getMainPage().setContentPane(LoginPage.getInstance().getPanel());
                Main.getMainPage().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Main.getMainPage().pack();
                Main.getMainPage().setVisible(true);
//                JOptionPane.showMessageDialog(null, "uggggg" );
            }
        });

    }

    public JPanel getPanel(JFrame frame) {
//        this.mainFrame = frame;

        return mainPanel;
    }
}