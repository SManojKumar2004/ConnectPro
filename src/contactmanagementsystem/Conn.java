package contactmanagementsystem; // Ensure this package name is consistent across all files

import java.sql.*;

public class Conn {

    Connection c;
    Statement s;

    public Conn() {
        try {
            // Register the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish the connection to your MySQL database
            // Replace "root" and "Manoj@#2004" with your MySQL username and password
            c = DriverManager.getConnection("jdbc:mysql:///contactbooksystem", "root", "Manoj@#2004");
            // Create a statement object for sending SQL commands
            s = c.createStatement();
        } catch (Exception e) {
            // Print any exceptions that occur during connection setup
            e.printStackTrace();
        }
    }
}
