package com.ekocbiyik.javafx.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by enbiya on 06.07.2017.
 */
@Entity
@Table(name = "t_office_info")
public class OfficeInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "office_id")
    private String officeId;

    @Column(name = "office_name")
    private String officeName;

    @Column(name = "location")
    private String location;

    @Column(name = "iptv_username")
    private String iptvUsername;

    @Column(name = "iptv_password")
    private String iptvPassword;

    public OfficeInfo() {
    }

    public OfficeInfo(String officeId) {
        this.officeId = officeId;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIptvUsername() {
        return iptvUsername;
    }

    public void setIptvUsername(String iptvUsername) {
        this.iptvUsername = iptvUsername;
    }

    public String getIptvPassword() {
        return iptvPassword;
    }

    public void setIptvPassword(String iptvPassword) {
        this.iptvPassword = iptvPassword;
    }
}
