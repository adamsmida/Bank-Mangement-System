package bank.ui;

import bank.data.Account;
import bank.data.User;
import bank.database.DBUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class BankerInterface extends JFrame implements ActionListener {
    private JTable accountTable;
    private JButton searchButton,logoutButton, clearSearchButton, creditButton, debitButton, transferButton;
    private JTextField searchField;
    private DefaultTableModel tableModel;
    private List<Account> accountList;
    private User user;

    public BankerInterface() {
        // Set window title
        setTitle("Banker Interface");
        this.user = DBUtils.currentUser;
        // Create components
        accountList = DBUtils.getAccounts();
        searchField = new JTextField();
        searchButton = new JButton("Search");
        clearSearchButton = new JButton("Clear Search");
        creditButton = new JButton("Credit");
        debitButton = new JButton("Debit");
        transferButton = new JButton("Transfer");
        accountTable = new JTable();

        // Add components to content pane
        Container container = getContentPane();
        container.setLayout(new BorderLayout(10, 10));
        JPanel searchPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(clearSearchButton);
        JLabel welcomeLabel = new JLabel("Welcome, " + user.getFirstName() + " " + user.getLastName() + "!");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 24));
        logoutButton = new JButton("Logout");
        JPanel welcomePanel = new JPanel();
        welcomePanel.add(welcomeLabel);
        JPanel topPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        topPanel.add(welcomePanel);
        topPanel.add(searchPanel);
        container.add(topPanel, BorderLayout.NORTH);
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        tableModel.addColumn("Account Number");
        tableModel.addColumn("Client Name");
        tableModel.addColumn("Balance");
        accountList.forEach(account -> tableModel.addRow(
                new String[]{
                        account.getRib(),
                        account.getFirstName() + " " + account.getLastName(),
                        account.getBalance().toString()
                }));
        accountTable.setModel(tableModel);
        accountTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(accountTable);
        container.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(creditButton);
        bottomPanel.add(debitButton);
        bottomPanel.add(transferButton);
        bottomPanel.add(logoutButton);
        container.add(bottomPanel, BorderLayout.SOUTH);

        // Set button actions
        searchButton.addActionListener(this);
        clearSearchButton.addActionListener(this);
        creditButton.addActionListener(this);
        debitButton.addActionListener(this);
        transferButton.addActionListener(this);
        logoutButton.addActionListener(this);

        // Set frame properties
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            searchAccount();
        } else if (e.getSource() == creditButton) {
            displayCreditInterface();
        } else if (e.getSource() == debitButton) {
            displayDebitInterface();
        } else if (e.getSource() == transferButton) {
            displayTransferInterface();
        } else if (e.getSource() == clearSearchButton) {
            searchField.setText("");
            refreshTable();
        }  if (e.getSource() == logoutButton) {
            dispose();
            new LoginInterface();
        }
    }

    private void displayTransferInterface() {
        TransferInterface transferInterface = new TransferInterface();
        transferInterface.setVisible(true);
        transferInterface.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                refreshTable();
            }
        });
    }

    private void displayDebitInterface() {
        int selectedRow = accountTable.getSelectedRow();
        if (selectedRow != -1) {
            TableModel model = accountTable.getModel();
            String accountRib = model.getValueAt(selectedRow, 0).toString();
            Optional<Account> optionalAccount = accountList.stream().filter(account -> account.getRib().equals(accountRib)).findFirst();
            if (optionalAccount.isPresent()) {
                Account account = optionalAccount.get();
                DebitInterface displayDebitInterface = new DebitInterface(account);
                displayDebitInterface.setVisible(true);
                displayDebitInterface.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        refreshTable();
                    }
                });
            }
        }
    }

    private void displayCreditInterface() {
        int selectedRow = accountTable.getSelectedRow();
        if (selectedRow != -1) {
            TableModel model = accountTable.getModel();
            String accountRib = model.getValueAt(selectedRow, 0).toString();
            Optional<Account> optionalAccount = accountList.stream().filter(account -> account.getRib().equals(accountRib)).findFirst();
            if (optionalAccount.isPresent()) {
                Account account = optionalAccount.get();
                CreditInterface creditInterface = new CreditInterface(account);
                creditInterface.setVisible(true);
                creditInterface.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        refreshTable();
                    }
                });
            }
        }
    }

    private void refreshTable() {
        accountList = DBUtils.getAccounts();
        tableModel.setRowCount(0);
        accountList.forEach(account -> tableModel.addRow(
                new String[]{
                        account.getRib(),
                        account.getFirstName() + " " + account.getLastName(),
                        account.getBalance().toString()
                }));
    }

    public void searchAccount() {
        String accountRib = searchField.getText();
        accountList = DBUtils.searchAccount(accountRib);
        tableModel.setRowCount(0);
        accountList.forEach(account -> tableModel.addRow(
                new String[]{
                        account.getRib(),
                        account.getFirstName() + " " + account.getLastName(),
                        account.getBalance().toString()
                }));
    }
}
