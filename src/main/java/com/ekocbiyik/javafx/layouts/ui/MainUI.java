package com.ekocbiyik.javafx.layouts.ui;

import com.ekocbiyik.javafx.layouts.controller.MainUIController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by enbiya on 23.06.2017.
 */
public class MainUI {

    public AnchorPane centerMainPanel;
    private MainUIController controller;

    public AnchorPane getMainUI() {

        if (centerMainPanel == null) {

            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/MainUI.fxml"));
                centerMainPanel = loader.load();
                controller = loader.getController();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return centerMainPanel;
    }

    public MainUIController getController() {
        return controller;
    }

}
