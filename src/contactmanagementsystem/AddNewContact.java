package contactmanagementsystem; // Ensure this package name is consistent across all files

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddNewContact extends JFrame implements ActionListener{

    JTextField tfname, tfphone, tfemail, tfaddress, tfcategory;
    JButton save;

    public AddNewContact() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null); // Using null layout for absolute positioning

        JLabel heading = new JLabel("ADD NEW CONTACT");
        heading.setBounds(220, 20, 500, 35);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 32));
        heading.setForeground(Color.BLUE);
        add(heading);

        JLabel lblname = new JLabel("Name");
        lblname.setBounds(60, 80, 150, 25);
        lblname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblname);

        tfname = new JTextField();
        tfname.setBounds(220, 80, 150, 25);
        add(tfname);

        JLabel lblphone = new JLabel("Phone");
        lblphone.setBounds(60, 130, 150, 25);
        lblphone.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblphone);

        tfphone = new JTextField();
        tfphone.setBounds(220, 130, 150, 25);
        add(tfphone);

        JLabel lblemail = new JLabel("Email");
        lblemail.setBounds(60, 180, 150, 25);
        lblemail.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblemail);

        tfemail = new JTextField();
        tfemail.setBounds(220, 180, 150, 25);
        add(tfemail);

        JLabel lbladdress = new JLabel("Address");
        lbladdress.setBounds(60, 230, 150, 25);
        lbladdress.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lbladdress);

        tfaddress = new JTextField();
        tfaddress.setBounds(220, 230, 150, 25);
        add(tfaddress);

        JLabel lblcategory = new JLabel("Category");
        lblcategory.setBounds(60, 280, 150, 25);
        lblcategory.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblcategory);

        tfcategory = new JTextField();
        tfcategory.setBounds(220, 280, 150, 25);
        add(tfcategory);

        save = new JButton("SAVE CONTACT");
        save.setBackground(Color.BLACK);
        save.setForeground(Color.WHITE);
        save.setBounds(220, 330, 150, 30);
        save.addActionListener(this); // Registering action listener for the save button
        add(save);

        // Optional: Add an image related to contacts if desired
        // ImageIcon image = new ImageIcon(ClassLoader.getSystemResource("path/to/contact_icon.png"));
        // JLabel lblimage = new JLabel(image);
        // lblimage.setBounds(450, 80, 280, 400);
        // add(lblimage);

        setSize(900, 450); // Adjusted size for contact form
        setLocation(300, 150);
        setVisible(true);
    }

    @Override // Indicates that this method overrides a method in a superclass/interface
    public void actionPerformed(ActionEvent ae) {
        // Get text from all input fields
        String name = tfname.getText();
        String phone = tfphone.getText();
        String email = tfemail.getText();
        String address = tfaddress.getText();
        String category = tfcategory.getText();

        // Basic validation: Ensure name and phone are not empty
        if (name.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Name and Phone are required fields.");
            return; // Stop execution if validation fails
        }

        try {
            Conn conn = new Conn(); // Establish database connection

            // SQL query to insert data into the 'contacts' table
            String query = "INSERT INTO contacts (name, phone, email, address, category) VALUES('" + name + "', '" + phone + "', '" + email + "', '" + address + "', '" + category + "')";

            conn.s.executeUpdate(query); // Execute the SQL update query

            JOptionPane.showMessageDialog(null, "Contact Added Successfully"); // Show success message

            setVisible(false); // Close the current frame after successful operation
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
            JOptionPane.showMessageDialog(null, "Error adding contact: " + e.getMessage()); // Show error message to user
        }
    }

    public static void main(String[] args) {
        new AddNewContact();
    }
}
