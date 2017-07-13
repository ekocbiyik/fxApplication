package com.ekocbiyik.javafx.redrathub;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by enbiya on 01.11.2016.
 */
public class GetResultByInputStream {

    private static Logger logger = Logger.getLogger(GetResultByInputStream.class);

    public static List<String> run(InputStream inputStream, InputStream errorStream) {

        List<String> inputResultList = new ArrayList<String>();

        BufferedReader input;
        try {

            input = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            String lineInput = null;
            while ((lineInput = input.readLine()) != null) {

                inputResultList.add(lineInput.trim());
                logger.info(lineInput);

                if (lineInput.contains("Found the following RedRat devices:")) {
                    logger.info(input.readLine());
                    inputResultList.add("true");
                    return inputResultList;
                }

                if (lineInput.contains("Signal DB data has not been set.")) {
                    logger.info(input.readLine());
                    return null;
                }
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //devam et..
        }

        return inputResultList;
    }

}
