package bank.ui;

import bank.database.DBUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddClientInterface extends JFrame implements ActionListener {

    private JLabel firstNameLabel, lastNameLabel, usernameLabel, passwordLabel;
    private JTextField firstNameField, lastNameField, usernameField;
    private JPasswordField passwordField;
    private JButton addButton;

    public AddClientInterface() {
        firstNameLabel = new JLabel("First Name:");
        lastNameLabel = new JLabel("Last Name:");
        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");

        firstNameField = new JTextField();
        lastNameField = new JTextField();
        usernameField = new JTextField();
        passwordField = new JPasswordField();

        addButton = new JButton("Add");

        setLayout(new GridLayout(5, 2));
        add(firstNameLabel);
        add(firstNameField);
        add(lastNameLabel);
        add(lastNameField);
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(new JLabel());
        add(addButton);

        addButton.addActionListener(this);

        setTitle("Add Client");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        DBUtils.addClient(firstName, lastName, username, password);
        JOptionPane.showMessageDialog(this, "User added successfully.");
        dispose();
    }
}
