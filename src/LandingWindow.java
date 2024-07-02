import Decryption.DecryptToolGUI;
import Encryption.EncryptToolGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LandingWindow extends JFrame {
    private JButton encryptButton;
    private JButton decryptButton;

    public LandingWindow() {
        // Frame setup
        setTitle("Encryption & Decryption Tool");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        getContentPane().setBackground(Color.WHITE); // Set background color

        // Create buttons
        encryptButton = new JButton("Encrypt");
        decryptButton = new JButton("Decrypt");

        // Styling buttons
        encryptButton.setBackground(new Color(230, 230, 230)); // Light gray background
        decryptButton.setBackground(new Color(230, 230, 230)); // Light gray background

        encryptButton.setFocusPainted(false); // Remove border around text when clicked
        decryptButton.setFocusPainted(false);

        encryptButton.setFont(new Font("Arial", Font.BOLD, 12));
        decryptButton.setFont(new Font("Arial", Font.BOLD, 12));

        // Set preferred size for buttons to make them smaller horizontally
        Dimension buttonSize = new Dimension(200, 50); // Width of 200 and height of 50
        encryptButton.setPreferredSize(buttonSize);
        decryptButton.setPreferredSize(buttonSize);

        // Button actions
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                EncryptToolGUI encryptToolGUI = new EncryptToolGUI();
                LandingWindow.this.setVisible(false);
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                DecryptToolGUI decryptToolGUI = new DecryptToolGUI();
                LandingWindow.this.setVisible(false);
            }
        });

        // Layout setup
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.CENTER; // Center alignment for components
        gbc.fill = GridBagConstraints.HORIZONTAL; // Stretch components to fill the space
        gbc.gridx = 0; // Initial gridx
        gbc.gridy = 0; // Initial gridy
        gbc.weightx = 1.0; // Distribute space horizontally
        gbc.weighty = 1.0; // Distribute space vertically
        gbc.insets = new Insets(10, 50, 10, 50); // Top, left, bottom, right padding

        // Add the encryptButton with constraints
        add(encryptButton, gbc);

        // Update gridy for the next component
        gbc.gridy = 1;

        // Add the decryptButton with updated constraints
        add(decryptButton, gbc);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LandingWindow().setVisible(true);
            }
        });
    }
}