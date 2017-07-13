package com.ekocbiyik.javafx.components;

import com.sun.javafx.application.PlatformImpl;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by enbiya on 11.07.2017.
 */
public class ConfirmDialog {

    public static boolean show(String message) {

        final boolean[] isConfirmed = {false};

        PlatformImpl.runAndWait(new Runnable() {
            @Override
            public void run() {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Lütfen doğrulayın!");
                alert.setHeaderText(null);
                alert.setContentText(message);

                alert.getButtonTypes().clear();
                ButtonType btnYes = new ButtonType("Evet", ButtonData.YES);
                ButtonType btnNo = new ButtonType("Hayır", ButtonData.NO);

                alert.getButtonTypes().addAll(btnYes, btnNo);

                Optional<ButtonType> result = alert.showAndWait();
                isConfirmed[0] = (result.get() == btnYes);
            }
        });

        return isConfirmed[0];
    }

    public static List<CheckBox> channelDialog() {

        CheckBox cbx21 = new CheckBox("21.kanal  ");
        CheckBox cbx22 = new CheckBox("22.kanal  ");
        CheckBox cbx23 = new CheckBox("23.kanal  ");
        CheckBox cbx24 = new CheckBox("24.kanal  ");

        cbx21.setSelected(true);
        cbx22.setSelected(true);
        cbx23.setSelected(true);
        cbx24.setSelected(true);

        cbx21.setId("cbx21");
        cbx22.setId("cbx22");
        cbx23.setId("cbx23");
        cbx24.setId("cbx24");

        PlatformImpl.runAndWait(new Runnable() {
            @Override
            public void run() {

                Dialog dialogPane = new Dialog();
                dialogPane.setTitle("Kanal doğrulama");
                dialogPane.setHeaderText("Kanal geçişi 2'şer saniye aralıklarla, başarılı bir şekilde oldu mu?");
                Label lbl = new Label("(başarısız olan kanalın işaretini kaldırın!)");

                GridPane grid = new GridPane();
                grid.add(new Label(), 4, 1);
                grid.add(cbx21, 1, 2);
                grid.add(cbx22, 2, 2);
                grid.add(cbx23, 3, 2);
                grid.add(cbx24, 4, 2);
                grid.add(new Label(), 4, 3);
                grid.add(lbl, 4, 4);
                dialogPane.getDialogPane().setContent(grid);

                ButtonType btnContinue = new ButtonType("Devam Et", ButtonData.OK_DONE);
                dialogPane.getDialogPane().getButtonTypes().add(btnContinue);
                dialogPane.showAndWait();
            }
        });

        return Arrays.asList(cbx21, cbx22, cbx23, cbx24);
    }

}
