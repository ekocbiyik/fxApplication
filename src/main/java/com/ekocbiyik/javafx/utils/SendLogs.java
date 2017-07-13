package com.ekocbiyik.javafx.utils;

import com.ekocbiyik.javafx.model.Log;
import com.ekocbiyik.javafx.model.Logs;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ekocbiyik.javafx.repository.service.ILogsService;
import com.ekocbiyik.javafx.repository.service.IOfficeInfoService;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ekocbiyik.javafx.utils.UtilsForSpring.getSingleBeanOfType;

/**
 * Created by enbiya on 17.02.2017.
 */
public class SendLogs {

    private Logger logger = Logger.getLogger(SendLogs.class);

    private String distributorID;
    private String postAccessTokenUrl = "https://ekocbiyik.com:81/accesstoken";
    private String DEFAULT_POST_LOGS_URL = "https://ekocbiyik.com:81/logs";

    private String serviceUsername = "ekocbiyik.com";
    private String servicePassword = "ekocbiyik.com";

    private Gson gson = new Gson();
    private Type gsonMapType = new TypeToken<Map<String, String>>() {
    }.getType();
    private String CONTENT_TYPE = "application/json;charset=UTF-8";
    private String CHAR_ENCODING = "UTF-8";
    private String RESPONSE_ID_KEY = "id";

    private String LOCAL_ADDRESS_TOKEN = null;
    private String LOGS_HEADER_KEY_SERIAL_NO = "serialNo";
    private String LOGS_HEADER_KEY_ACCESS_TOKEN = "accessToken";


    private ILogsService logsService;

    public SendLogs() {
    }

    public SendLogs(List<Logs> waitingLogs) {

        logsService = getSingleBeanOfType(ILogsService.class);
        distributorID = getSingleBeanOfType(IOfficeInfoService.class).getAllOfficeInfo().get(0).getOfficeId();

        for (Logs logs : waitingLogs) {

            try {

                postLogs(logs);
            } catch (Exception e) {
                logger.error("Log post operation was cancelled for HttpHostConnection: " + e.getMessage());
            }
        }

        logger.info("All Logs has been sent!");
    }

    public boolean sendLogForTest(String officeId, Logs logs) {

        distributorID = officeId;
        return postLogs(logs);
    }

    private boolean postLogs(Logs logs) {

        boolean result = false;

        try {

            HttpPost httpPost = new HttpPost(DEFAULT_POST_LOGS_URL);
            UsernamePasswordCredentials creds = new UsernamePasswordCredentials(serviceUsername, servicePassword);
            httpPost.addHeader(new BasicScheme().authenticate(creds, httpPost));

            URI uri = new URI(DEFAULT_POST_LOGS_URL);
            SpecialHttpsClient httpClient = new SpecialHttpsClient(uri);

            String content = convertToMyJSON(logs);
            StringEntity entity = new StringEntity(content, CHAR_ENCODING);
            entity.setContentType(CONTENT_TYPE);

            httpPost.addHeader(new BasicHeader(LOGS_HEADER_KEY_SERIAL_NO, distributorID));
            httpPost.addHeader(new BasicHeader(LOGS_HEADER_KEY_ACCESS_TOKEN, updateAccessToken()));
            httpPost.setEntity(entity);

            logger.info("Trying to connect: " + httpPost.getURI().toString());

            HttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {

                logs.setSent(true);
                logs.setSentDate(new Date());
                logger.info("Logs id " + logs.getExecutionId() + " is sent - " + response.getStatusLine().getStatusCode());

                if (logsService != null) {
                    logsService.saveLogs(logs);
                }

                result = true;

            } else {
                logger.info("Failed while posting Log: " + response.getStatusLine().getStatusCode());
                result = false;
            }

            httpClient.getConnectionManager().shutdown();

        } catch (AuthenticationException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally {
            return result;
        }

    }

    private String updateAccessToken() {

        logger.info("Getting an AccessToken for post log..");

        String id = null;

        try {

            HttpPost httpPost = new HttpPost(postAccessTokenUrl);
            UsernamePasswordCredentials cred = new UsernamePasswordCredentials(serviceUsername, servicePassword);
            httpPost.addHeader(new BasicScheme().authenticate(cred, httpPost));

            URI uri = new URI(DEFAULT_POST_LOGS_URL);
            SpecialHttpsClient httpClient = new SpecialHttpsClient(uri);
            Map<String, String> map = new HashMap<String, String>();
            map.put("serialNo", distributorID);

            String content = gson.toJson(map, gsonMapType);
            StringEntity entity = new StringEntity(content, CHAR_ENCODING);
            entity.setContentType(CONTENT_TYPE);
            httpPost.setEntity(entity);

            logger.info("Trying to get AccessToken..");
            HttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {

                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                HashMap<String, String> responseMap = gson.fromJson(rd, gsonMapType);
                logger.info("AccessToken responseMap: " + responseMap);

                if (responseMap != null && responseMap.size() != 0 && responseMap.containsKey(RESPONSE_ID_KEY)) {

                    id = responseMap.get(RESPONSE_ID_KEY);
                    logger.info("Has been gotten an AccessToken: " + LOCAL_ADDRESS_TOKEN);
                    return LOCAL_ADDRESS_TOKEN;
                }

            } else {
                logger.info("Failed while getting an AccessToken: " + response.getStatusLine().getStatusCode());
            }

            httpClient.getConnectionManager().shutdown();

        } catch (AuthenticationException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return id;
        }

    }

    private String convertToMyJSON(Logs logs) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

        String jsonObject = "{"
                + "\"executionId\":\"" + logs.getExecutionId() + "\","
                + "\"jobName\":\"" + logs.getJobName() + "\","
                + "\"startDate\":\"" + sdf.format(logs.getStartDate()) + "\","
                + "\"recordDate\":\"" + sdf.format(logs.getRecordDate()) + "\","
                + "\"sent\":\"" + logs.isSent() + "\","
                + "\"successful\":\"" + logs.isSuccessful() + "\","
                + "\"faultException\":\"" + logs.isFaultException() + "\","
                + "\"senderSerialNo\":\"" + logs.getSenderSerialNo() + "\","
                + "\"modemSerialNumber\":\"" + logs.getModemSerialNumber() + "\","
                + "\"logList\":[" + getLogString(logs) + "]"
                + "}";

        return jsonObject;
    }

    private String getLogString(Logs logs) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

        String logString = "";

        for (Log l : logs.getLogList()) {

            logString = logString
                    + "{"
                    + "\"id\":" + l.getId() + ","
                    + "\"executionId\":\"" + logs.getExecutionId() + "\","
                    + "\"startDate\":\"" + sdf.format(l.getStartDate()) + "\","
                    + "\"endDate\":\"" + sdf.format(l.getEndDate()) + "\","
                    + "\"logType\":\"" + l.getLogType() + "\","
                    + "\"scriptName\":\"" + l.getScriptName() + "\","
                    + "\"scriptVersion\":\"" + l.getScriptVersion() + "\","
                    + "\"successful\":" + l.isSuccessful() + ","
                    + "\"statusCode\":\"" + l.getStatusCode() + "\","
                    + "\"logDetails\":\"" + l.getLogDetails() + "\""
                    + "},";
        }

        return logString.substring(0, logString.lastIndexOf(","));
    }

}
