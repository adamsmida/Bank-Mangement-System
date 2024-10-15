package bank.ui;

import bank.data.Account;
import bank.data.User;
import bank.database.DBUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AccountsInterface extends JFrame implements ActionListener {

    private JTable accountsTable;
    private JScrollPane tableScrollPane;

    private DefaultTableModel tableModel;
    private JButton addAccountButton, deleteAccountButton;
    private List<Account> accounts;
    private Long userId;
    private User user;

    public AccountsInterface(Long id) {
        this.userId = id;
        this.user = DBUtils.getUser(id);
        this.accounts = DBUtils.getClientAccounts(id);
        String[] columns = {"RIB", "Client Name", "Balance"};
        Object[][] data = new Object[accounts.size()][3];
        for (int i = 0; i < accounts.size(); i++) {
            Account account = accounts.get(i);
            data[i][0] = account.getRib();
            data[i][1] = account.getFirstName() + " " + account.getLastName();
            data[i][2] = account.getBalance();
        }
        tableModel = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        accountsTable = new JTable(tableModel);
        accountsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        accountsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableScrollPane = new JScrollPane(accountsTable);
        tableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addAccountButton = new JButton("Add Account");
        deleteAccountButton = new JButton("Delete Account");
        bottomPanel.add(addAccountButton);
        bottomPanel.add(deleteAccountButton);
        add(tableScrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        addAccountButton.addActionListener(this);
        deleteAccountButton.addActionListener(this);

        setTitle("Accounts - " + user.getFirstName() + " " + user.getLastName());
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addAccountButton) {
            AddAccountInterface addAccountInterface = new AddAccountInterface(this.userId);
            addAccountInterface.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    accounts = DBUtils.getClientAccounts(userId);
                    refreshTable();
                }
            });
        } else if (e.getSource() == deleteAccountButton) {
            int selectedRow = accountsTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select an account.");
                return;
            }
            //add confirmation dialog

            int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this account?", "Delete Account", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                String rib = accountsTable.getValueAt(selectedRow, 0).toString();
                DBUtils.deleteAccount(rib);
                JOptionPane.showMessageDialog(this, "Account deleted successfully.");
                accounts = DBUtils.getClientAccounts(userId);
                refreshTable();
            }


        }
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        accounts.forEach(account -> tableModel.addRow(
                new String[]{
                        account.getRib(),
                        account.getFirstName() + " " + account.getLastName(),
                        account.getBalance().toString()
                }));
    }
}
