import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen extends JFrame {

    private JButton btnBuy = new JButton("Order View");
    private JButton btnSell = new JButton("Product View");

    public MainScreen(User user) {
        //User myUser = Application.getInstance().getCurrentUser();
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 300);

        btnSell.setPreferredSize(new Dimension(120, 50));
        btnBuy.setPreferredSize(new Dimension(120, 50));


        JLabel title = new JLabel("Store Management System");
        title.setFont(new Font("Sans Serif", Font.BOLD, 24));
        JPanel panelTitle = new JPanel();
        panelTitle.add(title);
        this.getContentPane().add(panelTitle);

        //String currUserID = String.valueOf(Application.getInstance().getCurrentUser().getUserID());
        //String currUserName = Application.getInstance().getCurrentUser().getUsername();
        //User myUser = Application.getInstance().getCurrentUser();
        //int myUserID = myUser.getUserID();
        //Application.getInstance().getCurrentUser().getFullName();
        //* JLabel user = new JLabel("Current User");
        //JLabel user = new JLabel("Current user: " + currName + "\n Username: " + currUserName + "\n User ID: " + currUserID); 
        //user.setFont(new Font("Sans Serif", Font.PLAIN, 15));

        String myUserID = String.valueOf(user.getUserID());
        String myUserName = user.getUsername();
        String myFullName = user.getFullName();
        //String myTest = "This is a test :()" + myUserID;
        //String userInfos = "Current user: " + myFullName; 
        JLabel user2 = new JLabel("Current user: " + myFullName);
        JPanel userInfo = new JPanel();
        userInfo.add(user2);
        this.getContentPane().add(userInfo);

        JLabel userNameLabel = new JLabel("Username: " + myUserName);
        JPanel userNameInfo = new JPanel();
        userNameInfo.add(userNameLabel);
        this.getContentPane().add(userNameInfo);

        JLabel userIDLabel = new JLabel("User ID: " + myUserID);
        JPanel userIDInfo = new JPanel();
        userIDInfo.add(userIDLabel);
        this.getContentPane().add(userIDInfo);

        JPanel panelButton = new JPanel();
        panelButton.add(btnBuy);
        panelButton.add(btnSell);

        this.getContentPane().add(panelButton);

        btnBuy.addActionListener(new ActionListener() { // when controller is simple, we can declare it on the fly
            public void actionPerformed(ActionEvent e) {
                Application.getInstance().getOrderView().setVisible(true);            }
        });

        //lambda fn for above
        btnBuy.addActionListener(
            e -> 
                Application.getInstance().getOrderView().setVisible(true)
            
    );   

        btnSell.addActionListener(new ActionListener() { // when controller is simple, we can declare it on the fly
            public void actionPerformed(ActionEvent e) {
                Application.getInstance().getProductView().setVisible(true);
            }
        });

        
        
    }



}
