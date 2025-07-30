package contactmanagementsystem; // Ensure this package name is consistent across all files

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils; // Required for resultSetToTableModel - ensure rs2xml.jar is in classpath

public class ViewAllContacts extends JFrame{

    JTable table; // Declare JTable at class level

    public ViewAllContacts() {

        getContentPane().setBackground(Color.WHITE);
        setLayout(null); // Using null layout

        table = new JTable(); // Initialize JTable

        try {
            Conn conn = new Conn(); // Establish database connection

            // SQL query to select all data from the 'contacts' table
            String query = "SELECT * FROM contacts";
            ResultSet rs = conn.s.executeQuery(query); // Execute the query

            // Set the table model using DbUtils to display ResultSet data
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch(Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
            JOptionPane.showMessageDialog(null, "Error loading contacts: " + e.getMessage());
        }

        // Add the table to a JScrollPane for scrollability
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 0, 800, 500); // Set bounds for the scroll pane
        add(jsp); // Add scroll pane to the frame

        setSize(800, 500); // Set frame size
        setLocation(400, 200); // Set frame location
        setVisible(true); // Make the frame visible
    }

    public static void main(String[] args) {
        new ViewAllContacts();
    }
}

