package gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

public class d {  
    public static void main(String[] args) {
        // Array of options to display
String[] options = {"Choice 1", "Choice 2", "Choice 3", "Choice 4"};

// Display the option dialog
int choice = JOptionPane.showOptionDialog(
    null, // Parent component (null for default)
    "Choose an option", // Message to display
    "Options", // Dialog title
   0, // Option type
    JOptionPane.QUESTION_MESSAGE, // Message type
    null, // Icon (null for default)
    options, // Options to display as buttons
    null); // Default option (can be null)

// Check the user's choice
if (choice != JOptionPane.CLOSED_OPTION) {
    System.out.println("You selected: " + options[choice]);
} else {
    System.out.println("Dialog closed without selection.");
}

    }
}
