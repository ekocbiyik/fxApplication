package com.ekocbiyik.javafx.layouts.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by enbiya on 23.06.2017.
 */
public class BottomUIController implements Initializable {

    public AnchorPane bottomPanel;
    public Label lblVersion;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /** todo bu kısım veritabanından gelecek */
        String version = "1.0 Beta";
        lblVersion.setText("Version: " + version);
    }
}
