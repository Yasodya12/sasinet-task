package com.sasinet.sasinetTask.util;

public class LoginRes {
    private String email;

    private Long id;

    public LoginRes() {
    }

    public LoginRes(String email, Long id) {
        this.email = email;

        this.id=id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}