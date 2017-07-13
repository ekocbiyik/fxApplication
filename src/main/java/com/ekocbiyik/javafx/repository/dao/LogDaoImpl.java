package com.ekocbiyik.javafx.repository.dao;

import com.ekocbiyik.javafx.model.Log;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by enbiya on 22.02.2017.
 */
@Component
public class LogDaoImpl implements ILogDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void saveLog(Log log) {
        getCurrentSession().saveOrUpdate(log);
    }

    @Override
    public void deleteLog(Log log) {
        getCurrentSession().delete(log);
    }

    @Override
    public List<Log> getAllLog(String executionId) {

        Query query = getCurrentSession().createQuery("from Log where executionId = :executionid");
        query.setParameter("executionid", executionId);

        return query.getResultList();
    }
}
