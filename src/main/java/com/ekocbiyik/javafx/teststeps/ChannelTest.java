package com.ekocbiyik.javafx.teststeps;

import com.ekocbiyik.javafx.components.ConfirmDialog;
import com.ekocbiyik.javafx.enums.ContextParam;
import com.ekocbiyik.javafx.enums.ETestStatus;
import com.ekocbiyik.javafx.enums.RemoteControl;
import com.ekocbiyik.javafx.enums.TestFieldsIDs;
import com.ekocbiyik.javafx.model.Log;
import com.ekocbiyik.javafx.model.Logs;
import com.ekocbiyik.javafx.redrathub.RedRat;
import com.ekocbiyik.javafx.utils.TimeUtils;
import javafx.scene.control.CheckBox;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by enbiya on 04.07.2017.
 */
public class ChannelTest {

    private Logger logger = Logger.getLogger(ChannelTest.class);
    private String testName = TestFieldsIDs.CHANNEL_TEST;
    private ETestStatus testStatus = ETestStatus.FAIL;   // fail-pass
    private boolean reRunStatus = true;

    public Map<String, Object> execute(Map<String, Object> mainContext, CheckBox cbxChannel21, CheckBox cbxChannel22, CheckBox cbxChannel23, CheckBox cbxChannel24) {

        logger.info("kanal testi başladı");
        TimeUtils.sleep(2);

        ChannelTest channelTest = (ChannelTest) mainContext.get(TestFieldsIDs.CHANNEL_TEST);
        channelTest.setTestStatus(ETestStatus.PASS);


        cbxChannel21.getStyleClass().add("cbx-channel-send-signal");
        RedRat.sendCommand(RemoteControl.BTN_2, RemoteControl.BTN_1);
        cbxChannel21.getStyleClass().remove("cbx-channel-send-signal");
        TimeUtils.sleep(1);

        cbxChannel22.getStyleClass().add("cbx-channel-send-signal");
        RedRat.sendCommand(RemoteControl.BTN_CHANNEL_UP);
        cbxChannel22.getStyleClass().remove("cbx-channel-send-signal");
        TimeUtils.sleep(1);

        cbxChannel23.getStyleClass().add("cbx-channel-send-signal");
        RedRat.sendCommand(RemoteControl.BTN_CHANNEL_UP);
        cbxChannel23.getStyleClass().remove("cbx-channel-send-signal");
        TimeUtils.sleep(1);

        cbxChannel24.getStyleClass().add("cbx-channel-send-signal");
        RedRat.sendCommand(RemoteControl.BTN_CHANNEL_UP);
        cbxChannel24.getStyleClass().remove("cbx-channel-send-signal");
        TimeUtils.sleep(2);

        List<CheckBox> channelList = ConfirmDialog.channelDialog();
        for (CheckBox cbx : channelList) {
            if (!cbx.isSelected()) {
                channelTest.setTestStatus(ETestStatus.FAIL);
                break;
            }
        }

        mainContext.put(ContextParam.STB_CHANNEL_21, channelList.get(0).isSelected());
        mainContext.put(ContextParam.STB_CHANNEL_22, channelList.get(1).isSelected());
        mainContext.put(ContextParam.STB_CHANNEL_23, channelList.get(2).isSelected());
        mainContext.put(ContextParam.STB_CHANNEL_24, channelList.get(3).isSelected());

        mainContext.put(TestFieldsIDs.CHANNEL_TEST, channelTest);


        Logs logs = (Logs) mainContext.get(ContextParam.CONTEXT_LOGS);
        logs.getLogList().add(insertLog(logs));
        mainContext.put(ContextParam.CONTEXT_LOGS, logs);

        TimeUtils.sleep(2);
        logger.info("kanal testi bitti");

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
