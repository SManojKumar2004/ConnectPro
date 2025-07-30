package contactmanagementsystem; // Ensure this package name is consistent across all files

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DeleteContact extends JFrame implements ActionListener{

    JTextField tfidentifier; // For searching by name or phone
    JLabel tfname, tfphone, tfemail, tfaddress, tfcategory; // Labels to display fetched info
    JButton fetchButton, deleteButton;
    Choice deleteBy; // Dropdown for search criteria

    public DeleteContact() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel heading = new JLabel("DELETE CONTACT");
        heading.setBounds(180, 20, 300, 35);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 32));
        add(heading);

        JLabel lblidentifier = new JLabel("Delete By:");
        lblidentifier.setBounds(60, 80, 150, 25);
        lblidentifier.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblidentifier);

        deleteBy = new Choice();
        deleteBy.setBounds(220, 80, 150, 25);
        deleteBy.add("Name");
        deleteBy.add("Phone");
        add(deleteBy);

        tfidentifier = new JTextField();
        tfidentifier.setBounds(220, 120, 150, 25);
        add(tfidentifier);

        fetchButton = new JButton("Show Details");
        fetchButton.setBackground(Color.BLACK);
        fetchButton.setForeground(Color.WHITE);
        fetchButton.setBounds(380, 100, 120, 25);
        fetchButton.addActionListener(this); // Register action listener
        add(fetchButton);

        // Labels to display fetched contact details
        JLabel lblname = new JLabel("Name:");
        lblname.setBounds(60, 170, 150, 25);
        lblname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblname);

        tfname = new JLabel(); // Use JLabel to display read-only information
        tfname.setBounds(220, 170, 150, 25);
        add(tfname);

        JLabel lblphone = new JLabel("Phone:");
        lblphone.setBounds(60, 210, 150, 25);
        lblphone.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblphone);

        tfphone = new JLabel();
        tfphone.setBounds(220, 210, 150, 25);
        add(tfphone);

        JLabel lblemail = new JLabel("Email:");
        lblemail.setBounds(60, 250, 150, 25);
        lblemail.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblemail);

        tfemail = new JLabel();
        tfemail.setBounds(220, 250, 150, 25);
        add(tfemail);

        JLabel lbladdress = new JLabel("Address:");
        lbladdress.setBounds(60, 290, 150, 25);
        lbladdress.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lbladdress);

        tfaddress = new JLabel();
        tfaddress.setBounds(220, 290, 150, 25);
        add(tfaddress);

        JLabel lblcategory = new JLabel("Category:");
        lblcategory.setBounds(60, 330, 150, 25);
        lblcategory.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblcategory);

        tfcategory = new JLabel();
        tfcategory.setBounds(220, 330, 150, 25);
        add(tfcategory);

        deleteButton = new JButton("Delete Contact");
        deleteButton.setBackground(Color.BLACK);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBounds(220, 380, 150, 25);
        deleteButton.addActionListener(this); // Register action listener
        deleteButton.setEnabled(false); // Disable delete button initially
        add(deleteButton);

        setSize(800, 480); // Adjusted size
        setLocation(350, 150);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == fetchButton) {
            String identifier = tfidentifier.getText().trim();
            String selectedDeleteBy = deleteBy.getSelectedItem();

            if (identifier.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a value to search.");
                clearContactLabels();
                deleteButton.setEnabled(false);
                return;
            }

            try {
                Conn conn = new Conn();
                String query = "";

                // Construct query based on search criterion
                if (selectedDeleteBy.equals("Name")) {
                    query = "SELECT * FROM contacts WHERE name = '" + identifier + "'";
                } else if (selectedDeleteBy.equals("Phone")) {
                    query = "SELECT * FROM contacts WHERE phone = '" + identifier + "'";
                }

                ResultSet rs = conn.s.executeQuery(query);

                if (rs.next()) {
                    // Populate labels with fetched data
                    tfname.setText(rs.getString("name"));
                    tfphone.setText(rs.getString("phone"));
                    tfemail.setText(rs.getString("email"));
                    tfaddress.setText(rs.getString("address"));
                    tfcategory.setText(rs.getString("category"));
                    deleteButton.setEnabled(true); // Enable delete button
                } else {
                    JOptionPane.showMessageDialog(null, "No contact found with the provided details.");
                    clearContactLabels(); // Clear labels if no contact found
                    deleteButton.setEnabled(false); // Disable delete button
                }
                rs.close();
                conn.c.close();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error fetching contact: " + e.getMessage());
                clearContactLabels();
                deleteButton.setEnabled(false);
            }
        } else if (ae.getSource() == deleteButton) {
            String identifier = tfidentifier.getText().trim();
            String selectedDeleteBy = deleteBy.getSelectedItem();

            // Confirmation dialog before deleting
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this contact?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    Conn conn = new Conn();
                    String query = "";

                    // Construct DELETE query
                    if (selectedDeleteBy.equals("Name")) {
                        query = "DELETE FROM contacts WHERE name = '" + identifier + "'";
                    } else if (selectedDeleteBy.equals("Phone")) {
                        query = "DELETE FROM contacts WHERE phone = '" + identifier + "'";
                    }

                    int rowsAffected = conn.s.executeUpdate(query); // Execute delete

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Contact Deleted Successfully");
                        setVisible(false); // Close frame on success
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to delete contact. Contact not found or an error occurred.");
                    }
                    conn.c.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error deleting contact: " + e.getMessage());
                }
            }
        }
    }

    // Helper method to clear all contact detail labels
    private void clearContactLabels() {
        tfname.setText("");
        tfphone.setText("");
        tfemail.setText("");
        tfaddress.setText("");
        tfcategory.setText("");
    }

    public static void main(String[] args) {
        new DeleteContact();
    }
}
