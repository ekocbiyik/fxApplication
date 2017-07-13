package com.ekocbiyik.javafx.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * Created by enbiya on 10.07.2017.
 */
public class InternetUtils {

    public static boolean isConnected() {

        /** internet bağlantısını kontrol edecek */
        boolean result = false;

        try {

            HttpGet httpGet = new HttpGet("http://www.ekocbiyik.com/");
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(httpGet);

            if (response.getStatusLine().getStatusCode() == 200) {
                result = true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

}
