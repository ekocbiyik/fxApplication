package com.ekocbiyik.javafx.configuration;

import com.ekocbiyik.javafx.components.InputDialog;
import com.ekocbiyik.javafx.model.Log;
import com.ekocbiyik.javafx.model.Logs;
import com.ekocbiyik.javafx.model.OfficeInfo;
import com.ekocbiyik.javafx.repository.service.IOfficeInfoService;
import com.ekocbiyik.javafx.utils.InternetUtils;
import com.ekocbiyik.javafx.utils.UtilsForSpring;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

/**
 * Created by enbiya on 10.07.2017.
 */
public class OfficeIdConfiguration {

    private static Logger logger = Logger.getLogger(OfficeIdConfiguration.class);

    public boolean execute() {

        /** db de officeId varsa return true
         *  officeId yoksa internet bağlantısını kontrol et
         *          bağlantı yoksa uyarı ver programı kapat
         *          bağlantı varsa officeId ekranından id girişi yaptır kontrol ettir
         *  id işlemi başarılı ise devam et
         *      hatalı ise işlemi sonsuz tekrar
         * */

        IOfficeInfoService officeService = UtilsForSpring.getSingleBeanOfType(IOfficeInfoService.class);

        if (officeService.getAllOfficeInfo().size() > 0) {
            logger.info("OfficeId already exist!");
            return true;
        }

        logger.info("OfficeId no exist!");

        if (!InternetUtils.isConnected()) {
            logger.info("No any internet connection! Program will be closed!");
            return false;
        }

        /** bu döngüden true dönerse id tanımlaması tamam demektir. */
        boolean isExist = false;
        while (!isExist) {


            String officeId = InputDialog.show("Bayi Id Ekranı", "Lütfen bayi Id'nizi giriniz", "Id:");
            if (officeId.length() < 5) {

                logger.info("Incorrect office id: " + officeId);
            } else {
                isExist = checkForOfficeId(officeId);
            }

            if (isExist) {

                OfficeInfo office = new OfficeInfo();
                office.setOfficeId(officeId);
                office.setIptvUsername("deneme");
                office.setIptvPassword("12345");
                officeService.saveOfficeInfo(office);

                logger.info("Bayi id'si tanımlı, db'ye kaydet...");
                break;
            }
        }

        return true;
    }


    private boolean checkForOfficeId(String officeId) {

        logger.info("Trying to send a log for office: " + officeId);

        Logs logs = new Logs();
        logs.setExecutionId(UUID.randomUUID().toString());
        logs.setSenderSerialNo(officeId);// burası önemli
        logs.setJobName("");
        logs.setStartDate(new Date());
        logs.setRecordDate(new Date());
        logs.setSentDate(new Date());
        logs.setSent(false);
        logs.setSuccessful(true);
        logs.setFaultException(false);
        logs.setExceptionMessage("");
        logs.setModemSerialNumber("TEST_AMACLI_LOG");

        Log log1 = new Log();
        log1.setLogs(logs);
        log1.setStartDate(new Date());
        log1.setEndDate(new Date());
        log1.setLogType("SCRIPT_INFO");
        log1.setScriptVersion("61");
        log1.setSuccessful(true);
        log1.setScriptName("Bayi_id_testi");
        log1.setLogDetails("Bayi_id_testi - OK");

        logs.setLogList(Arrays.asList(log1));

        return true;
    }


}
