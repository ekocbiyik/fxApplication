package com.ekocbiyik.javafx.utils;

import com.ekocbiyik.javafx.enums.ContextParam;
import com.ekocbiyik.javafx.enums.ETestStatus;
import com.ekocbiyik.javafx.enums.TestFieldsIDs;
import com.ekocbiyik.javafx.model.Logs;
import com.ekocbiyik.javafx.repository.service.ILogsService;
import com.ekocbiyik.javafx.teststeps.Accesstest;
import com.ekocbiyik.javafx.teststeps.BarcodeReadTest;
import com.ekocbiyik.javafx.teststeps.ChannelTest;
import com.ekocbiyik.javafx.teststeps.FirmwareTest;
import com.ekocbiyik.javafx.teststeps.LoginTest;
import com.ekocbiyik.javafx.teststeps.StreamTest;
import com.ekocbiyik.javafx.teststeps.OpticTest;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.Map;

/**
 * Created by enbiya on 28.11.2016.
 */
public class SaveLogs {


    private Logger logger = Logger.getLogger(SaveLogs.class);

    public void execute(Map<String, Object> mainContext) {

        Logs logs = (Logs) mainContext.get(ContextParam.CONTEXT_LOGS);
        String stbSerialNumber = (String) mainContext.get(ContextParam.STB_SERIAL_NO);

        try {

            logs.setJobName("");
            logs.setStartDate(new Date());
            logs.setSentDate(null);
            logs.setSent(false);
            logs.setFaultException(false);
            logs.setExceptionMessage(null);
            logs.setModemSerialNumber(stbSerialNumber);

            boolean accessTest = ((Accesstest) mainContext.get(TestFieldsIDs.ACCESS_TEST)).getTestStatus() == ETestStatus.PASS;
            boolean barcodeTest = ((BarcodeReadTest) mainContext.get(TestFieldsIDs.BARCODE_READ_TEST)).getTestStatus() == ETestStatus.PASS;
            boolean opticTest = ((OpticTest) mainContext.get(TestFieldsIDs.OPTIC_TEST)).getTestStatus() == ETestStatus.PASS;
            boolean firmwareTest = ((FirmwareTest) mainContext.get(TestFieldsIDs.FIRMWARE_TEST)).getTestStatus() == ETestStatus.PASS;
            boolean loginTest = ((LoginTest) mainContext.get(TestFieldsIDs.LOGIN_TEST)).getTestStatus() == ETestStatus.PASS;
            boolean streamTest = ((StreamTest) mainContext.get(TestFieldsIDs.STREAM_TEST)).getTestStatus() == ETestStatus.PASS;
            boolean channelTest = ((ChannelTest) mainContext.get(TestFieldsIDs.CHANNEL_TEST)).getTestStatus() == ETestStatus.PASS;

            if (accessTest && barcodeTest && opticTest && firmwareTest && loginTest && streamTest && channelTest) {
                logs.setSuccessful(true);
            } else {
                logs.setSuccessful(false);
            }

            logs.setRecordDate(new Date());

            // save ...
            UtilsForSpring.getSingleBeanOfType(ILogsService.class).saveLogs(logs);
            logger.info("logs has been saved: " + logs);

            logs = null;
            logger.info("logs has been cleared: " + logs);

        } catch (Exception e) {

            e.printStackTrace();
            logger.info("An error occured!\n" + e.toString());
            logger.info(mainContext);
            logger.info(logs);

        } finally {
            return;
        }

    }
}
