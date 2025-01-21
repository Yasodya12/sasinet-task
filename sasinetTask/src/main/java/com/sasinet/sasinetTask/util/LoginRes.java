package com.sasinet.sasinetTask.util;

public class LoginRes {
    private String email;

    private Long id;

    private String token;

    public LoginRes() {
    }

    public LoginRes(String email, Long id, String token) {
        this.email = email;
        this.id = id;
        this.token = token;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}