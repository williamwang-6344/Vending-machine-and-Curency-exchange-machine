package CC14_Group_02_Assignment_2;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@ExcludeFromJacocoGeneratedReport
public class TransactionUI {

    public static void main(VendingMachine vm,Item item,int quantitySelected) {

        JPanel panel = new JPanel();
        JFrame frame = new JFrame();
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(panel);

        panel.setLayout(null);

        JLabel cardLabel = new JLabel("Card number");
        cardLabel.setBounds(10, 20, 100, 25);
        panel.add(cardLabel);

        JTextField cardText = new JTextField(20);
        cardText.setBounds(120, 20, 165, 25);
        panel.add(cardText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 50, 100, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField();
        passwordText.setBounds(120, 50, 165, 25);
        panel.add(passwordText);

        JLabel success = new JLabel("");
        success.setBounds(10, 110, 300, 25);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.setBounds(10, 80, 80, 25);
        confirmButton.addActionListener(e -> {
            String cardNumber = cardText.getText();
            String password = passwordText.getText();
            boolean cardValidity = vm.getCardManager().checkCardDetail(cardNumber,password);
            if (cardValidity) {
                success.setText("Transaction successful!");
                System.out.println("Successfully purchased via card! You will be redirected to the main menu.");
                SellerPage.refillOrAdjust(item.getCategory(), Integer.toString(item.getCode()),item.getName(),Float.toString(item.getPrice()),Integer.toString(item.getQuantity()-quantitySelected),App.productDatabase);
                if (!Objects.equals(vm.getCurrentUser(), "")) {
                    int result = JOptionPane.showConfirmDialog(frame, "Would you like to save your card details for future purchases?", "Transaction Successful", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (result == JOptionPane.YES_OPTION) {
                        vm.getCardManager().saveCard(vm.getCurrentUser(), cardNumber);
                        JOptionPane.showMessageDialog(frame, "Card details saved successfully!");
                    } else if (result == JOptionPane.NO_OPTION) {
                        JOptionPane.showMessageDialog(frame, "Card details not saved.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Card details not saved.");
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                frame.dispose();
                //DefaultPage dp = new DefaultPage();
                //dp.runPage(new Scanner(System.in), vm);
            } else {
                success.setText("purchase failed! Please try again.");
            }
        });
        panel.add(confirmButton);
        panel.add(success);
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.setBounds(110, 80, 80, 25);
        backButton.addActionListener(e -> {
            frame.dispose();
            //#1 need to cancel to the transaction page with quantity and item inherited
            TransactionPage tp = new TransactionPage(item, quantitySelected );
            tp.runPage(new Scanner(System.in), vm);
//            DefaultPageGUI.main(null);
        });
        panel.add(backButton);


        frame.setVisible(true);
    }
}

