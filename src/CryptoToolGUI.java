import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class CryptoToolGUI {
    public static void main(String[] args) {
        // Frame setup
        JFrame frame = new JFrame("CryptoTool");
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
        gbc.gridwidth = GridBagConstraints.RELATIVE; // This makes the next component (inputField) to be at the second last column
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Allows inputField to expand and fill space
        frame.add(inputField, gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER; // This makes the clearButton to be at the last column
        gbc.fill = GridBagConstraints.NONE; // Prevents the clearButton from expanding
        gbc.weightx = 0; // No extra horizontal space allocation for the clear button
        frame.add(clearButton, gbc);

        // Reset gbc settings for the next components
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        frame.add(executeButton, gbc);
        frame.add(executeButton, gbc);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        frame.add(scrollPane, gbc); // ScrollPane with expanded weight

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

                if ("DES".equals(selectedAlgorithm)) {
                    result = "DES Encrypted Output: \n" + DESEncryptor.encrypt(inputText);
                } else if ("AES".equals(selectedAlgorithm)) {
                    try {
                        result = "AES Encrypted Output: \n" + AESEncryptor.encrypt(inputText);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        result = "AES operation failed.";
                    }
                } else if ("3DES".equals(selectedAlgorithm)) {
                    result = "3DES Encrypted Output: \n" + DES3Encryptor.encrypt(inputText);
                } else if ("Blowfish".equals(selectedAlgorithm)) {
                    result = "Blowfish Encrypted Output: \n" + BlowfishEncryptor.encrypt(inputText);
                } else if ("Twofish".equals(selectedAlgorithm)) {
                    String keyFile = "src/keys/twofish_key.bin";
                    twofishKeyGenerator.generateTwofishKey(keyFile);
                    try {
                        // Read the key from the file
                        byte[] keyBytes = Files.readAllBytes(Paths.get(keyFile));
                        String key = new String(keyBytes);
                        // Encrypt the input text using the TwofishEncryptor
                        result = "Twofish Encrypted Output: \n" + TwofishEncryptor.encrypt(inputText, key);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        result = "Twofish operation failed.";
                    }
                } else if ("IDEA".equals(selectedAlgorithm)) {
                    try {
                        result = "IDEA Encrypted Output: \n" + IDEAEncryptor.encrypt(inputText);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        result = "IDEA operation failed.";
                    }
                } else if ("RC4".equals(selectedAlgorithm)) {
                    try {
                        rc4KeyGenUtil.generateAndSaveRC4Key();
                    } catch (NoSuchAlgorithmException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    result = "RC4 Encrypted Output: \n" + RC4Encryptor.encrypt(inputText);
                } else if ("RSA".equals(selectedAlgorithm)) {
                    try {
                        RSAEncryptor.generateKeyPair();
                        result = "RSA Encrypted Output: \n" + RSAEncryptor.encrypt(inputText);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        result = "RSA operation failed.";
                    }
                } else if ("ECC".equals(selectedAlgorithm)) {
                    try {
                        ECCEncryptor.initializeKeyPair();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    result = "ECC Encrypted Output: \n" + ECCEncryptor.encrypt(inputText);
                } else if ("ElGamal".equals(selectedAlgorithm)) {
                    ElGamalKeyManager elGamalKeyManager = new ElGamalKeyManager();
                    result = "ElGamal Encrypted Output: \n" + ElGamalEncryptor.encrypt(inputText, elGamalKeyManager);
                } else if ("DSA".equals(selectedAlgorithm)) {
                    try {
                        KeyPair keyPair = DSAEncryptor.generateKeyPair();
                        String signature = DSAEncryptor.sign(inputText, keyPair.getPrivate());
                        boolean isVerified = DSAEncryptor.verify(inputText, signature, keyPair.getPublic());
                        result = "DSA Signature: \n" + signature + "\nVerification: " + isVerified;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        result = "DSA operation failed.";
                    }
                } else if ("SHA-1".equals(selectedAlgorithm)) {
                    try {
                        result = "SHA-1 Encrypted Output: \n" + SHA1Encryptor.encrypt(inputText);
                    } catch (NoSuchAlgorithmException ex) {
                        ex.printStackTrace();
                        result = "SHA-1 operation failed.";
                    }
                } else if ("SHA-256".equals(selectedAlgorithm)) {
                    result = "SHA-256 Encrypted Output: \n" + SHA256Encryptor.encrypt(inputText);
                } else if ("SHA-3".equals(selectedAlgorithm)) {
                    try {
                        result = "SHA-3 Encrypted Output: \n" + SHA3Encryptor.encrypt(inputText);
                    } catch (NoSuchAlgorithmException ex) {
                        ex.printStackTrace();
                        result = "SHA-3 operation failed.";
                    }
                } else if ("MD5".equals(selectedAlgorithm)) {
                    try {
                        result = "MD5 Encrypted Output: \n" + MD5Encryptor.encrypt(inputText);
                    } catch (NoSuchAlgorithmException ex) {
                        ex.printStackTrace();
                        result = "MD5 operation failed.";
                    }
                } else if ("RIPEMD".equals(selectedAlgorithm)) {
                    try {
                        result = "RIPEMD Encrypted Output: \n" + RIPEMDEncryptor.encrypt(inputText);
                    } catch (NoSuchAlgorithmException ex) {
                        ex.printStackTrace();
                        result = "RIPEMD operation failed.";
                    }
                } else if ("Whirlpool".equals(selectedAlgorithm)) {
                    result = "Whirlpool Encrypted Output: \n" + WhirlpoolEncryptor.encrypt(inputText);
                }

                System.out.println("Result: " + result);
                finalResultArea.setText(result);
                finalResultArea.repaint(); // Force the resultArea to repaint
                frame.validate(); // Force the frame to re-layout its components
            }
        });

        // Display the window
        frame.setVisible(true);
    }
}