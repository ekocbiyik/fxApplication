package com.ekocbiyik.javafx.repository.dao;

import com.ekocbiyik.javafx.model.OfficeInfo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by enbiya on 27.10.2016.
 */
@Component
public class OfficeInfoDaoImpl implements IOfficeInfoDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void saveOfficeInfo(OfficeInfo info) {
        getCurrentSession().saveOrUpdate(info);
    }

    @Override
    public void deleteOfficeInfo(OfficeInfo info) {
        getCurrentSession().delete(info);
    }

    @Override
    public List<OfficeInfo> getAllOfficeInfo() {
        return getCurrentSession().createQuery("from OfficeInfo").list();
    }
}
