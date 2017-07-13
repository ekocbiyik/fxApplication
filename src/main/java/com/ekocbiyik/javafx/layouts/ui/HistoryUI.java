package com.ekocbiyik.javafx.layouts.ui;

import com.ekocbiyik.javafx.layouts.controller.HistoryUIController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by enbiya on 23.06.2017.
 */
public class HistoryUI {

    private AnchorPane centerHistoryPanel;
    private HistoryUIController controller;

    public AnchorPane getHistoryUI() {

        if (centerHistoryPanel == null) {

            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/HistoryUI.fxml"));
                centerHistoryPanel = loader.load();
                controller = loader.getController();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return centerHistoryPanel;
    }

    public HistoryUIController getController() {
        return controller;
    }

}
