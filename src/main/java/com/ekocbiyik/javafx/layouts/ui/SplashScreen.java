package com.ekocbiyik.javafx.layouts.ui;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Created by enbiya on 19.10.2016.
 */
public class SplashScreen {


    public JFrame getInitWindow() {

        JFrame loadingFrame = new JFrame();

        URL url = this.getClass().getResource("/images/loading.gif");
        Icon icon = new ImageIcon(url);
        JLabel label = new JLabel(icon);

        loadingFrame.setIconImage(new ImageIcon(getClass().getResource("/images/icon.png").getPath()).getImage());
        loadingFrame.setUndecorated(true);
        loadingFrame.setBackground(new Color(0f, 0f, 0f, 0f));

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        loadingFrame.setLocation((int) ((screenBounds.getWidth() - 600) / 2), (int) ((screenBounds.getHeight() - 0) / 2));

        loadingFrame.getContentPane().add(label);

        loadingFrame.pack();
        loadingFrame.setLocationRelativeTo(null);
        loadingFrame.setVisible(true);

        return loadingFrame;
    }

}
