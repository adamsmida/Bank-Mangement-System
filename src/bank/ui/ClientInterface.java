package bank.ui;

import bank.data.Account;
import bank.data.Client;
import bank.data.User;
import bank.database.DBUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ClientInterface extends JFrame implements ActionListener {
    private JLabel welcomeLabel;
    private JTable accountsTable;
    private JButton logoutButton;
    private User user;
    private JPanel topPanel;
    private List<Account> accounts;
    private Object[][] accountData;

    public ClientInterface() {
        this.user = DBUtils.currentUser;
        accounts = DBUtils.getClientAccounts(user.getId());
        accountData = new Object[accounts.size()][2];
        int i = 0;
        for (Account account : accounts) {
            accountData[i][0] = account.getRib();
            accountData[i][1] = account.getBalance();
            i++;
        }

        welcomeLabel = new JLabel("Welcome, " + user.getFirstName() + " " + user.getLastName() + "!");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 24));
        logoutButton = new JButton("Logout");
        topPanel = new JPanel();
        topPanel.add(welcomeLabel);
        accountsTable = new JTable(accountData, new String[]{"Account Number", "Balance"});
        accountsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        accountsTable.setDefaultEditor(Object.class, null); // make table not editable
        JScrollPane scrollPane = new JScrollPane(accountsTable);
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(logoutButton, BorderLayout.SOUTH);
        logoutButton.addActionListener(this);

        setTitle("Bank Management - Client Interface");
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logoutButton) {
            dispose();
            new LoginInterface();
        }
    }

}
