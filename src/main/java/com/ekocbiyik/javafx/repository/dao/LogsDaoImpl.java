package com.ekocbiyik.javafx.repository.dao;

import com.ekocbiyik.javafx.model.Logs;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by enbiya on 22.02.2017.
 */
@Component
public class LogsDaoImpl implements ILogsDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void saveLogs(Logs logs) {
        getCurrentSession().saveOrUpdate(logs);
    }

    @Override
    public void deleteLogs(Logs logs) {
        getCurrentSession().delete(logs);
    }

    @Override
    public void deleteSendedLogs() {

        // getSendStatus=true & 4 ay onceki veriler sil..
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -3);
        LocalDate before4months = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);

        Query query = getCurrentSession().createQuery("delete from Logs where sent = true and recordDate < :before4months");
        query.setParameter("before4months", java.sql.Date.valueOf(before4months));
        query.executeUpdate();
    }

    @Override
    public List<Logs> getThisMonthLogs() {

        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.MONTH, 1);
        LocalDate thisMonth = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
        Query query = getCurrentSession().createQuery("from Logs where recordDate >= :thisMonth order by recordDate desc");
        query.setParameter("thisMonth", java.sql.Date.valueOf(thisMonth));

        return query.getResultList();
    }

    @Override
    public List<Logs> getBetweenLogs(LocalDate start, LocalDate end) {

        Query query = getCurrentSession().createQuery("from Logs where recordDate >= :startDate and recordDate < :endDate order by recordDate desc");
        query.setParameter("startDate", java.sql.Date.valueOf(start));
        query.setParameter("endDate", java.sql.Date.valueOf(end.plusDays(1)));

        return query.getResultList();
    }

    @Override
    public List<Logs> getNotSendedLogs() {

        List<Logs> resultList = getCurrentSession().createQuery("from Logs where sent = false order by recordDate asc").getResultList();
        for (Logs l : resultList) {
            l.setSentDate(new Date());
        }
        return resultList;
    }

    @Override
    public List<Logs> getAllLogs() {
        return getCurrentSession().createQuery("from Logs order by recordDate desc").list();
    }
}
