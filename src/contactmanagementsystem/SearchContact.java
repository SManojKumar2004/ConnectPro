package contactmanagementsystem; // Ensure this package name is consistent across all files

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel; // Import DefaultTableModel
import net.proteanit.sql.DbUtils; // Required for resultSetToTableModel

public class SearchContact extends JFrame implements ActionListener{
    JTable table;
    JTextField searchField;
    JButton show;
    Choice searchBy; // Dropdown for search criteria

    public SearchContact() {

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel lblsearch = new JLabel("Search By:");
        lblsearch.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblsearch.setBounds(50, 30, 100, 25);
        add(lblsearch);

        searchBy = new Choice();
        searchBy.setBounds(160, 30, 120, 25);
        searchBy.add("Name");
        searchBy.add("Phone");
        searchBy.add("Category");
        add(searchBy);

        searchField = new JTextField();
        searchField.setBounds(290, 30, 150, 25);
        add(searchField);

        show = new JButton("Search");
        show.setBackground(Color.BLACK);
        show.setForeground(Color.WHITE);
        show.setBounds(450, 30, 100, 25);
        show.addActionListener(this); // Register action listener
        add(show);

        table = new JTable(); // Initialize JTable

        JScrollPane jsp = new JScrollPane(table); // Add table to scroll pane
        jsp.setBounds(0, 80, 800, 400);
        jsp.setBackground(Color.WHITE);
        add(jsp);

        setSize(800, 550);
        setLocation(400, 150);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == show) { // Check if the "Search" button was clicked
            try {
                Conn conn = new Conn(); // Establish database connection
                String query = "";
                String searchTerm = searchField.getText().trim(); // Get search term and trim whitespace
                String selectedSearchBy = searchBy.getSelectedItem(); // Get selected search criterion

                if (searchTerm.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a search term.");
                    // Clear the table by setting an empty DefaultTableModel
                    table.setModel(new DefaultTableModel());
                    return;
                }

                // Construct SQL query based on selected search criterion
                if (selectedSearchBy.equals("Name")) {
                    query = "SELECT * FROM contacts WHERE name LIKE '%" + searchTerm + "%'";
                } else if (selectedSearchBy.equals("Phone")) {
                    query = "SELECT * FROM contacts WHERE phone LIKE '%" + searchTerm + "%'";
                } else if (selectedSearchBy.equals("Category")) {
                    query = "SELECT * FROM contacts WHERE category LIKE '%" + searchTerm + "%'";
                }

                ResultSet rs = conn.s.executeQuery(query); // Execute the query

                // Check if the ResultSet is empty
                if (!rs.isBeforeFirst()) { // Checks if there are any rows
                    JOptionPane.showMessageDialog(null, "No contacts found for '" + searchTerm + "'.");
                    table.setModel(new DefaultTableModel()); // Set empty model to clear table
                } else {
                    table.setModel(DbUtils.resultSetToTableModel(rs)); // Set table model with results
                }
                rs.close(); // Close the ResultSet
                conn.c.close(); // Close the connection
            } catch(Exception e) {
                e.printStackTrace(); // Print stack trace for debugging
                JOptionPane.showMessageDialog(null, "Error during search: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new SearchContact();
    }
}
