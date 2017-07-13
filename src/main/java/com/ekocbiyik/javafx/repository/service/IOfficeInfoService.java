package com.ekocbiyik.javafx.repository.service;

import com.ekocbiyik.javafx.model.OfficeInfo;

import java.util.List;

/**
 * Created by enbiya on 27.10.2016.
 */
public interface IOfficeInfoService {

    void saveOfficeInfo(OfficeInfo info);

    void deleteOfficeInfo(OfficeInfo info);

    List<OfficeInfo> getAllOfficeInfo();

}
