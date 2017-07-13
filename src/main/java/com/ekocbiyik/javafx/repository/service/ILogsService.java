package com.ekocbiyik.javafx.repository.service;

import com.ekocbiyik.javafx.model.Logs;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by enbiya on 22.02.2017.
 */
public interface ILogsService {

    void saveLogs(Logs logs);

    void deleteLogs(Logs logs);

    void deleteSendedLogs();

    List<Logs> getThisMonthLogs();

    List<Logs> getBetweenLogs(LocalDate start, LocalDate end);

    List<Logs> getNotSendedLogs();

    List<Logs> getAllLogs();

}
