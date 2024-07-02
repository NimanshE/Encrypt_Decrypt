package Encryption;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class EncryptToolGUI extends JFrame {
    public EncryptToolGUI() {
        // Frame setup
        JFrame frame = new JFrame("EncryptTool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // GridBagLayout for flexible component arrangement
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10); // Margin around components

        // Components
        JComboBox<String> encryptionTypeDropdown = new JComboBox<>();
        encryptionTypeDropdown.addItem("Select Encryption Type");
        for (String type : new String[]{"Symmetric", "Asymmetric", "Hash"}) {
            encryptionTypeDropdown.addItem(type);
        }

        JComboBox<String> algorithmDropdown = new JComboBox<>();
        algorithmDropdown.addItem("Select Algorithm");

        JTextField inputField = new JTextField(20); // field size
        JButton clearButton = new JButton("Clear");
        JButton executeButton = new JButton("Execute");
        JTextArea resultArea = new JTextArea(8, 20);
        resultArea.setEditable(false);
        resultArea.setMargin(new Insets(5, 5, 5, 5)); // margin
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Components with GridBagConstraints
        frame.add(encryptionTypeDropdown, gbc);
        frame.add(algorithmDropdown, gbc);

        // Adjustments for GridBagConstraints
        gbc.gridwidth = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        frame.add(inputField, gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        frame.add(clearButton, gbc);

        // Reset gbc settings for the next components
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        frame.add(executeButton, gbc);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        frame.add(scrollPane, gbc);

        // Copy button
        JButton copyButton = new JButton("Copy");
        copyButton.addActionListener(e -> {
            String text = resultArea.getText();
            StringSelection stringSelection = new StringSelection(text);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        });
        frame.add(copyButton, gbc); // Add copy button to the frame

        // Encryption Algorithms Map
        Map<String, String[]> algorithmsMap = new HashMap<>();
        algorithmsMap.put("Symmetric", new String[]{"DES", "AES", "3DES", "Blowfish", "Twofish", "IDEA", "RC4"});
        algorithmsMap.put("Asymmetric", new String[]{"RSA", "ECC", "ElGamal", "DSA"});
        algorithmsMap.put("Hash", new String[]{"SHA-1", "SHA-256", "SHA-3", "MD5", "RIPEMD", "Whirlpool"});

        // Event Listeners
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputField.setText(""); // Clear the input field
                resultArea.setText(""); // Clear the Result area
            }
        });
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
                resultArea.setText(""); // Clear the result area whenever the encryption type is changed
            }
        });

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
                resultArea.setText(""); // Clear the result area whenever the encryption type is changed
            }
        });

        algorithmDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultArea.setText(""); // Clear the result area whenever a new algorithm is selected
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
                String result = "Unsupported algorithm or operation failed."; // Default message


                switch (selectedAlgorithm) {
                    // DES Algo Call
                    case "DES" ->
                        //result = "DES Encrypted Output: \n" + DESEncryptor.encrypt(inputText);
                            result = DESEncryptor.encrypt(inputText);

                    // AES Algo Call
                    case "AES" -> {
                        try {
                            //result = "AES Encrypted Output: \n" + AESEncryptor.encrypt(inputText);
                            result = AESEncryptor.encrypt(inputText);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            result = "AES operation failed.";
                        }
                    }

                    // 3DES Algo call
                    case "3DES" ->
                        //result = "3DES Encrypted Output: \n" + DES3Encryptor.encrypt(inputText);
                            result = DES3Encryptor.encrypt(inputText);


                    // Blowfish Algo Call
                    case "Blowfish" ->
                        //result = "Blowfish Encrypted Output: \n" + BlowfishEncryptor.encrypt(inputText);
                            result = BlowfishEncryptor.encrypt(inputText);


                    // Twofish Algo Call
                    case "Twofish" -> {
                        String keyFile = "src/keys/twofish_key.bin";
                        twofishKeyGenerator.generateTwofishKey(keyFile);
                        try {
                            // Read the key from the file
                            byte[] keyBytes = Files.readAllBytes(Paths.get(keyFile));
                            String key = new String(keyBytes);
                            // Encrypt the input text using the TwofishEncryptor
                            //result = "Twofish Encrypted Output: \n" + TwofishEncryptor.encrypt(inputText, key);
                            result = TwofishEncryptor.encrypt(inputText, key);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            result = "Twofish operation failed.";
                        }
                    }

                    // IDEA Algo Call
                    case "IDEA" -> {
                        try {
                            //result = "IDEA Encrypted Output: \n" + IDEAEncryptor.encrypt(inputText);
                            result = IDEAEncryptor.encrypt(inputText);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            result = "IDEA operation failed.";
                        }
                    }

                    // RC4 Algo Call
                    case "RC4" -> {
                        try {
                            rc4KeyGenUtil.generateAndSaveRC4Key();
                        } catch (NoSuchAlgorithmException | IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        //result = "RC4 Encrypted Output: \n" + RC4Encryptor.encrypt(inputText);
                        result = RC4Encryptor.encrypt(inputText);
                    }

                    // RSA Algo Call
                    case "RSA" -> {
                        try {
                            RSAEncryptor.generateKeyPair();
                            //result = "RSA Encrypted Output: \n" + RSAEncryptor.encrypt(inputText);
                            result = RSAEncryptor.encrypt(inputText);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            result = "RSA operation failed.";
                        }
                    }

                    // ECC Algo Call
                    case "ECC" -> {
                        try {
                            ECCEncryptor.initializeKeyPair();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        //result = "ECC Encrypted Output: \n" + ECCEncryptor.encrypt(inputText);
                        result = ECCEncryptor.encrypt(inputText);
                    }

                    // ElGamal Algo Call
                    case "ElGamal" -> {
                        ElGamalKeyManager elGamalKeyManager = new ElGamalKeyManager();
                        //result = "ElGamal Encrypted Output: \n" + ElGamalEncryptor.encrypt(inputText, elGamalKeyManager);
                        result = ElGamalEncryptor.encrypt(inputText, elGamalKeyManager);
                    }

                    // DSA Algo Call
                    case "DSA" -> {
                        try {
                            KeyPair keyPair = DSAEncryptor.generateKeyPair();
                            String signature = DSAEncryptor.sign(inputText, keyPair.getPrivate());
                            boolean isVerified = DSAEncryptor.verify(inputText, signature, keyPair.getPublic());
                            //result = "DSA Signature: \n" + signature + "\nVerification: " + isVerified;
                            result = signature;
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            result = "DSA operation failed.";
                        }
                    }

                    // SHA-1 Algo Call
                    case "SHA-1" -> {
                        try {
                            //result = "SHA-1 Encrypted Output: \n" + SHA1Encryptor.encrypt(inputText);
                            result = SHA1Encryptor.encrypt(inputText);
                        } catch (NoSuchAlgorithmException ex) {
                            ex.printStackTrace();
                            result = "SHA-1 operation failed.";
                        }
                    }

                    // SHA-256 Algo Call
                    case "SHA-256" ->
                        //result = "SHA-256 Encrypted Output: \n" + SHA256Encryptor.encrypt(inputText);
                            result = SHA256Encryptor.encrypt(inputText);


                    // SHA-3 Algo Call
                    case "SHA-3" -> {
                        try {
                            //result = "SHA-3 Encrypted Output: \n" + SHA3Encryptor.encrypt(inputText);
                            result = SHA3Encryptor.encrypt(inputText);
                        } catch (NoSuchAlgorithmException ex) {
                            ex.printStackTrace();
                            result = "SHA-3 operation failed.";
                        }
                    }

                    // MD5 Algo Call
                    case "MD5" -> {
                        try {
                            //result = "MD5 Encrypted Output: \n" + MD5Encryptor.encrypt(inputText);
                            result = MD5Encryptor.encrypt(inputText);
                        } catch (NoSuchAlgorithmException ex) {
                            ex.printStackTrace();
                            result = "MD5 operation failed.";
                        }
                    }

                    // RIPEMD Algo Call
                    case "RIPEMD" -> {
                        try {
                            //result = "RIPEMD Encrypted Output: \n" + RIPEMDEncryptor.encrypt(inputText);
                            result = RIPEMDEncryptor.encrypt(inputText);
                        } catch (NoSuchAlgorithmException ex) {
                            ex.printStackTrace();
                            result = "RIPEMD operation failed.";
                        }
                    }

                    // Whirlpool Algo Call
                    case "Whirlpool" ->
                        //result = "Whirlpool Encrypted Output: \n" + WhirlpoolEncryptor.encrypt(inputText);
                            result = WhirlpoolEncryptor.encrypt(inputText);

                    case null, default -> result = "ERROR, Choose a Valid Option";
                }

                System.out.println("Result: " + result);
                finalResultArea.setText(result);
                finalResultArea.repaint(); // Force the resultArea to repaint
                frame.validate(); // Force the frame to re-layout its components
            }
        });

        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Assuming 'encryptedText' is a String containing the text to be copied
                String encryptedText = "Your encrypted text here"; // Replace with actual encrypted text variable
                StringSelection stringSelection = new StringSelection(encryptedText);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);

                // Show popup message
                JDialog popup = new JDialog();
                popup.setSize(300, 100);
                popup.setLayout(new BorderLayout());
                JLabel messageLabel = new JLabel("Encrypted text has been copied to your clipboard", SwingConstants.CENTER);
                popup.add(messageLabel, BorderLayout.CENTER);
                popup.setLocationRelativeTo(frame); // Center the popup relative to the main frame
                popup.setUndecorated(true); // Remove window decorations
                popup.setOpacity(0.8f); // Make slightly transparent
                popup.setVisible(true);

                // Use a Timer to make the popup disappear after 2 seconds
                new Timer(2000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        popup.setVisible(false);
                        popup.dispose(); // Free resources and remove the popup
                    }
                }).start(); // Start the timer
            }
        });

        // Display the window
        frame.setVisible(true);
    }
}