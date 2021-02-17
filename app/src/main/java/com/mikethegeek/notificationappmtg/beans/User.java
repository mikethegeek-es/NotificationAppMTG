package com.mikethegeek.notificationappmtg.beans;

import java.io.Serializable;

public class User implements Serializable {

    private String userId;
    private String username;
    private String name;
    private String email;
    private String urlImage;
    private String token;


    public User() {
    }

    public User(String userId, String username, String name, String email, String urlImage, String token) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.email = email;
        this.urlImage = urlImage;
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}