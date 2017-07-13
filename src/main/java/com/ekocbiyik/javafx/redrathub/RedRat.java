package com.ekocbiyik.javafx.redrathub;

import com.ekocbiyik.javafx.enums.DefaultSettings;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by enbiya on 10.07.2017.
 */
public class RedRat {

    public static Process p;
    public static String DEVICE_NAME = "";
    public static String DATA_SET = "";
    private static Logger logger = Logger.getLogger(RedRat.class);
    private static Client client;

    public static boolean installDriver() {

        logger.info("Driver will be installed...");

        boolean result = false;

        try {

            File srcFolder = new File(DefaultSettings.REDRAT_DRIVER_SRC_PATH);
            File destFolder = new File(DefaultSettings.REDRAT_DRIVER_DEST_PATH);

            if (!srcFolder.exists()) {

                logger.info("Directory does not exist: " + srcFolder.getName());

            } else {

                result = copyFolder(srcFolder, destFolder);
            }

            logger.info("RedRat driver installiation result: " + result);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            logger.info("Driver installiation has finished!");
            return result;
        }
    }

    private static boolean copyFolder(File src, File dest) {

        boolean result = false;

        try {

            if (src.isDirectory()) {

                if (!dest.exists()) {
                    dest.mkdir();
                    logger.info("Directory copied from " + src + "  to " + dest);
                }

                String files[] = src.list();
                logger.info("All files: " + Arrays.asList(src.listFiles()));

                for (String file : files) {

                    File srcFile = new File(src, file);
                    File destFile = new File(dest, file);
                    result = copyFolder(srcFile, destFile);
                }

            } else {

                if (!dest.exists()) {

                    InputStream in = new FileInputStream(src);
                    OutputStream out = new FileOutputStream(dest);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0) {
                        out.write(buffer, 0, length);
                    }

                    in.close();
                    out.close();
                    logger.info("File copied from " + src + " to " + dest);

                } else {
                    logger.info("File already exist: " + dest.getName());
                }

                result = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logger.info("copy operation has finished!");
            return result;
        }
    }

    public static boolean startRedRat() {

        final boolean[] isStarted = {false};

        try {

            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {

//                    p = Runtime.getRuntime().exec("cmd /C C:\\RedRatHub\\RedRatHubCmd.exe C:\\RedRatHub\\TivibuDB.xml --config=C:\\RedRatHub\\NLog.config");
                    p = Runtime.getRuntime().exec("cmd /C C:/RedRatHub/RedRatHubCmd.exe C:/RedRatHub/TivibuDB.xml");
                    isStarted[0] = GetResultByInputStream.run(p.getInputStream(), p.getErrorStream()) != null; // dönen değer null değilse redrat çalıştı!
                    return null;
                }
            };
            worker.run();

            TimeUnit.SECONDS.sleep(5);

        } catch (Exception e) {
            e.printStackTrace();
        }


        if (!p.isAlive() || !isStarted[0]) {
            return false;
        }

        try {

            client = new Client();
            client.openSocket("localhost", 40000);

            DEVICE_NAME = client.readData("hubquery=\"list redrats\"").split("]")[1].split("\n")[0].trim();
            DATA_SET = client.readData("hubquery=\"list datasets\"").split("\n")[1];

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            return true;
        }

    }

    public static void stopRedRat() {

        try {


            if (client != null) {

                /** redrat başlamamışsa client null */
                client.closeSocket();
            }

            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {

                    p = Runtime.getRuntime().exec("cmd /C tasklist");
                    List<String> resultList = GetResultByInputStream.run(p.getInputStream(), p.getErrorStream());

                    for (String i : resultList) {
                        if (i.contains("RedRatHubCmd.exe")) {
                            String pid = i.split("\\s+")[1];
                            System.out.println(pid);

                            Runtime.getRuntime().exec("cmd /C taskkill /f /pid " + pid);
                        }
                    }

                    return null;
                }
            };
            worker.run();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendCommand(String... command) {

        try {

            for (String cmd : command) {

//                client.sendMessage("name=\"" + DEVICE_NAME + "\" dataset=\"" + DATA_SET + "\" signal=\"" + cmd + "\"");
                TimeUnit.MILLISECONDS.sleep(500);
                logger.info(cmd + " signal send");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
