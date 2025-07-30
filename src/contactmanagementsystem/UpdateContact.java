package contactmanagementsystem; // Ensure this package name is consistent across all files

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateContact extends JFrame implements ActionListener{

    JTextField tfidentifier; // For searching by name or phone
    JTextField tfname, tfphone, tfemail, tfaddress, tfcategory;
    JButton fetchButton, updateButton;
    Choice searchBy; // Dropdown for search criteria

    public UpdateContact() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel heading = new JLabel("UPDATE CONTACT DETAILS");
        heading.setBounds(180, 20, 450, 35);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 32));
        heading.setForeground(Color.BLUE);
        add(heading);

        JLabel lblsearch = new JLabel("Search By:");
        lblsearch.setBounds(60, 80, 150, 25);
        lblsearch.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblsearch);

        searchBy = new Choice();
        searchBy.setBounds(220, 80, 150, 25);
        searchBy.add("Name");
        searchBy.add("Phone");
        add(searchBy);

        tfidentifier = new JTextField();
        tfidentifier.setBounds(220, 120, 150, 25);
        add(tfidentifier);

        fetchButton = new JButton("Fetch Contact");
        fetchButton.setBackground(Color.BLACK);
        fetchButton.setForeground(Color.WHITE);
        fetchButton.setBounds(380, 100, 120, 25);
        fetchButton.addActionListener(this); // Register action listener
        add(fetchButton);

        // Labels and TextFields for displaying/editing contact details
        JLabel lblname = new JLabel("Name");
        lblname.setBounds(60, 170, 150, 25);
        lblname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblname);

        tfname = new JTextField();
        tfname.setBounds(220, 170, 150, 25);
        add(tfname);

        JLabel lblphone = new JLabel("Phone");
        lblphone.setBounds(60, 220, 150, 25);
        lblphone.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblphone);

        tfphone = new JTextField();
        tfphone.setBounds(220, 220, 150, 25);
        add(tfphone);

        JLabel lblemail = new JLabel("Email");
        lblemail.setBounds(60, 270, 150, 25);
        lblemail.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblemail);

        tfemail = new JTextField();
        tfemail.setBounds(220, 270, 150, 25);
        add(tfemail);

        JLabel lbladdress = new JLabel("Address");
        lbladdress.setBounds(60, 320, 150, 25);
        lbladdress.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lbladdress);

        tfaddress = new JTextField();
        tfaddress.setBounds(220, 320, 150, 25);
        add(tfaddress);

        JLabel lblcategory = new JLabel("Category");
        lblcategory.setBounds(60, 370, 150, 25);
        lblcategory.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblcategory);

        tfcategory = new JTextField();
        tfcategory.setBounds(220, 370, 150, 25);
        add(tfcategory);

        updateButton = new JButton("UPDATE CONTACT");
        updateButton.setBackground(Color.BLACK);
        updateButton.setForeground(Color.WHITE);
        updateButton.setBounds(220, 420, 180, 30);
        updateButton.addActionListener(this); // Register action listener
        updateButton.setEnabled(false); // Disable update button initially
        add(updateButton);

        setSize(700, 550);
        setLocation(350, 100);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == fetchButton) {
            String identifier = tfidentifier.getText().trim();
            String selectedSearchBy = searchBy.getSelectedItem();

            if (identifier.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a value to search.");
                // Clear fields and disable update button if identifier is empty
                clearContactFields();
                updateButton.setEnabled(false);
                return;
            }

            try {
                Conn conn = new Conn();
                String query = "";

                // Construct query based on search criterion
                if (selectedSearchBy.equals("Name")) {
                    query = "SELECT * FROM contacts WHERE name = '" + identifier + "'";
                } else if (selectedSearchBy.equals("Phone")) {
                    query = "SELECT * FROM contacts WHERE phone = '" + identifier + "'";
                }

                ResultSet rs = conn.s.executeQuery(query);

                if (rs.next()) {
                    // Populate fields with fetched data
                    tfname.setText(rs.getString("name"));
                    tfphone.setText(rs.getString("phone"));
                    tfemail.setText(rs.getString("email"));
                    tfaddress.setText(rs.getString("address"));
                    tfcategory.setText(rs.getString("category"));
                    updateButton.setEnabled(true); // Enable update button
                } else {
                    JOptionPane.showMessageDialog(null, "No contact found with the provided details.");
                    clearContactFields(); // Clear fields if no contact found
                    updateButton.setEnabled(false); // Disable update button
                }
                rs.close();
                conn.c.close();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error fetching contact: " + e.getMessage());
                clearContactFields();
                updateButton.setEnabled(false);
            }
        } else if (ae.getSource() == updateButton) {
            String originalIdentifier = tfidentifier.getText().trim(); // The identifier used for fetching
            String selectedSearchBy = searchBy.getSelectedItem();

            String name = tfname.getText().trim();
            String phone = tfphone.getText().trim();
            String email = tfemail.getText().trim();
            String address = tfaddress.getText().trim();
            String category = tfcategory.getText().trim();

            // Basic validation for fields being updated
            if (name.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Name and Phone cannot be empty for update.");
                return;
            }

            try {
                Conn conn = new Conn();
                String query = "";

                // Construct UPDATE query based on the original identifier used for fetching
                if (selectedSearchBy.equals("Name")) {
                    query = "UPDATE contacts SET name = '" + name + "', phone = '" + phone + "', email = '" + email + "', address = '" + address + "', category = '" + category + "' WHERE name = '" + originalIdentifier + "'";
                } else if (selectedSearchBy.equals("Phone")) {
                    query = "UPDATE contacts SET name = '" + name + "', phone = '" + phone + "', email = '" + email + "', address = '" + address + "', category = '" + category + "' WHERE phone = '" + originalIdentifier + "'";
                }

                int rowsAffected = conn.s.executeUpdate(query); // Execute update

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Contact Updated Successfully");
                    setVisible(false); // Close frame on success
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update contact. Please ensure the contact exists.");
                }
                conn.c.close();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error updating contact: " + e.getMessage());
            }
        }
    }

    // Helper method to clear all contact detail fields
    private void clearContactFields() {
        tfname.setText("");
        tfphone.setText("");
        tfemail.setText("");
        tfaddress.setText("");
        tfcategory.setText("");
    }

    public static void main(String[] args) {
        new UpdateContact();
    }
}
