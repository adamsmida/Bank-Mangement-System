package bank.ui;

import bank.data.Account;
import bank.database.DBUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreditInterface extends JFrame implements ActionListener {
    private JLabel accountLabel, amountLabel;
    private JTextField accountField, amountField;
    private JButton creditButton;
    private Account account;

    public CreditInterface(Account account) {
        this.account = account;
        accountLabel = new JLabel("Account number:");
        amountLabel = new JLabel("Amount to credit:");
        accountField = new JTextField(account.getRib());
        accountField.setEditable(false);
        amountField = new JTextField();
        creditButton = new JButton("Credit");

        setLayout(new GridLayout(3, 2));
        add(accountLabel);
        add(accountField);
        add(amountLabel);
        add(amountField);
        add(new JLabel());
        add(creditButton);

        creditButton.addActionListener(this);
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == creditButton) {
            Long amount = Long.parseLong(amountField.getText());
            if (amount > 0) {
                DBUtils.deposit(account.getRib(), amount);
                account.credit(amount);
                JOptionPane.showMessageDialog(this, "Account credited successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Amount should be greater than 0!");
            }
        }
    }
}
