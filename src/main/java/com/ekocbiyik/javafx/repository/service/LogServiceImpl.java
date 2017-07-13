package com.ekocbiyik.javafx.repository.service;

import com.ekocbiyik.javafx.model.Log;
import com.ekocbiyik.javafx.repository.dao.ILogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by enbiya on 22.02.2017.
 */
@Service
public class LogServiceImpl implements ILogService {

    @Autowired
    private ILogDao logDao;

    @Override
    @Transactional
    public void saveLog(Log log) {
        logDao.saveLog(log);
    }

    @Override
    @Transactional
    public void deleteLog(Log log) {
        logDao.deleteLog(log);
    }

    @Override
    @Transactional
    public List<Log> getAllLog(String executionId) {
        return logDao.getAllLog(executionId);
    }
}
