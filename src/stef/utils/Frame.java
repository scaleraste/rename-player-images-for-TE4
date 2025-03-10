package stef.utils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Frame extends JFrame {
    private final JFrame frame;
    private final JTextField textField;
    private String path1;
    private String path2;

    public Frame() {
        frame = new JFrame("Rename Players");

        Container container = frame.getContentPane();
        container.setLayout(new GridLayout(3, 1));
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Stefano\\Desktop\\icon.ico"));
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);

        JLabel DB_label = new JLabel("Player's list File:");
        textField = new JTextField(20);
        JButton browseDBButton = new JButton("Browse");
        DB_label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        frame.add(DB_label);
        frame.add(textField);
        frame.add(browseDBButton);


        browseDBButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser1 = new JFileChooser();
                FileNameExtensionFilter filter1 = new FileNameExtensionFilter("Ini files", "ini");
                chooser1.setFileFilter(filter1);
                chooser1.showOpenDialog(frame);
                File file = chooser1.getSelectedFile();
                path1 = file.getAbsolutePath();
                textField.setText(path1);
            }
        });


        JTextField textField2 = new JTextField(20);
        JLabel filesToEdit_label = new JLabel("Files to rename Path:");
        JButton browseFolderButton = new JButton("Browse");
        filesToEdit_label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        frame.add(filesToEdit_label);
        frame.add(textField2);
        frame.add(browseFolderButton);


        browseFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser2 = new JFileChooser();
                chooser2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//                FileNameExtensionFilter filter2 = new FileNameExtensionFilter("Png files", "png");
//                chooser2.setFileFilter(filter2);
                chooser2.showOpenDialog(frame);
                File folder = chooser2.getSelectedFile();
                path2 = folder.getAbsolutePath();
                textField2.setText(path2);
            }
        });


        Button rename_button = new Button("Rename");
        rename_button.setBackground(Color.GREEN);
        frame.add(new Panel());
        frame.add(rename_button);

        rename_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (textField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please choose a file as a database");
                } else if (textField2.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please choose a folder to rename files");
                } else {
                    try {
                        TennisUtils.renamePlayers(path1, path2);
                        JOptionPane.showMessageDialog(frame, TennisUtils.output);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
