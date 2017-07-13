package com.ekocbiyik.javafx.layouts.ui;

import com.ekocbiyik.javafx.components.MessageDialog;
import com.ekocbiyik.javafx.configuration.OfficeIdConfiguration;
import com.ekocbiyik.javafx.redrathub.RedRat;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import org.apache.log4j.Logger;

/**
 * Created by enbiya on 13.10.2016.
 */
public class AppWindow {

    public static BorderPane borderPane;// static olması problem?
    //centerPanels..
    public static HeaderUI headerUI;
    public static BottomUI bottomUI;
    public static LeftUI leftUI;
    public static MainUI mainUI;
    public static HistoryUI historyUI;
    public static ConfigurationUI configurationUI;
    private Logger logger = Logger.getLogger(AppWindow.class);

    public AppWindow() {

        copyRedRatDriver();//+
        officeIDchecking();//+
        ldapChecking();
        updateChecking();
        sendLogs2Server(); // +
        deleteOldLogsFromDB();// +

        initUiComponents();
    }

    private void copyRedRatDriver() {

        boolean isInstalled = new RedRat().installDriver();

        if (!isInstalled) {

            /** burda uygulama devam etsin, test yapmasını engelleriz */
            MessageDialog.showError("RedRat driver dosyası eksik!");
        }
    }

    private void officeIDchecking() {

        logger.info("officeId checking...");

        if (!new OfficeIdConfiguration().execute()) {
            MessageDialog.showWarning("Lütfen internet bağlantınızı kontrol edip yeniden deneyin!");
            System.exit(-1);
        }
    }

    private void ldapChecking() {

    }

    private void updateChecking() {
        /** todo burada güncelleme denetimi yap,
         * yeni varsa updater'ı çalıştır
         * */
    }

    private void sendLogs2Server() {
        /** todo logları arkaplanda gönder */
    }

    private void deleteOldLogsFromDB() {
        /** todo eski logları arkaplanda sil */
    }

    private void initUiComponents() {

        headerUI = new HeaderUI();
        bottomUI = new BottomUI();
        leftUI = new LeftUI();

        //init center menus***************
        mainUI = new MainUI();
        historyUI = new HistoryUI();
        configurationUI = new ConfigurationUI();

    }

    public BorderPane getAppWindow() {

        if (borderPane == null) {

            borderPane = new BorderPane();
            borderPane.getStylesheets().add("Custom.css");
            borderPane.getStyleClass().add("borderpane-background");

            borderPane.setTop(headerUI.getHeaderUI());
            borderPane.setBottom(bottomUI.getBottomUI());
            borderPane.setLeft(leftUI.getLeftUI());
            borderPane.setCenter(mainUI.getMainUI());

            borderPane.setAlignment(borderPane.getCenter(), Pos.CENTER);
        }

        return borderPane;
    }

}
