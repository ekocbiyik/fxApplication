package com.ekocbiyik.javafx.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

/**
 * Created by enbiya on 21.02.2017.
 */
@Entity
@Table(name = "t_logs")
public class Logs {

    @Id
    @Column(name = "executionid")
    private String executionId;

    @OneToMany(mappedBy = "logs", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Log> logList;

    @Column(name = "senderserialno")
    private String senderSerialNo;//bayi id'si

    @Column(name = "jobname")
    private String jobName;//boş dursun

    @Column(name = "stardate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "recorddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recordDate;

    @Column(name = "sentdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sentDate;

    @Column(name = "sent")
    private boolean sent;

    @Column(name = "successful")
    private boolean successful;//test sonucu

    @Column(name = "faultexception")
    private boolean faultException;//false olacak

    @Column(name = "exceptionmessage")
    private String exceptionMessage;//null

    @Column(name = "modemserialnumber")
    private String modemSerialNumber;//modem seri nosu

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = "";
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public boolean isFaultException() {
        return faultException;
    }

    public void setFaultException(boolean faultException) {
        this.faultException = false;//böyle kalsın
    }

    public String getSenderSerialNo() {
        return senderSerialNo;
    }

    public void setSenderSerialNo(String senderSerialNo) {
        this.senderSerialNo = senderSerialNo;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = "";
    }

    public String getModemSerialNumber() {
        return modemSerialNumber;
    }

    public void setModemSerialNumber(String modemSerialNumber) {
        this.modemSerialNumber = modemSerialNumber;
    }

    public List<Log> getLogList() {
        return logList;
    }

    public void setLogList(List<Log> logList) {
        this.logList = logList;
    }
}
