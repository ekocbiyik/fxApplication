package com.ekocbiyik.javafx.layouts.ui;

import com.ekocbiyik.javafx.layouts.controller.ConfigurationUIController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by enbiya on 23.06.2017.
 */
public class ConfigurationUI {

    private AnchorPane centerConfigurationUI;
    private ConfigurationUIController controller;


    public AnchorPane getConfigurationUI() {

        if (centerConfigurationUI == null) {

            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/ConfigurationUI.fxml"));
                centerConfigurationUI = loader.load();
                controller = loader.getController();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return centerConfigurationUI;
    }

    public ConfigurationUIController getController() {
        return controller;
    }

}
