package com.ekocbiyik.javafx.teststeps;

import com.ekocbiyik.javafx.components.ConfirmDialog;
import com.ekocbiyik.javafx.enums.ContextParam;
import com.ekocbiyik.javafx.enums.ETestStatus;
import com.ekocbiyik.javafx.enums.TestFieldsIDs;
import com.ekocbiyik.javafx.model.Log;
import com.ekocbiyik.javafx.model.Logs;
import com.ekocbiyik.javafx.utils.TimeUtils;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.Map;

/**
 * Created by enbiya on 04.07.2017.
 */
public class OpticTest {

    private Logger logger = Logger.getLogger(OpticTest.class);
    private String testName = TestFieldsIDs.OPTIC_TEST;
    private ETestStatus testStatus = ETestStatus.FAIL;   // fail-pass
    private boolean reRunStatus = true;

    public Map<String, Object> execute(Map<String, Object> mainContext) {

        logger.info("optic testi başladı");

        boolean isOK = ConfirmDialog.show("TV açıldı mı?"); 

        OpticTest opticTest = (OpticTest) mainContext.get(TestFieldsIDs.OPTIC_TEST);
        opticTest.setTestStatus(isOK == true ? ETestStatus.PASS : ETestStatus.FAIL);
        mainContext.put(TestFieldsIDs.OPTIC_TEST, opticTest);

        Logs logs = (Logs) mainContext.get(ContextParam.CONTEXT_LOGS);
        logs.getLogList().add(insertLog(logs));
        mainContext.put(ContextParam.CONTEXT_LOGS, logs);

        TimeUtils.sleep(2);
        logger.info("optic testi bitti");

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
