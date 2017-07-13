package com.ekocbiyik.javafx.layouts.controller;

import com.ekocbiyik.javafx.ApplicationLoader;
import com.ekocbiyik.javafx.components.MessageDialog;
import com.ekocbiyik.javafx.enums.ContextParam;
import com.ekocbiyik.javafx.enums.DefaultSettings;
import com.ekocbiyik.javafx.enums.ETestStatus;
import com.ekocbiyik.javafx.enums.TestFieldsIDs;
import com.ekocbiyik.javafx.layouts.ui.AppWindow;
import com.ekocbiyik.javafx.model.Log;
import com.ekocbiyik.javafx.model.Logs;
import com.ekocbiyik.javafx.model.OfficeInfo;
import com.ekocbiyik.javafx.repository.service.IOfficeInfoService;
import com.ekocbiyik.javafx.teststeps.Accesstest;
import com.ekocbiyik.javafx.teststeps.BarcodeReadTest;
import com.ekocbiyik.javafx.teststeps.ChannelTest;
import com.ekocbiyik.javafx.teststeps.FirmwareTest;
import com.ekocbiyik.javafx.teststeps.LoginTest;
import com.ekocbiyik.javafx.teststeps.OpticTest;
import com.ekocbiyik.javafx.teststeps.StreamTest;
import com.ekocbiyik.javafx.utils.SaveLogs;
import com.ekocbiyik.javafx.utils.UtilsForSpring;
import com.sun.javafx.application.PlatformImpl;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

import static com.ekocbiyik.javafx.components.MessageDialog.showError;

/**
 * Created by enbiya on 23.06.2017.
 */
public class MainUIController implements Initializable {

    private static Logger logger = Logger.getLogger(MainUIController.class);

    public AnchorPane mainPanel;
    public Label lblModel;
    public Label lblSerialNo;
    public Label lblMac;

    public CheckBox cbxAccessTest;
    public CheckBox cbxBarcodeReadTest;
    public CheckBox cbxOpticTest;
    public CheckBox cbxFirmwareTest;
    public CheckBox cbxLoginTest;
    public CheckBox cbxStreamTest;
    public CheckBox cbxChannelTest;
    public CheckBox cbxSelectAll;

    public ProgressBar pbarAccessTest;
    public ProgressBar pbarBarcodeReadTest;
    public ProgressBar pbarOpticTest;
    public ProgressBar pbarFirmwareTest;
    public ProgressBar pbarLoginTest;
    public ProgressBar pbarStreamTest;
    public ProgressBar pbarChannelTest;

    public ImageView imgAccessTest;
    public ImageView imgBarcodeReadTest;
    public ImageView imgOpticTest;
    public ImageView imgFirmwareTest;
    public ImageView imgLoginTest;
    public ImageView imgStreamTest;
    public ImageView imgChannelTest;

    public CheckBox cbxChannel21;
    public CheckBox cbxChannel22;
    public CheckBox cbxChannel23;
    public CheckBox cbxChannel24;

    public Button btnStartTest;
    public Button btnEndTest;

    private Image imagePass;
    private Image imageFail;

    /** */
    private List<CheckBox> cbxList = new ArrayList<>();
    private List<CheckBox> selectedCbxList = new ArrayList<>();
    private List<ImageView> imgViewList = new ArrayList<>();
    private List<ProgressBar> pbarList = new ArrayList<>();

    /** */
    private Map<String, Object> mainContext = new LinkedHashMap<String, Object>();
    private SwingWorker<Void, Void> worker;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cbxAccessTest.setId(TestFieldsIDs.ACCESS_TEST);
        cbxBarcodeReadTest.setId(TestFieldsIDs.BARCODE_READ_TEST);
        cbxOpticTest.setId(TestFieldsIDs.OPTIC_TEST);
        cbxFirmwareTest.setId(TestFieldsIDs.FIRMWARE_TEST);
        cbxLoginTest.setId(TestFieldsIDs.LOGIN_TEST);
        cbxStreamTest.setId(TestFieldsIDs.STREAM_TEST);
        cbxChannelTest.setId(TestFieldsIDs.CHANNEL_TEST);

