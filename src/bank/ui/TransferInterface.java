package bank.ui;

import bank.data.Account;
import bank.database.DBUtils;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.*;

public class TransferInterface extends JFrame implements ActionListener {
    private JLabel fromLabel, toLabel, amountLabel;
    private JComboBox<Account> fromComboBox, toComboBox;
    private JTextField amountField;

    private List<Account> accountList;
    private JButton transferButton;

    public TransferInterface() {
        accountList = DBUtils.getAccounts();
        JPanel mainPanel = new JPanel(new GridLayout(4, 2));
        fromLabel = new JLabel("From:");
        mainPanel.add(fromLabel);
        fromComboBox = new JComboBox<>(accountList.toArray(new Account[1]));
        mainPanel.add(fromComboBox);
        toLabel = new JLabel("To:");
        mainPanel.add(toLabel);
        toComboBox = new JComboBox<>(accountList.toArray(new Account[1]));
        mainPanel.add(toComboBox);
        amountLabel = new JLabel("Amount:");
        mainPanel.add(amountLabel);
        amountField = new JTextField();
        mainPanel.add(amountField);
        mainPanel.add(new JLabel());
        transferButton = new JButton("Transfer");
        mainPanel.add(transferButton);

        getContentPane().add(mainPanel, BorderLayout.CENTER);

        // set listeners
        transferButton.addActionListener(this);

        setTitle("Transfer Money");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void setAccounts(Account[] accounts) {
        fromComboBox.removeAllItems();
        toComboBox.removeAllItems();
        for (Account account : accounts) {
            fromComboBox.addItem(account);
            toComboBox.addItem(account);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == transferButton) {
            Account fromAccount = (Account) fromComboBox.getSelectedItem();
            Account toAccount = (Account) toComboBox.getSelectedItem();
            if(fromAccount==null || toAccount==null){
                JOptionPane.showMessageDialog(this, "Please select accounts!");
                return;
            }
            if (fromAccount.equals(toAccount)) {
                JOptionPane.showMessageDialog(this, "Cannot transfer to same account");
            } else {
                Long amount = Long.parseLong(amountField.getText());
                if (amount > 0) {
                    if (!fromAccount.canDebit(amount)) {
                        JOptionPane.showMessageDialog(this, "Insufficient funds");
                        return;
                    }
                    // perform transfer
                    DBUtils.deposit(toAccount.getRib(), amount);
                    DBUtils.withdraw(fromAccount.getRib(), amount);
                    fromAccount.debit(amount);
                    toAccount.credit(amount);
                    JOptionPane.showMessageDialog(this, "Transfer successful");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Amount should be greater than 0!");
                }
            }
        }
    }
}
