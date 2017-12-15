package com.leus.GUI;

import javax.swing.*;
import java.awt.*;

public class Display {
    private JFrame window = new JFrame();
    private int width;
    private int height;

    public Display() {
        width = 1024;
        height = 600;
    }

    public Display(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void createWindow(String title) {
        window.setTitle(title);
        window.setSize(width, height);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.setBackground(Color.white);
        window.setLocationRelativeTo(null);
        window.setLayout(new BorderLayout());
    }

    public void addComponentNorth(Component comp) {
        window.getContentPane().add(comp, BorderLayout.NORTH);
    }

    public void addComponentCenter(Component comp) {
        window.getContentPane().add(comp, BorderLayout.CENTER);
    }
}
