package bank.ui;

import bank.data.User;
import bank.database.DBUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditUserInterface extends JFrame implements ActionListener {
    private User user;
    private JLabel usernameLabel, firstNameLabel, lastNameLabel, passwordLabel;
    private JTextField usernameField, firstNameField, lastNameField;
    private JPasswordField passwordField;
    private JButton saveButton, cancelButton;

    public EditUserInterface(Long id) {
        this.user=DBUtils.getUser(id);

        usernameLabel = new JLabel("Username:");
        firstNameLabel = new JLabel("First Name:");
        lastNameLabel = new JLabel("Last Name:");
        passwordLabel = new JLabel("Password:");

        usernameField = new JTextField(user.getUsername());
        firstNameField = new JTextField(user.getFirstName());
        lastNameField = new JTextField(user.getLastName());
        passwordField = new JPasswordField(user.getPassword());

        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        setLayout(new GridLayout(5, 2));
        add(usernameLabel);
        add(usernameField);
        add(firstNameLabel);
        add(firstNameField);
        add(lastNameLabel);
        add(lastNameField);
        add(passwordLabel);
        add(passwordField);
        add(saveButton);
        add(cancelButton);

        saveButton.addActionListener(this);
        cancelButton.addActionListener(this);

        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            String username = usernameField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String password = String.valueOf(passwordField.getPassword());

            if (!username.equals("") && !firstName.equals("") && !lastName.equals("") && !password.equals("")) {
                user.setUsername(username);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setPassword(password);
                // Save changes to the database
                DBUtils.updateUser(user);
                JOptionPane.showMessageDialog(this, "User details updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all the fields.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }
}
