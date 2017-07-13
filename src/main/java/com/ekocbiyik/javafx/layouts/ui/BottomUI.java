package com.ekocbiyik.javafx.layouts.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import com.ekocbiyik.javafx.layouts.controller.BottomUIController;

import java.io.IOException;

/**
 * Created by enbiya on 23.06.2017.
 */
public class BottomUI {

    private AnchorPane bottomUI;
    private BottomUIController controller;

    public AnchorPane getBottomUI() {

        if (bottomUI == null) {

            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/BottomUI.fxml"));
                bottomUI = loader.load();
                controller = loader.getController();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bottomUI;
    }


    public BottomUIController getController() {
        return controller;
    }


}
