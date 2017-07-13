package com.ekocbiyik.javafx.components;

import com.sun.javafx.application.PlatformImpl;
import javafx.scene.control.Alert;

/**
 * Created by enbiya on 06.07.2017.
 */
public class MessageDialog {

    public static void showWarning(String message) {

        PlatformImpl.runAndWait(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Uyar!");
                alert.setHeaderText(null);
                alert.setContentText(message);
                alert.showAndWait();
            }
        });
    }

    public static void showError(String message) {

        PlatformImpl.runAndWait(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hata!");
                alert.setHeaderText(null);
                alert.setContentText(message);
                alert.showAndWait();
            }
        });

    }


}
