package com.ekocbiyik.javafx.layouts.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import com.ekocbiyik.javafx.layouts.ui.AppWindow;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by enbiya on 23.06.2017.
 */
public class HeaderUIController implements Initializable {

    public AnchorPane headerPanel;
    public Label lblOfficeId;

    public Button btnMainUI;
    public Button btnHistoryUI;
    public Button btnConfigUI;
    public Button btnHelpUI;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /** todo bu kısımda veritabanı bağlantısı ile id alınacak */
        lblOfficeId.setText("Ekocbiyik A.Ş.");
    }

    public void btnMainUIOnClick(ActionEvent e) {
        AppWindow.borderPane.setCenter(AppWindow.mainUI.getMainUI());
    }

    public void btnHistoryUIOnClick(ActionEvent e) {
        AppWindow.borderPane.setCenter(AppWindow.historyUI.getHistoryUI());
    }

    public void btnConfigUIOnClick(ActionEvent e) {
        AppWindow.borderPane.setCenter(AppWindow.configurationUI.getConfigurationUI());
    }

    public void btnHelpUIOnClick(ActionEvent e) {
//        AppWindow.borderPane.setCenter(AppWindow.mainUI.getMainUI());
    }

}
