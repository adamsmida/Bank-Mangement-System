package bank.ui;

import bank.data.Account;
import bank.database.DBUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DebitInterface extends JFrame implements ActionListener {
    private JLabel accountLabel, amountLabel;
    private JTextField accountField, amountField;
    private JButton debitButton;
    private Account account;

    public DebitInterface(Account account) {
        this.account = account;
        accountLabel = new JLabel("Account number:");
        amountLabel = new JLabel("Amount to debit:");
        amountField = new JTextField();
        accountField = new JTextField(account.getRib());
        accountField.setEditable(false);
        debitButton = new JButton("Debit");

        setLayout(new GridLayout(3, 2));
        add(accountLabel);
        add(accountField);
        add(amountLabel);
        add(amountField);
        add(new JLabel());
        add(debitButton);

        debitButton.addActionListener(this);

        setTitle("Debit Account");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        Long amount = Long.parseLong(amountField.getText());
        if (amount > 0) {
            if (amount > account.getBalance()) {
                JOptionPane.showMessageDialog(this, "Not enough funds in account!");
            } else {
                DBUtils.withdraw(account.getRib(), amount);
                account.debit(amount);
                JOptionPane.showMessageDialog(this, "Account debited successfully!");
                dispose();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Amount should be greater than 0!");
        }
    }
}
