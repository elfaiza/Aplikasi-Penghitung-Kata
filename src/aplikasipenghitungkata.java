/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Disporapar HST
 */
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.regex.*;

public class aplikasipenghitungkata extends JFrame {

    private JTextArea textArea;
    private JLabel wordCountLabel, charCountLabel, sentenceCountLabel, paragraphCountLabel;
    private JButton calculateButton, saveButton, searchButton;
    private JTextField searchField;

    public aplikasipenghitungkata() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Aplikasi Penghitung Kata");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // Center the window

        // Panel utama
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Text Area dengan ScrollPane
        textArea = new JTextArea(10, 40);
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Label untuk menampilkan hasil perhitungan
        wordCountLabel = new JLabel("Jumlah kata: 0");
        charCountLabel = new JLabel("Jumlah karakter: 0");
        sentenceCountLabel = new JLabel("Jumlah kalimat: 0");
        paragraphCountLabel = new JLabel("Jumlah paragraf: 0");

        // Button untuk menghitung
        calculateButton = new JButton("Hitung");
        calculateButton.setFont(new Font("Arial", Font.BOLD, 14));

        // Button untuk menyimpan
        saveButton = new JButton("Simpan");
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));

        // Button untuk mencari kata
        searchButton = new JButton("Cari Kata");
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));

        // Text Field untuk pencarian kata
        searchField = new JTextField(10);

        // Panel untuk menampilkan hasil perhitungan
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new GridLayout(4, 1));
        resultPanel.add(wordCountLabel);
        resultPanel.add(charCountLabel);
        resultPanel.add(sentenceCountLabel);
        resultPanel.add(paragraphCountLabel);

        // Panel untuk button dan pencarian
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(calculateButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(searchField);
        buttonPanel.add(searchButton);

        // Menambahkan komponen ke panel utama
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(resultPanel, BorderLayout.EAST);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        // Event Listener untuk tombol Hitung
        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateTextStats();
            }
        });

        // Event Listener untuk tombol Simpan
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveTextToFile();
            }
        });

        // Event Listener untuk tombol Cari Kata
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchWord();
            }
        });

        // DocumentListener untuk menghitung secara real-time
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                calculateTextStats();
            }

            public void removeUpdate(DocumentEvent e) {
                calculateTextStats();
            }

            public void changedUpdate(DocumentEvent e) {
                calculateTextStats();
            }
        });
    }

    private void calculateTextStats() {
        String text = textArea.getText();
        
        // Menghitung jumlah kata
        String[] words = text.split("\\s+");
        int wordCount = words.length;

        // Menghitung jumlah karakter
        int charCount = text.replaceAll("\\s", "").length();

        // Menghitung jumlah kalimat (menggunakan titik, tanda seru, dan tanda tanya)
        String[] sentences = text.split("[.!?]+");
        int sentenceCount = sentences.length;

        // Menghitung jumlah paragraf (baris kosong sebagai pemisah paragraf)
        String[] paragraphs = text.split("\\n\\s*\\n");
        int paragraphCount = paragraphs.length;

        // Memperbarui label dengan hasil perhitungan
        wordCountLabel.setText("Jumlah kata: " + wordCount);
        charCountLabel.setText("Jumlah karakter: " + charCount);
        sentenceCountLabel.setText("Jumlah kalimat: " + sentenceCount);
        paragraphCountLabel.setText("Jumlah paragraf: " + paragraphCount);
    }

    private void saveTextToFile() {
        try {
            // Membuka file dialog untuk menyimpan file
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showSaveDialog(this);

            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(textArea.getText());
                writer.close();
                JOptionPane.showMessageDialog(this, "Teks berhasil disimpan!");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menyimpan file.");
        }
    }

    private void searchWord() {
        String word = searchField.getText().trim();
        String text = textArea.getText();
        if (!word.isEmpty()) {
            Pattern pattern = Pattern.compile("\\b" + Pattern.quote(word) + "\\b", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(text);
            int count = 0;
            while (matcher.find()) {
                count++;
            }
            JOptionPane.showMessageDialog(this, "Kata '" + word + "' ditemukan " + count + " kali.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new aplikasipenghitungkata().setVisible(true);
            }
        });
    }
}

