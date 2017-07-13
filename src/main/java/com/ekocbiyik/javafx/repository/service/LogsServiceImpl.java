package com.ekocbiyik.javafx.repository.service;

import com.ekocbiyik.javafx.model.Logs;
import com.ekocbiyik.javafx.repository.dao.ILogsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by enbiya on 22.02.2017.
 */
@Service
public class LogsServiceImpl implements ILogsService {

    @Autowired
    private ILogsDao logsDao;

    @Override
    @Transactional
    public void saveLogs(Logs logs) {
        logsDao.saveLogs(logs);
    }

    @Override
    @Transactional
    public void deleteLogs(Logs logs) {
        logsDao.deleteLogs(logs);
    }

    @Override
    @Transactional
    public void deleteSendedLogs() {
        logsDao.deleteSendedLogs();
    }

    @Override
    @Transactional
    public List<Logs> getThisMonthLogs() {
        return logsDao.getThisMonthLogs();
    }

    @Override
    @Transactional
    public List<Logs> getBetweenLogs(LocalDate start, LocalDate end) {
        return logsDao.getBetweenLogs(start, end);
    }

    @Override
    @Transactional
    public List<Logs> getNotSendedLogs() {
        return logsDao.getNotSendedLogs();
    }

    @Override
    @Transactional
    public List<Logs> getAllLogs() {
        return logsDao.getAllLogs();
    }
}
