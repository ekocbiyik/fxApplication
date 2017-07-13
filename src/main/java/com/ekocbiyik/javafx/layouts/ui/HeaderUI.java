package com.ekocbiyik.javafx.layouts.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import com.ekocbiyik.javafx.layouts.controller.HeaderUIController;

import java.io.IOException;

/**
 * Created by enbiya on 23.06.2017.
 */
public class HeaderUI {

    private AnchorPane headerUI;
    private HeaderUIController controller;

    public AnchorPane getHeaderUI() {

        if (headerUI == null) {

            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/HeaderUI.fxml"));
                headerUI = loader.load();
                controller = loader.getController();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return headerUI;
    }

    public HeaderUIController getController() {
        return controller;
    }

}
