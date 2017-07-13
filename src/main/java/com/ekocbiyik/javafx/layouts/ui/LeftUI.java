package com.ekocbiyik.javafx.layouts.ui;

import com.ekocbiyik.javafx.layouts.controller.LeftUIController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * Created by enbiya on 10.07.2017.
 */
public class LeftUI {

    private HBox leftPanel;
    private LeftUIController controller;


    public HBox getLeftUI() {

        if (leftPanel == null) {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/LeftUI.fxml"));
                leftPanel = loader.load();
                controller = loader.getController();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return leftPanel;
    }

    public LeftUIController getController() {
        return controller;
    }


}
