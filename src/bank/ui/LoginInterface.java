package bank.ui;

import bank.data.Role;
import bank.database.DBUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginInterface extends JFrame implements ActionListener {
    private JLabel usernameLabel, passwordLabel, roleLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JRadioButton clientRadioButton, bankerRadioButton, adminRadioButton;
    private ButtonGroup radioButtonGroup;

    public LoginInterface() {

        setTitle("Bank Management System");
        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        roleLabel = new JLabel("Select role:");
        clientRadioButton = new JRadioButton("Client");
        bankerRadioButton = new JRadioButton("Banker");
        adminRadioButton = new JRadioButton("Administrator");
        clientRadioButton.setActionCommand("CLIENT");
        bankerRadioButton.setActionCommand("BANKER");
        adminRadioButton.setActionCommand("ADMINISTRATOR");
        radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(clientRadioButton);
        radioButtonGroup.add(bankerRadioButton);
        radioButtonGroup.add(adminRadioButton);
        setLayout(new GridLayout(5, 2));
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(roleLabel);
        JPanel radioPanel = new JPanel(new GridLayout(1, 3));
        radioPanel.add(clientRadioButton);
        radioPanel.add(bankerRadioButton);
        radioPanel.add(adminRadioButton);
        add(radioPanel);
        add(new JLabel());
        add(loginButton);

        loginButton.addActionListener(this);

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (radioButtonGroup.getSelection() == null || username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill the form.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                DBUtils.login(username, password, radioButtonGroup.getSelection().getActionCommand());
                this.dispose();
                switch (Role.valueOf(radioButtonGroup.getSelection().getActionCommand())) {
                    case CLIENT -> {
                        ClientInterface clientInterface = new ClientInterface();
                        clientInterface.setVisible(true);
                    }
                    case BANKER -> {
                        BankerInterface bankerInterface = new BankerInterface();
                        bankerInterface.setVisible(true);
                    }
                    case ADMINISTRATOR -> {
                        AdministratorInterface administratorInterface = new AdministratorInterface();
                        administratorInterface.setVisible(true);
                    }
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
