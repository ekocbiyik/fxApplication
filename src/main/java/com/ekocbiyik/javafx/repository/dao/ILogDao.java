package com.ekocbiyik.javafx.repository.dao;

import com.ekocbiyik.javafx.model.Log;

import java.util.List;

/**
 * Created by enbiya on 22.02.2017.
 */
public interface ILogDao {

    void saveLog(Log log);

    void deleteLog(Log log);

    List<Log> getAllLog(String executionId);

}
