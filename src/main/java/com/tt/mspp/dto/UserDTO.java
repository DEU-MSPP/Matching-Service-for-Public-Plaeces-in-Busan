package com.tt.mspp.dto;

import lombok.ToString;


@ToString

public class UserDTO {
    private String u_id;
    private String u_pw;
    private String u_name;
    private String u_nick;
    private String u_phone;
    private String u_path;

    public UserDTO() {
        //super();
    }


    public UserDTO(String u_id, String u_pw, String u_name, String u_nick, String u_phone, String u_path) {
        //super();
        this.u_id = u_id;
        this.u_pw = u_pw;
        this.u_name = u_name;
        this.u_nick = u_nick;
        this.u_phone = u_phone;
        this.u_path = u_path;
    }


    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getU_pw() {
        return u_pw;
    }

    public void setU_pw(String u_pw) {
        this.u_pw = u_pw;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_nick() {
        return u_nick;
    }

    public void setU_nick(String u_nick) {
        this.u_nick = u_nick;
    }

    public String getU_phone() {
        return u_phone;
    }

    public void setU_phone(String u_phone) {
        this.u_phone = u_phone;
    }

    public String getU_path() {
        return u_path;
    }

    public void setU_path(String u_path) {
        this.u_path = u_path;
    }


}
