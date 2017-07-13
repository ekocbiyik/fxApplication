package com.ekocbiyik.javafx.layouts.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import com.ekocbiyik.javafx.components.MessageDialog;
import com.ekocbiyik.javafx.model.Logs;
import com.ekocbiyik.javafx.repository.service.ILogsService;
import com.ekocbiyik.javafx.utils.UtilsForSpring;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by enbiya on 23.06.2017.
 */
public class HistoryUIController implements Initializable {

    public AnchorPane historyPanel;
    public TextField txtSerialNo;
    public DatePicker dpStartDate;
    public DatePicker dpEndDate;
    public Button btnList;
    public Button btnRefresh;
    public Button btnExport;
    public Label lblRecordSize;

    /** */
    public TableView<Logs> tblHistory;
    public TableColumn colExecutionId;
    public TableColumn colSerialNumber;
    public TableColumn colSuccessful;
    public TableColumn colSenderSerialNo;
    public TableColumn colJobName;
    public TableColumn colStartDate;
    public TableColumn colRecordDate;
    public TableColumn colSendDate;
    public TableColumn colSent;
    public TableColumn colFaultException;
    public TableColumn colExceptionMessage;


    /** */
    private ILogsService logsService;
    private FilteredList<Logs> filteredList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initCellFactories();
        initDateCellFactories();

        logsService = UtilsForSpring.getSingleBeanOfType(ILogsService.class);
        filteredList = new FilteredList<>(FXCollections.observableList(logsService.getThisMonthLogs()), s -> true);
        tblHistory.itemsProperty().addListener(((observable, oldValue, newValue) -> lblRecordSize.textProperty().bind(Bindings.size(newValue).asString())));
        tblHistory.setItems(filteredList);
        tblHistory.setMouseTransparent(false);
    }

    private void initCellFactories() {

        colExecutionId.setCellValueFactory(new PropertyValueFactory<Logs, String>("executionId"));
        colSerialNumber.setCellValueFactory(new PropertyValueFactory<Logs, String>("modemSerialNumber"));
        colSuccessful.setCellValueFactory(new PropertyValueFactory<Logs, String>("successful"));
        colSenderSerialNo.setCellValueFactory(new PropertyValueFactory<Logs, String>("senderSerialNo"));
        colJobName.setCellValueFactory(new PropertyValueFactory<Logs, String>("jobName"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<Logs, String>("startDate"));
        colRecordDate.setCellValueFactory(new PropertyValueFactory<Logs, String>("recordDate"));
        colSendDate.setCellValueFactory(new PropertyValueFactory<Logs, String>("sendDate"));
        colSent.setCellValueFactory(new PropertyValueFactory<Logs, String>("sent"));
        colFaultException.setCellValueFactory(new PropertyValueFactory<Logs, String>("faultException"));
        colExceptionMessage.setCellValueFactory(new PropertyValueFactory<Logs, String>("exceptionMessage"));
    }

    private void initDateCellFactories() {

        Callback<DatePicker, DateCell> endDateFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (dpStartDate.getValue() != null) {

                            if (item.isBefore(dpStartDate.getValue())) {
                                setDisable(true);
                                setStyle("-fx-background-color: #A0A0A0;");
                            }
                        }
                    }
                };
            }
        };

        dpEndDate.setDayCellFactory(endDateFactory);

        Callback<DatePicker, DateCell> startDateFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (dpEndDate.getValue() != null) {
                            if (item.isAfter(dpEndDate.getValue())) {
                                setDisable(true);
                                setStyle("-fx-background-color: #A0A0A0;");
                            }
                        }

                    }
                };
            }
        };

        dpStartDate.setDayCellFactory(startDateFactory);
    }

    public void btnListOnClickAction(ActionEvent e) {

        if (dpStartDate.getValue() != null && dpEndDate.getValue() != null) {
            filteredList = new FilteredList<>(FXCollections.observableList(logsService.getBetweenLogs(dpStartDate.getValue(), dpEndDate.getValue())));
            tblHistory.setItems(filteredList);
        }
    }

    public void btnRefreshOnClickAction(ActionEvent e) {

        txtSerialNo.clear();
        dpEndDate.setValue(null);
        dpStartDate.setValue(null);

        filteredList = new FilteredList<>(FXCollections.observableList(logsService.getThisMonthLogs()), s -> true);
        tblHistory.setItems(filteredList);
    }

    public void btnExportOnClickAction() {
        MessageDialog.showWarning("Not implemented yet!");
    }

    public void txtSerialNoOnKeyPress() {

        txtSerialNo.textProperty().addListener(observable -> {

            String filter = txtSerialNo.getText().toUpperCase();
            if (filter == null || filter.length() == 0) {
                filteredList.setPredicate(s -> true);
            } else {
                filteredList.setPredicate(s -> s.getModemSerialNumber().contains(filter));
            }
        });
    }

}
