package com.jxutcm.equipmentMaintance.Bean;

public class User {
    private String uname;
    private String upwd;
    private String id;
    private String utype;

    public User() {
    }

    public User(String uname, String upwd) {
        this.uname = uname;
        this.upwd = upwd;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpwd() {
        return upwd;
    }

    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUtype() {
        return utype;
    }

    public void setUtype(String utype) {
        this.utype = utype;
    }
}
