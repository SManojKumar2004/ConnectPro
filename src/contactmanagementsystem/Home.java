package contactmanagementsystem; // Ensure this package name is consistent across all files

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Home extends JFrame implements ActionListener {

    public Home() {
        setLayout(new BorderLayout()); // Use BorderLayout for overall frame layout

        // Load the original image icon
        ImageIcon frontIcon = new ImageIcon(ClassLoader.getSystemResource("contactmanagementsystem/icons/front.jpg"));

        // Get the Image object from the ImageIcon
        Image image = frontIcon.getImage();

        // Scale the image to a desired size (e.g., 800x500) using SCALE_SMOOTH for quality
        // You can adjust these dimensions (800, 500) as needed for your console/laptop screen
        Image scaledImage = image.getScaledInstance(1366, 768, Image.SCALE_SMOOTH);

        // Create a new ImageIcon from the scaled Image
        ImageIcon scaledFrontIcon = new ImageIcon(scaledImage);

        // Create a JLabel to hold the scaled image
        JLabel imageLabel = new JLabel(scaledFrontIcon);
        // Do NOT use setLayout(null) or setBounds on imageLabel itself if adding to BorderLayout.CENTER
        // It will automatically take up the available space in the center.

        // Create the heading label
        JLabel heading = new JLabel("");
        // Set bounds for the heading relative to the imageLabel's internal coordinate system
        // The heading is added *to* the imageLabel, so its position is relative to the imageLabel's top-left corner.
        // Adjust these coordinates (e.g., 150, 40) to position the text aesthetically over your scaled image.
        heading.setBounds(150, 40, 900, 40);
        heading.setForeground(Color.BLUE);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 36));
        imageLabel.add(heading); // Add the heading label directly to the imageLabel

        // Add the imageLabel (which now contains the heading) to the center of the JFrame
        add(imageLabel, BorderLayout.CENTER);

        // Menu Bar setup
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar); // Set the menu bar for the frame

        JMenu contactsMenu = new JMenu("Contacts");
        menubar.add(contactsMenu); // Add the "Contacts" menu to the menu bar

        // Menu Items for Contact Management
        JMenuItem addContact = new JMenuItem("Add New Contact");
        addContact.addActionListener(this); // Register action listener
        contactsMenu.add(addContact);

        JMenuItem viewContacts = new JMenuItem("View All Contacts");
        viewContacts.addActionListener(this);
        contactsMenu.add(viewContacts);

        JMenuItem searchContact = new JMenuItem("Search Contact");
        searchContact.addActionListener(this);
        contactsMenu.add(searchContact);

        JMenuItem updateContact = new JMenuItem("Update Contact");
        updateContact.addActionListener(this);
        contactsMenu.add(updateContact);

        JMenuItem deleteContact = new JMenuItem("Delete Contact");
        deleteContact.addActionListener(this);
        contactsMenu.add(deleteContact);

        // Frame settings
        // Setting a fixed size provides more control over how the scaled image fits.
        // If you still want MAXIMIZED_BOTH, you would need more advanced dynamic image scaling
        // that recalculates on window resize events.
        setSize(1000, 700); // Set a fixed, reasonable size for the frame
        setLocationRelativeTo(null); // Center the frame on the screen
        setTitle("Contact Book Management System"); // Set the frame title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exits the application on close
        setVisible(true); // Make the frame visible
    }

    // Handles action events from menu items
    @Override // Indicates that this method overrides a method in a superclass/interface
    public void actionPerformed(ActionEvent ae) {
        String text = ae.getActionCommand(); // Get the command string from the action event

        // Open the corresponding JFrame based on the menu item clicked
        if (text.equals("Add New Contact")) {
            new AddNewContact();
        } else if (text.equals("View All Contacts")) {
            new ViewAllContacts();
        } else if (text.equals("Search Contact")) {
            new SearchContact();
        } else if (text.equals("Update Contact")) {
            new UpdateContact();
        } else if (text.equals("Delete Contact")) {
            new DeleteContact();
        }
    }

    public static void main(String[] args) {
        // Ensure Swing operations are performed on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Home();
            }
        });
    }
}
