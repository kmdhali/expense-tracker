package com.demo.kmd.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genId;                                 //generated id locally in database .

    private String userId;
    private String password;
    private String passwordHash;
    private Date lastLoginFailedLoginDt;
    private Date lastSuccessfulLoginDt;
    private Date creationDt;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Date getLastLoginFailedLoginDt() {
        return lastLoginFailedLoginDt;
    }

    public void setLastLoginFailedLoginDt(Date lastLoginFailedLoginDt) {
        this.lastLoginFailedLoginDt = lastLoginFailedLoginDt;
    }

    public Date getLastSuccessfulLoginDt() {
        return lastSuccessfulLoginDt;
    }

    public void setLastSuccessfulLoginDt(Date lastSuccessfulLoginDt) {
        this.lastSuccessfulLoginDt = lastSuccessfulLoginDt;
    }

    public Date getCreationDt() {
        return creationDt;
    }

    public void setCreationDt(Date creationDt) {
        this.creationDt = creationDt;
    }
}
