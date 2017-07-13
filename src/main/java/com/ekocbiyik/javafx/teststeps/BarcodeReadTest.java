package com.ekocbiyik.javafx.teststeps;

import com.ekocbiyik.javafx.components.InputDialog;
import com.ekocbiyik.javafx.configuration.StbConfiguration;
import com.ekocbiyik.javafx.enums.ETestStatus;
import com.ekocbiyik.javafx.enums.TestFieldsIDs;
import com.ekocbiyik.javafx.model.Log;
import com.ekocbiyik.javafx.model.Logs;
import com.ekocbiyik.javafx.utils.TimeUtils;
import com.ekocbiyik.javafx.enums.ContextParam;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.Map;

/**
 * Created by enbiya on 04.07.2017.
 */
public class BarcodeReadTest {

    private Logger logger = Logger.getLogger(BarcodeReadTest.class);
    private String testName = TestFieldsIDs.BARCODE_READ_TEST;
    private ETestStatus testStatus = ETestStatus.FAIL;   // fail-pass
    private boolean reRunStatus = true;

    public Map<String, Object> execute(Map<String, Object> mainContext) {

        logger.info("Barcode okuma testi başladı");

        String serialNumber = "";
        while (serialNumber.length() < 5) {

            serialNumber = InputDialog.show("Barkod Okuma Ekranı", "Lütfen cihaz seri numarasını giriniz!", "Seri No:");
            if (serialNumber.length() < 5) {
                logger.info("Incorrect serialNumber: " + serialNumber);
            }
        }

        Map<String, String> stbInfo = new StbConfiguration().execute(serialNumber);
        if (stbInfo.containsKey(ContextParam.STB_SERIAL_NO)) {
            mainContext.put(ContextParam.STB_SERIAL_NO, stbInfo.get(ContextParam.STB_SERIAL_NO));
        }

        if (stbInfo.containsKey(ContextParam.STB_BRAND)) {
            mainContext.put(ContextParam.STB_BRAND, stbInfo.get(ContextParam.STB_BRAND));
        }

        if (stbInfo.containsKey(ContextParam.STB_MODEL)) {
            mainContext.put(ContextParam.STB_MODEL, stbInfo.get(ContextParam.STB_MODEL));
        }

        if (stbInfo.containsKey(ContextParam.STB_MAC)) {
            mainContext.put(ContextParam.STB_MAC, stbInfo.get(ContextParam.STB_MAC));
        }

        BarcodeReadTest brTest = (BarcodeReadTest) mainContext.get(TestFieldsIDs.BARCODE_READ_TEST);
        brTest.setTestStatus(ETestStatus.PASS);
        mainContext.put(TestFieldsIDs.BARCODE_READ_TEST, brTest);

        Logs logs = (Logs) mainContext.get(ContextParam.CONTEXT_LOGS);
        logs.getLogList().add(insertLog(logs));
        mainContext.put(ContextParam.CONTEXT_LOGS, logs);

        TimeUtils.sleep(2);

        logger.info("Barcode okuma testi bitti");

        return mainContext;
    }

    private Log insertLog(Logs logs) {

        Log log = new Log();
        log.setLogs(logs);
        log.setStartDate(new Date());
        log.setEndDate(new Date());
        log.setLogType("SCRIPT_INFO");
        log.setScriptVersion("61");
        log.setSuccessful(true);
        log.setScriptName(getTestName());
        log.setLogDetails(getTestName() + " - " + getTestStatus());

        return log;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public ETestStatus getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(ETestStatus testStatus) {
        this.testStatus = testStatus;
    }

    public boolean getReRunStatus() {
        return reRunStatus;
    }

    public void setReRunStatus(boolean reRunStatus) {
        this.reRunStatus = reRunStatus;
    }

}
