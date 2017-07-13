package com.ekocbiyik.javafx.repository.dao;

import com.ekocbiyik.javafx.model.OfficeInfo;

import java.util.List;

/**
 * Created by enbiya on 27.10.2016.
 */
public interface IOfficeInfoDao {

    void saveOfficeInfo(OfficeInfo info);

    void deleteOfficeInfo(OfficeInfo info);

    List<OfficeInfo> getAllOfficeInfo();

}