        /** Test akış sırası cbxList'e ekleniş sırasına göre ilerliyor!!! */
        cbxList.add(cbxAccessTest);
        cbxList.add(cbxBarcodeReadTest);
        cbxList.add(cbxOpticTest);
        cbxList.add(cbxFirmwareTest);
        cbxList.add(cbxLoginTest);
        cbxList.add(cbxStreamTest);
        cbxList.add(cbxChannelTest);
        cbxList.add(cbxSelectAll);

        /** pass/fail resimleri listeye ekle */
        imgViewList.add(imgAccessTest);
        imgViewList.add(imgBarcodeReadTest);
        imgViewList.add(imgOpticTest);
        imgViewList.add(imgFirmwareTest);
        imgViewList.add(imgLoginTest);
        imgViewList.add(imgStreamTest);
        imgViewList.add(imgChannelTest);

        /** progresBar'ları listeye ekle */
        pbarList.add(pbarAccessTest);
        pbarList.add(pbarBarcodeReadTest);
        pbarList.add(pbarOpticTest);
        pbarList.add(pbarFirmwareTest);
        pbarList.add(pbarLoginTest);
        pbarList.add(pbarStreamTest);
        pbarList.add(pbarChannelTest);

        /** progressbarları disable yap*/
        for (ProgressBar pbar : pbarList) {
            pbar.setDisable(true);
        }

        /** pass/fail resimleri visible=false yap*/
        for (ImageView img : imgViewList) {
            img.setVisible(false);
        }

        /** kanal cbx'lerini disable yap */
        cbxChannel21.setDisable(true);
        cbxChannel22.setDisable(true);
        cbxChannel23.setDisable(true);
        cbxChannel24.setDisable(true);

        btnEndTest.setDisable(true);

