package bank.ui;

import bank.data.User;
import bank.database.DBUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddAccountInterface extends JFrame implements ActionListener {

    private JLabel balanceLabel;
    private JTextField balanceField;
    private JButton addButton;
    private Long clientId;
    private User user;

    public AddAccountInterface(Long id) {
        this.clientId=id;
        this.user=DBUtils.getUser(id);
        balanceLabel = new JLabel("Initial Balance:");
        balanceField = new JTextField();
        addButton = new JButton("Add");

        setLayout(new GridLayout(2, 2));
        add(balanceLabel);
        add(balanceField);
        add(new JLabel());
        add(addButton);

        addButton.addActionListener(this);

        setTitle("Add Account for "+user.getFirstName()+" "+user.getLastName());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setSize(400,200);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        Long balance = Long.parseLong(balanceField.getText());
        DBUtils.addAccount(clientId, balance);
        JOptionPane.showMessageDialog(this, "Account added successfully.");
        dispose();
    }
}
