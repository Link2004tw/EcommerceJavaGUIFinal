package com.example.ecommerceguiv2.Scenes;

import com.example.ecommerceguiv2.Models.Admin;
import com.example.ecommerceguiv2.Models.Customer;
import com.example.ecommerceguiv2.Models.Database;
import com.example.ecommerceguiv2.Models.Person;
import javafx.scene.Scene;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfilePage extends ScenePage {

    public ProfilePage(Database db, Person p){
        Scene s = null;

        // Create the frame
        JFrame frame = new JFrame("Profile Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);

        // Create a panel to hold all components
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add title label
        JLabel titleLabel = new JLabel("Your Profile", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer

        // Username field
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);
        panel.add(usernameLabel);
        panel.add(usernameField);

        // Password field
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        panel.add(passwordLabel);
        panel.add(passwordField);

        // Date of Birth field
        JLabel dobLabel = new JLabel("Date of Birth:");
        JTextField dobField = new JTextField(20);
        panel.add(dobLabel);
        panel.add(dobField);

        // Balance field
        JLabel balanceLabel = new JLabel("Balance:");
        JTextField balanceField = new JTextField(20);
        panel.add(balanceLabel);
        panel.add(balanceField);

        // Gender field
        JLabel genderLabel = new JLabel("Gender:");
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        panel.add(genderLabel);
        panel.add(genderComboBox);

        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer

        // Buttons for actions
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        // Panel to hold buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel);


        // Add action listeners for buttons
        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String dob = dobField.getText();
                String balance = balanceField.getText();
                String gender = (String) genderComboBox.getSelectedItem();

                if(db.isAdmin())
                {
                    db.update(Admin.class, p);
                }
                else{ db.update(Customer.class, p);}

                // Here you can add logic to save the profile information
                JOptionPane.showMessageDialog(frame, "Profile saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear all fields
                usernameField.setText("");
                passwordField.setText("");
                dobField.setText("");
                balanceField.setText("");
                genderComboBox.setSelectedIndex(0);
            }
        });

                @Override
                public void refresh()
        {
            System.out.println("Profile page refreshed!");
        }

        // Add the panel to the frame
        frame.add(panel);

        // Make the frame visible
        frame.setVisible(true);
        setScene(s);
    }


}
