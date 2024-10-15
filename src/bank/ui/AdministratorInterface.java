package bank.ui;

import bank.data.Client;
import bank.data.User;
import bank.database.DBUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdministratorInterface extends JFrame implements ActionListener {
    private JLabel titleLabel;
    private JTable clientsTable;
    private JButton editButton;
    private JButton manageAccountsButton;
    private JButton logoutButton;
    private JButton deleteButton;
    private JButton addClientButton;
    private List<Client> clients;

    public AdministratorInterface() {
        super("Bank Management System - Administrator Interface");
        setLayout(new BorderLayout());

        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titleLabel = new JLabel("All Clients");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel);

        // Clients Panel
        JPanel clientsPanel = new JPanel(new BorderLayout());
        clientsTable = new JTable();
        clientsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane clientsTableScrollPane = new JScrollPane(clientsTable);
        clientsPanel.add(clientsTableScrollPane, BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        editButton = new JButton("Edit Client");
        deleteButton = new JButton("Delete Client");
        manageAccountsButton = new JButton("Manage Accounts");
        logoutButton = new JButton("Logout");
        addClientButton = new JButton("Add Client");
        bottomPanel.add(addClientButton);
        bottomPanel.add(editButton);
        bottomPanel.add(manageAccountsButton);
        bottomPanel.add(deleteButton);
        bottomPanel.add(logoutButton);

        // Add components to the frame
        add(titlePanel, BorderLayout.NORTH);
        add(clientsPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Register action listeners
        editButton.addActionListener(this);
        manageAccountsButton.addActionListener(this);
        deleteButton.addActionListener(this);
        addClientButton.addActionListener(this);
        logoutButton.addActionListener(this);

        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        // Load clients data from the database and display in the table
        refreshClientsTable();
    }

    private void refreshClientsTable() {
        // Get all clients from the database
        clients = DBUtils.getClients();

        // Create table model with columns: ID, First Name, Last Name, Username
        String[] columns = {"ID", "First Name", "Last Name", "Username"};
        Object[][] data = new Object[clients.size()][4];
        for (int i = 0; i < clients.size(); i++) {
            User client = clients.get(i);
            data[i][0] = client.getId();
            data[i][1] = client.getFirstName();
            data[i][2] = client.getLastName();
            data[i][3] = client.getUsername();
        }
        DefaultTableModel tableModel = new DefaultTableModel(data, columns);

        // Set the table model to the clients table
        clientsTable.setModel(tableModel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == editButton) {
            int selectedRow = clientsTable.getSelectedRow();
            if (selectedRow >= 0) {
                User selectedClient = clients.get(selectedRow);
                EditUserInterface editUserInterface = new EditUserInterface(selectedClient.getId());
                editUserInterface.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        refreshClientsTable();
                    }
                });
            } else {
                JOptionPane.showMessageDialog(this, "Please select a client first.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == manageAccountsButton) {
            int selectedRow = clientsTable.getSelectedRow();
            if (selectedRow >= 0) {
                User selectedClient = clients.get(selectedRow);
                new AccountsInterface(selectedClient.getId());
            } else {
                JOptionPane.showMessageDialog(this, "Please select a client first.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == deleteButton) {
            int selectedRow = clientsTable.getSelectedRow();
            if (selectedRow >= 0) {
                User selectedClient = clients.get(selectedRow);
                int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this client?", "Delete Client", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    DBUtils.deleteClient(selectedClient.getId());
                    refreshClientsTable();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a client first.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == logoutButton) {
            dispose();
            new LoginInterface();
        } else if (e.getSource() == addClientButton) {
            AddClientInterface addClientInterface = new AddClientInterface();
            addClientInterface.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    refreshClientsTable();
                }
            });
        }
    }
}