        imagePass = new Image("images/success.png");
        imageFail = new Image("images/error.png");
    }

    public void cbxSelectOnAction(ActionEvent e) {

        CheckBox cbx = (CheckBox) e.getSource();

        if (cbx == cbxSelectAll) {

            if (cbx.isSelected()) {
                for (CheckBox c : cbxList) {

                    if (!c.isDisabled()) {
                        c.setSelected(true);
                    }
                }
                logger.info("All test selected..");

            } else {
                for (CheckBox c : cbxList) {
                    if (!c.isDisabled()) {
                        c.setSelected(false);
                    }
                }
                logger.info("All test unSelected..");
            }

        } else {

            boolean isAllSelected = true;

            for (CheckBox c : cbxList) {
                if (!c.isSelected() && c != cbxSelectAll && !c.isDisabled()) {
                    isAllSelected = false;
                    break;
                }
            }

            if (isAllSelected) {
                cbxSelectAll.setSelected(true);
                logger.info("cbxSelectAll = true");
            } else {
                cbxSelectAll.setSelected(false);
                logger.info("cbxSelectAll = false");
            }

        }
    }

    public void btnStarTestOnClick(ActionEvent e) {

        boolean enAzBirSeciliVarMi = false;
        for (CheckBox c : cbxList) {
            if (c.isSelected()) {
                enAzBirSeciliVarMi = true;
                break;
            }
        }

        if (!enAzBirSeciliVarMi) {
            MessageDialog.showWarning("Lütfen test adımlarını işaretleyin!");
            logger.info("There is no any selected item!");
            return;
        }

        if ((mainContext.size() == 0) || (mainContext == null)) {
            mainContext = new HashMap<String, Object>();// burası linkedhashmap idi
            initMainContext();
        }

        /** önce hangi testler koşulacaksa onları seç */
        selectExecutedTests();

        /** sonra tüm testleri disable yap, hangisi koşulurken enable yaparsın */
        disableMainButtons();

        /** buraya kadar koşulmak istenen testleri seçtik, şimdi testleri çalıştır */
        executeAllSelectedTest();

    }

    public void btnEndTestOnClick(ActionEvent e) {

        ApplicationLoader.isFirst = true;
        logger.info("end test clicked!");

        new SaveLogs().execute(mainContext);

        mainContext.clear();
        logger.info("mainContext has been cleared: " + mainContext.toString());

        btnCancelTestOnClickAction(new ActionEvent());
    }

    public void btnCancelTestOnClickAction(ActionEvent e) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                worker.cancel(true);
                worker = null;

                AppWindow.mainUI.centerMainPanel = null;
                AppWindow.borderPane.setCenter(null);
                AppWindow.borderPane.setCenter(AppWindow.mainUI.getMainUI());
                AppWindow.borderPane.setAlignment(AppWindow.mainUI.getMainUI(), Pos.CENTER);
            }
        });
    }

    private void initMainContext() {

        /** her testi cbx id'si ile tut, bu idler ile arayüzdeki id'ler aynı olmalı!! */

        mainContext.put(ContextParam.STB_LDAP_USERNAME, DefaultSettings.STB_LDAP_USERNAME);
        mainContext.put(ContextParam.STB_LDAP_PASSWORD, DefaultSettings.STB_LDAP_PASSWORD);

        mainContext.put(TestFieldsIDs.ACCESS_TEST, new Accesstest());
        mainContext.put(TestFieldsIDs.BARCODE_READ_TEST, new BarcodeReadTest());
        mainContext.put(TestFieldsIDs.OPTIC_TEST, new OpticTest());
        mainContext.put(TestFieldsIDs.FIRMWARE_TEST, new FirmwareTest());
        mainContext.put(TestFieldsIDs.LOGIN_TEST, new LoginTest());
        mainContext.put(TestFieldsIDs.STREAM_TEST, new StreamTest());
        mainContext.put(TestFieldsIDs.CHANNEL_TEST, new ChannelTest());

        String executionId = UUID.randomUUID().toString();
        OfficeInfo office = UtilsForSpring.getSingleBeanOfType(IOfficeInfoService.class).getAllOfficeInfo().get(0);

        Logs logs = new Logs();
        logs.setExecutionId(executionId);
        logs.setSenderSerialNo(office.getOfficeId());
        logs.setLogList(new ArrayList<Log>());

        mainContext.put(ContextParam.CONTEXT_LOGS, logs);
    }

    private void selectExecutedTests() {

        /** bu metot hangi testler koşulmak isteniyorsa onları seçecek
         *  istenmeyen testlerin reRunStatus'u false olacak
         * */

        // her buraya düştüğünden sıfırlansın..
        selectedCbxList = new ArrayList<>();

        for (int i = 0; i < cbxList.size() - 1; i++) {

            CheckBox cbx = cbxList.get(i);

            // testi geçmişse veya koşulmak istenmiyorsa
            if (!cbx.isSelected() && cbx.isDisabled()) {

                if (cbx.getId() == cbxAccessTest.getId()) {
                    ((Accesstest) mainContext.get(TestFieldsIDs.ACCESS_TEST)).setReRunStatus(false);
                }
                if (cbx.getId() == cbxBarcodeReadTest.getId()) {
                    ((BarcodeReadTest) mainContext.get(TestFieldsIDs.BARCODE_READ_TEST)).setReRunStatus(false);
                }
                if (cbx.getId() == cbxOpticTest.getId()) {
                    ((OpticTest) mainContext.get(TestFieldsIDs.OPTIC_TEST)).setReRunStatus(false);
                }
                if (cbx.getId() == cbxFirmwareTest.getId()) {
                    ((FirmwareTest) mainContext.get(TestFieldsIDs.FIRMWARE_TEST)).setReRunStatus(false);
                }
                if (cbx.getId() == cbxLoginTest.getId()) {
                    ((LoginTest) mainContext.get(TestFieldsIDs.LOGIN_TEST)).setReRunStatus(false);
                }
                if (cbx.getId() == cbxStreamTest.getId()) {
                    ((StreamTest) mainContext.get(TestFieldsIDs.STREAM_TEST)).setReRunStatus(false);
                }
                if (cbx.getId() == cbxChannelTest.getId()) {
                    ((ChannelTest) mainContext.get(TestFieldsIDs.CHANNEL_TEST)).setReRunStatus(false);
                }
            }


            // test koşulmak için seçilmiş ve enable ise
            if (cbx.isSelected() && !cbx.isDisabled()) {

                if (cbx.getId() == cbxAccessTest.getId()) {
                    selectedCbxList.add(cbxAccessTest);
                    ((Accesstest) mainContext.get(TestFieldsIDs.ACCESS_TEST)).setReRunStatus(true);
                }
                if (cbx.getId() == cbxBarcodeReadTest.getId()) {
                    selectedCbxList.add(cbxBarcodeReadTest);
                    ((BarcodeReadTest) mainContext.get(TestFieldsIDs.BARCODE_READ_TEST)).setReRunStatus(true);
                }
                if (cbx.getId() == cbxOpticTest.getId()) {
                    selectedCbxList.add(cbxOpticTest);
                    ((OpticTest) mainContext.get(TestFieldsIDs.OPTIC_TEST)).setReRunStatus(true);
                }
                if (cbx.getId() == cbxFirmwareTest.getId()) {
                    selectedCbxList.add(cbxFirmwareTest);
                    ((FirmwareTest) mainContext.get(TestFieldsIDs.FIRMWARE_TEST)).setReRunStatus(true);
                }
                if (cbx.getId() == cbxLoginTest.getId()) {
                    selectedCbxList.add(cbxLoginTest);
                    ((LoginTest) mainContext.get(TestFieldsIDs.LOGIN_TEST)).setReRunStatus(true);
                }
                if (cbx.getId() == cbxStreamTest.getId()) {
                    selectedCbxList.add(cbxStreamTest);
                    ((StreamTest) mainContext.get(TestFieldsIDs.STREAM_TEST)).setReRunStatus(true);
                }
                if (cbx.getId() == cbxChannelTest.getId()) {
                    selectedCbxList.add(cbxChannelTest);
                    ((ChannelTest) mainContext.get(TestFieldsIDs.CHANNEL_TEST)).setReRunStatus(true);
                }
            }

            cbx.setSelected(false);
        }

        selectedCbxList.add(cbxSelectAll); // test koşulurken döngü buna gelince, showPassFailTests()'e gimek için
        cbxSelectAll.setSelected(false);//..
    }

    private void disableMainButtons() {

        logger.info("will disable some components");

        btnStartTest.setDisable(true);
        btnEndTest.setDisable(true);

        for (CheckBox c : cbxList) {
            c.setDisable(true);
        }
    }

    private void executeAllSelectedTest() {

        worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {

                for (int i = 0; i < selectedCbxList.size(); i++) {

                    //cancel tıklandığında testler iptal edilsin
                    if (!worker.isCancelled()) {

                        CheckBox testStep = selectedCbxList.get(i);

                        /** ACCESS TEST */
                        if (testStep.getId() == cbxAccessTest.getId()) {

                            Accesstest acTestBefore = (Accesstest) mainContext.get(TestFieldsIDs.ACCESS_TEST);
                            /** test fail ve tekrar koşulmak istenirse execute et */
                            if ((acTestBefore.getTestStatus() == ETestStatus.FAIL) && acTestBefore.getReRunStatus()) {

                                initProgressBar(true, pbarAccessTest, null, null);
                                mainContext = acTestBefore.execute(mainContext);

                                Accesstest acTestAfter = (Accesstest) mainContext.get(TestFieldsIDs.ACCESS_TEST);
                                initProgressBar(false, pbarAccessTest, imgAccessTest, acTestAfter.getTestStatus());

                                if (acTestAfter.getTestStatus() == ETestStatus.FAIL) {
                                    showError("Kumanda cihazı takılı değil veya konfigürasyon dosyası eksik!");
                                    btnStartTest.setDisable(true);
                                    btnEndTest.setDisable(false);
                                    return null;
                                }
                            }
                        }


                        /** BARCODE READ TEST */
                        if (testStep.getId() == cbxBarcodeReadTest.getId()) {

                            BarcodeReadTest brTestBefore = (BarcodeReadTest) mainContext.get(TestFieldsIDs.BARCODE_READ_TEST);
                            /** test fail ve tekrar koşulmak istenirse execute et */
                            if ((brTestBefore.getTestStatus() == ETestStatus.FAIL) && brTestBefore.getReRunStatus()) {

                                initProgressBar(true, pbarBarcodeReadTest, null, null);
                                mainContext = brTestBefore.execute(mainContext);

                                BarcodeReadTest brTestAfter = (BarcodeReadTest) mainContext.get(TestFieldsIDs.BARCODE_READ_TEST);
                                initProgressBar(false, pbarBarcodeReadTest, imgBarcodeReadTest, brTestAfter.getTestStatus());

                                if (brTestAfter.getTestStatus() == ETestStatus.FAIL) {
                                    showError("Barkod okuma hatası!");
                                    btnStartTest.setDisable(true);
                                    btnEndTest.setDisable(false);
                                    return null;
                                } else {

                                    /** başarılı ise barcode fieldlarını set et */
                                    setupBarcodeFields();
                                }
                            }
                        }


                        /** OPTIC TEST */
                        if (testStep.getId() == cbxOpticTest.getId()) {

                            OpticTest opticBefore = (OpticTest) mainContext.get(TestFieldsIDs.OPTIC_TEST);
                            /** test fail ve tekrar koşulmak istenirse execute et */
                            if ((opticBefore.getTestStatus() == ETestStatus.FAIL) && opticBefore.getReRunStatus()) {

                                initProgressBar(true, pbarOpticTest, null, null);
                                mainContext = opticBefore.execute(mainContext);

                                OpticTest opticAfter = (OpticTest) mainContext.get(TestFieldsIDs.OPTIC_TEST);
                                initProgressBar(false, pbarOpticTest, imgOpticTest, opticAfter.getTestStatus());

                                if (opticAfter.getTestStatus() == ETestStatus.FAIL) {
                                    /** buradan sonra devam etmemeli, optic çalışmıyorsa problem var! */
                                    MessageDialog.showError("Optic testi olmadan devam edemezsiniz!");
                                    btnStartTest.setDisable(true);
                                    btnEndTest.setDisable(false);
                                    return null;
                                }
                            }
                        }


                        /** FIRMWARE TEST */
                        if (testStep.getId() == cbxFirmwareTest.getId()) {

                            FirmwareTest firmwareTestBefore = (FirmwareTest) mainContext.get(TestFieldsIDs.FIRMWARE_TEST);
                            /** test fail ve tekrar koşulmak istenirse execute et */
                            if ((firmwareTestBefore.getTestStatus() == ETestStatus.FAIL) && firmwareTestBefore.getReRunStatus()) {

                                initProgressBar(true, pbarFirmwareTest, null, null);
                                mainContext = firmwareTestBefore.execute(mainContext);
                                FirmwareTest firmwareTestAfter = (FirmwareTest) mainContext.get(TestFieldsIDs.FIRMWARE_TEST);
                                initProgressBar(false, pbarFirmwareTest, imgFirmwareTest, firmwareTestAfter.getTestStatus());
                            }
                        }

                        /** LOGIN TEST */
                        if (testStep.getId() == cbxLoginTest.getId()) {

                            LoginTest loginTestBefore = (LoginTest) mainContext.get(TestFieldsIDs.LOGIN_TEST);
                            /** test fail ve tekrar koşulmak istenirse execute et */
                            if ((loginTestBefore.getTestStatus() == ETestStatus.FAIL) && loginTestBefore.getReRunStatus()) {

                                initProgressBar(true, pbarLoginTest, null, null);
                                mainContext = loginTestBefore.execute(mainContext);
                                LoginTest loginTestAfter = (LoginTest) mainContext.get(TestFieldsIDs.LOGIN_TEST);
                                initProgressBar(false, pbarLoginTest, imgLoginTest, loginTestAfter.getTestStatus());

                                if (loginTestAfter.getTestStatus() == ETestStatus.FAIL) {
                                    /** login test hatalıysa LDAP parametrelerinde hata var, set edilmesi gerekir */
                                    showError("LDAP Login bilgileri hatalı, lütfen yeni bilgileri sisteme girişini yapınız.");
                                    showPassFailTests();
                                    btnEndTest.setDisable(false);
                                    return null;
                                } else {

                                    /** burada bakod için fieldları doldur softwareden sonra da olabilir */
                                    setupBarcodeFields();
                                }
                            }
                        }

                        /** STREAM TEST */
                        if (testStep.getId() == cbxStreamTest.getId()) {

                            StreamTest streamTestBefore = (StreamTest) mainContext.get(TestFieldsIDs.STREAM_TEST);
                            /** test fail ve tekrar koşulmak istenirse execute et */
                            if ((streamTestBefore.getTestStatus() == ETestStatus.FAIL) && streamTestBefore.getReRunStatus()) {

                                initProgressBar(true, pbarStreamTest, null, null);
                                mainContext = streamTestBefore.execute(mainContext);
                                StreamTest streamTestAfter = (StreamTest) mainContext.get(TestFieldsIDs.STREAM_TEST);
                                initProgressBar(false, pbarStreamTest, imgStreamTest, streamTestAfter.getTestStatus());

                                if (streamTestAfter.getTestStatus() == ETestStatus.FAIL) {
                                    /**  fail olursa devam et */
                                }
                            }
                        }


                        /** CHANNEL TEST */
                        if (testStep.getId() == cbxChannelTest.getId()) {

                            ChannelTest channelTestBefore = (ChannelTest) mainContext.get(TestFieldsIDs.CHANNEL_TEST);
                            /** test fail ve tekrar koşulmak istenirse execute et */
                            if ((channelTestBefore.getTestStatus() == ETestStatus.FAIL) && channelTestBefore.getReRunStatus()) {

                                cbxChannel21.setSelected(false);
                                cbxChannel22.setSelected(false);
                                cbxChannel23.setSelected(false);
                                cbxChannel24.setSelected(false);

                                initProgressBar(true, pbarChannelTest, null, null);
                                mainContext = channelTestBefore.execute(mainContext, cbxChannel21, cbxChannel22, cbxChannel23, cbxChannel24);
                                ChannelTest channelTestAfter = (ChannelTest) mainContext.get(TestFieldsIDs.CHANNEL_TEST);
                                initProgressBar(false, pbarChannelTest, imgChannelTest, channelTestAfter.getTestStatus());

                                cbxChannel21.setSelected((Boolean) mainContext.get(ContextParam.STB_CHANNEL_21));
                                cbxChannel22.setSelected((Boolean) mainContext.get(ContextParam.STB_CHANNEL_22));
                                cbxChannel23.setSelected((Boolean) mainContext.get(ContextParam.STB_CHANNEL_23));
                                cbxChannel24.setSelected((Boolean) mainContext.get(ContextParam.STB_CHANNEL_24));
                            }
                        }

                        if (testStep == cbxSelectAll) {
                            // tüm seçilen test döngüleri bittiğinde bunu yapsın???
                            showPassFailTests();
                            ApplicationLoader.isFirst = false;
                        }
                    }
                }

                return null;
            }
        };
        worker.execute();
    }

    private void initProgressBar(boolean isStart, ProgressBar pbar, ImageView imgView, ETestStatus testResult) {

        /** isStart true ise sadece pbarı çalıştır
         *  false ise pbar'ı sonlandır, image'i göster
         * */
        if (isStart) {

            PlatformImpl.runAndWait(new Runnable() {
                @Override
                public void run() {
                    pbar.setDisable(false);
                    pbar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
                }
            });
        }

        if (!isStart) {

            PlatformImpl.runAndWait(new Runnable() {
                @Override
                public void run() {

                    pbar.setDisable(true);
                    pbar.setProgress(0);
                    imgView.setVisible(true);

                    if (testResult == ETestStatus.PASS) {
                        imgView.setImage(imagePass);
                    }

                    if (testResult == ETestStatus.FAIL) {
                        imgView.setImage(imageFail);
                    }

                }
            });
        }
    }

    private void setupBarcodeFields() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                lblModel.setText(mainContext.get(ContextParam.STB_MODEL).toString());
                lblSerialNo.setText(mainContext.get(ContextParam.STB_SERIAL_NO).toString());
                lblMac.setText(mainContext.get(ContextParam.STB_MAC).toString());
            }
        });
    }

    private void showPassFailTests() {

        /** fail olanlar enable olacak */
        /** testler bitti veya hata verdi şimdi fail olan testleri enable, pass olan testleri disable yap */

        Accesstest accessTest = (Accesstest) mainContext.get(TestFieldsIDs.ACCESS_TEST);
        cbxAccessTest.setDisable(accessTest.getTestStatus() == ETestStatus.PASS);

        BarcodeReadTest barcodeReadTest = (BarcodeReadTest) mainContext.get(TestFieldsIDs.BARCODE_READ_TEST);
        cbxBarcodeReadTest.setDisable(barcodeReadTest.getTestStatus() == ETestStatus.PASS);

        OpticTest opticTest = (OpticTest) mainContext.get(TestFieldsIDs.OPTIC_TEST);
        cbxOpticTest.setDisable(opticTest.getTestStatus() == ETestStatus.PASS);

        FirmwareTest firmwareTest = (FirmwareTest) mainContext.get(TestFieldsIDs.FIRMWARE_TEST);
        cbxFirmwareTest.setDisable(firmwareTest.getTestStatus() == ETestStatus.PASS);

        LoginTest loginTest = (LoginTest) mainContext.get(TestFieldsIDs.LOGIN_TEST);
        cbxLoginTest.setDisable(loginTest.getTestStatus() == ETestStatus.PASS);

        StreamTest streamTest = (StreamTest) mainContext.get(TestFieldsIDs.STREAM_TEST);
        cbxStreamTest.setDisable(streamTest.getTestStatus() == ETestStatus.PASS);

        ChannelTest channelTest = (ChannelTest) mainContext.get(TestFieldsIDs.CHANNEL_TEST);
        cbxChannelTest.setDisable(channelTest.getTestStatus() == ETestStatus.PASS);

        enableMainButtons();
    }

    private void enableMainButtons() {

        /** bu metot test çalıştı, hata var veya tekrar koşulacak testler var
         *  o zaman çağırılacak
         *  pass olan testler disable kalacak
         *
         *  test sona erdirilebilecek
         * */

        logger.info("will enable some components");

        btnStartTest.setDisable(false);
        btnEndTest.setDisable(false);

        boolean isAllTestDisable = true;
        for (CheckBox c : cbxList) {
            if (!c.isDisabled() && c != cbxSelectAll) {
                isAllTestDisable = false;
                break;
            }
        }

        //tüm testler başarılıysa startTest ve selectAll disable olsun
        cbxSelectAll.setDisable(isAllTestDisable);
        btnStartTest.setDisable(isAllTestDisable);
    }

}
