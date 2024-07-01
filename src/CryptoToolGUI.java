import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class CryptoToolGUI {
    public static void main(String[] args) {
        // Frame setup
        JFrame frame = new JFrame("CryptoTool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);

        // Box Layout for vertical arrangement
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        // Components
        JComboBox<String> encryptionTypeDropdown = new JComboBox<>();
        encryptionTypeDropdown.addItem("Select Encryption Type");
        for (String type : new String[]{"Symmetric", "Asymmetric", "Hash"}) {
            encryptionTypeDropdown.addItem(type);
        }

        JComboBox<String> algorithmDropdown = new JComboBox<>();
        algorithmDropdown.addItem("Select Algorithm");

        JTextField inputField = new JTextField(10);
        JButton executeButton = new JButton("Execute");
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);

        // Add components
        frame.add(encryptionTypeDropdown);
        frame.add(algorithmDropdown);
        frame.add(inputField);
        frame.add(executeButton);

        // Ensure resultArea is properly initialized and adding to the frame
        resultArea = new JTextArea(10, 15); // Adjusted size, reduced rows and columns
        resultArea.setMargin(new Insets(5, 5, 5, 5)); // Add some margin to start text from top
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        frame.add(scrollPane);

        // Encryption Algorithms Map
        Map<String, String[]> algorithmsMap = new HashMap<>();
        algorithmsMap.put("Symmetric", new String[]{"DES", "AES", "3DES", "Blowfish", "Twofish", "IDEA", "RC4"});
        algorithmsMap.put("Asymmetric", new String[]{"RSA", "ECC", "Diffie-Hellman", "ElGamal", "DSA"});
        algorithmsMap.put("Hash", new String[]{"SHA-1", "SHA-256", "SHA-3", "MD5", "RIPEMD", "Whirlpool"});

        // Event Listeners
        encryptionTypeDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedType = (String) encryptionTypeDropdown.getSelectedItem();
                algorithmDropdown.removeAllItems(); // Clear current items
                algorithmDropdown.addItem("Select Algorithm"); // Reset default value
                if (selectedType != null && !selectedType.equals("Select Encryption Type")) {
                    for (String algorithm : algorithmsMap.getOrDefault(selectedType, new String[]{})) {
                        algorithmDropdown.addItem(algorithm);
                    }
                }
            }
        });

        JTextArea finalResultArea = resultArea;
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedAlgorithm = (String) algorithmDropdown.getSelectedItem();
                String inputText = inputField.getText();
                System.out.println("Algorithm Selected: " + selectedAlgorithm); // Debugging line
                System.out.println("Input Text: " + inputText); // Debugging line
                String result;

                // Check if SHA-256 is selected and encrypt accordingly
                if ("SHA-256".equals(selectedAlgorithm)) {
                    result = "SHA-256 Encrypted Output: \n" + SHA256Encryptor.encrypt(inputText);
                } else {
                    // Placeholder for other encryption logic
                    result = "Operation with " + selectedAlgorithm + " on input '" + inputText + "' completed.";
                }

                System.out.println("Result: " + result);
                finalResultArea.setText(result);
                finalResultArea.setText(result); // Set the text to resultArea
                finalResultArea.repaint(); // Force the resultArea to repaint
                frame.validate(); // Force the frame to re-layout its components
            }
        });

        // Display the window
        frame.setVisible(true);
    }
}