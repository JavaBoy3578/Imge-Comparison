package com.leus;

import com.leus.GUI.Display;
import com.leus.IO.IOImage;
import com.leus.business.ComparisonImage;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Runner {

    private static BufferedImage firstImage = null;
    private static BufferedImage secondImage = null;
    private static BufferedImage resultImg = null;

    public static void main(String[] args) {

        Display window = new Display();
        window.createWindow("Image Comparison");

        JPanel panel = new JPanel();

        JButton firstImgPathButton = new JButton("Add path to first image");
        JButton secondImgPathButton = new JButton("Add path to second image");
        JButton resultButton = new JButton("Result");
        JButton saveResultButton = new JButton("Save result");

        JLabel resultImgLabel = new JLabel();
        JScrollPane scrollImg = new JScrollPane();

        firstImgPathButton.addActionListener(e -> {
            String path = JOptionPane.showInputDialog("Put path to first image");

            if (path != null && !path.equals("")) {
                try {
                     firstImage = IOImage.loadImage(path);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Incorrect path!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Path is empty");
            }
        });

        secondImgPathButton.addActionListener(e -> {
            String path = JOptionPane.showInputDialog("Put path to second image");

            if (path != null && !path.equals("")) {
                try {
                    secondImage = IOImage.loadImage(path);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Incorrect path!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Path is empty");
            }
        });

        resultButton.addActionListener(e -> {
            if (firstImage != null && secondImage != null) {
                resultImg = ComparisonImage.compareImages(firstImage, secondImage);
                JOptionPane.showMessageDialog(null, "Done!");
                resultImgLabel.setIcon(new ImageIcon(resultImg));
                scrollImg.getViewport().add(resultImgLabel);
            } else {
                JOptionPane.showMessageDialog(null, "You don't select all images!");
            }
        });

        saveResultButton.addActionListener(e -> {
            if (resultImg != null) {
                String path = JOptionPane.showInputDialog("Put path to result image");

                if (path != null && !path.equals("")) {
                    try {
                        IOImage.saveImage(resultImg, path);
                        JOptionPane.showMessageDialog(null, "Done!");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error, something went wrong!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Path is empty!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "You haven't got result!");
            }
        });

        panel.add(firstImgPathButton);
        panel.add(secondImgPathButton);
        panel.add(resultButton);
        panel.add(saveResultButton);

        window.addComponentNorth(panel);
        window.addComponentCenter(resultImgLabel);
        window.addComponentCenter(scrollImg);
    }
}
