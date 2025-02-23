package CC14_Group_02_Assignment_2;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import static CC14_Group_02_Assignment_2.App.getFile;

@ExcludeFromJacocoGeneratedReport
public class LoginGUI {

    public static void main(VendingMachine vm) {


        JPanel panel = new JPanel();
        JFrame frame = new JFrame();
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(panel);

        panel.setLayout(null);

        JLabel userLabel = new JLabel("User");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField();
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        JLabel success = new JLabel("");
        success.setBounds(10, 110, 300, 25);
        panel.add(success);

        JButton loginButton = new JButton("login");
        loginButton.setBounds(10, 80, 80, 25);
        loginButton.addActionListener(e -> {
            String user = userText.getText();
            String password = passwordText.getText();
            String access = vm.getUserManager().loginUser(user,password);
            if (!access.equals("fail"))  {
                System.out.println("Login successful! Welcome " + user + "!");
                System.out.println("You can now type commands to interact with the vending machine");

                vm.setCurrentUser(user, access);
                frame.dispose();
                if(access.equals("seller")){
                    System.out.println("Press S to enter seller mode!!!");
//
                }
//                else{
//                DefaultPage dp = new DefaultPage();
//                dp.runPage(new Scanner(System.in), vm);
//                }


            } else {
                success.setText("Login failed! Please try again.");
            }
        });
        panel.add(loginButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(110, 80, 80, 25);
        backButton.addActionListener(e -> {
            frame.dispose();
            //DefaultPage dp = new DefaultPage();
            //dp.runPage(new Scanner(System.in), vm);
//            DefaultPageGUI.main(null);
        });
        panel.add(backButton);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(210, 80, 80, 25);
        registerButton.addActionListener(e -> {
            String user = userText.getText();
            String password = passwordText.getText();
            if (vm.getUserManager().registerUser(user,password,"user")) {
                System.out.println("User creation successful! Welcome " + user + "!");
                System.out.println("You can now type commands to interact with the vending machine");
                vm.setCurrentUser(user, "user");
                frame.dispose();
                //DefaultPage dp = new DefaultPage();
                //dp.runPage(new Scanner(System.in), vm);
            } else {
                success.setText("Login failed! Please try again.");
            }
        });
        panel.add(registerButton);

        frame.setVisible(true);
    }
}
