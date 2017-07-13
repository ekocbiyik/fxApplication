package com.ekocbiyik.javafx.layouts.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import com.ekocbiyik.javafx.layouts.ui.AppWindow;

/**
 * Created by enbiya on 10.07.2017.
 */
public class LeftUIController {

    public Button btnMainUI;
    public Button btnHistoryUI;
    public Button btnConfigUI;
    public Button btnHelpUI;

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
