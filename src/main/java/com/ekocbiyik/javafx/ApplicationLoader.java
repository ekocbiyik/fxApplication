package com.ekocbiyik.javafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import com.ekocbiyik.javafx.layouts.ui.AppWindow;
import com.ekocbiyik.javafx.layouts.ui.SplashScreen;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.*;

/**
 * Created by enbiya on 23.06.2017.
 */
public class ApplicationLoader extends Application {

    public static boolean isFirst = true;

    public static ApplicationContext applicationContext;

    public static JFrame loadingWindow;
    private static double APP_WINDOW_WIDTH = 990;
    private static double APP_WINDOW_HEIGHT = 700;
    private Logger logger = Logger.getLogger(AppWindow.class);

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage mainStage) throws Exception {

        logger.info("********************* Aplication has been started *********************");

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        try {

            if (!isProgramAlreadyRunning()) {
                //start...
                initAppScreen(mainStage);
            } else {
                throw new RuntimeException("********************* Aplication is already running! *********************");
            }

        } catch (Exception e) {
            logger.info(e.getMessage());
            System.exit(0);
        }
    }

    public void initAppScreen(final Stage mainStage) {

        loadingWindow = new SplashScreen().getInitWindow();
        applicationContext = new ClassPathXmlApplicationContext("hibernate-config.xml");

        Platform.runLater(new Runnable() {
            public void run() {

                Scene root = new Scene(new AppWindow().getAppWindow());
                mainStage.setScene(root);

                mainStage.setTitle("FX application");
                mainStage.getIcons().add(new Image("/images/icon.png"));
                mainStage.setOnCloseRequest(event -> System.exit(0));

                mainStage.setWidth(APP_WINDOW_WIDTH);
                mainStage.setHeight(APP_WINDOW_HEIGHT);
                mainStage.setMinWidth(APP_WINDOW_WIDTH);
                mainStage.setMinHeight(APP_WINDOW_HEIGHT);

                Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                mainStage.setX((screenBounds.getWidth() - APP_WINDOW_WIDTH) / 2);
                mainStage.setY((screenBounds.getHeight() - APP_WINDOW_HEIGHT) / 2);

                mainStage.show();
                loadingWindow.dispose();
            }
        });
    }

    private boolean isProgramAlreadyRunning() {
        return false;
    }

}
