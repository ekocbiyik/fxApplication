package com.ekocbiyik.javafx.repository.service;

import com.ekocbiyik.javafx.model.OfficeInfo;
import com.ekocbiyik.javafx.repository.dao.IOfficeInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by enbiya on 27.10.2016.
 */
@Service
public class OfficeInfoServiceImpl implements IOfficeInfoService {

    @Autowired
    private IOfficeInfoDao officeInfoDao;

    @Override
    @Transactional
    public void saveOfficeInfo(OfficeInfo info) {
        officeInfoDao.saveOfficeInfo(info);
    }

    @Override
    @Transactional
    public void deleteOfficeInfo(OfficeInfo info) {
        officeInfoDao.deleteOfficeInfo(info);
    }

    @Override
    @Transactional
    public List<OfficeInfo> getAllOfficeInfo() {
        return officeInfoDao.getAllOfficeInfo();
    }
}
