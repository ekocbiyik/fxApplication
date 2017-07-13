package com.ekocbiyik.javafx.components;

import com.sun.javafx.application.PlatformImpl;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

/**
 * Created by enbiya on 11.07.2017.
 */
public class InputDialog {

    public static String show(String title, String header, String content) {

        final String[] serialNumber = {""};

        PlatformImpl.runAndWait(new Runnable() {
            @Override
            public void run() {

                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle(title);
                dialog.setHeaderText(header);
                dialog.setContentText(content);
                Optional<String> result = dialog.showAndWait();

                serialNumber[0] = result.get();
            }
        });

        return serialNumber[0];
    }


}
