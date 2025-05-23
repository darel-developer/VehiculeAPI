package com.propelize.vehicleapi.dto;

public class AuthResponse {
    private String token;
    private Long userId;
    private String userName;
    private String serverTime;

    // public AuthResponse(String token, Long userId, String userName, String serverTime) {
    public AuthResponse(String token,String userName, String serverTime) {
        this.token = token;
        // this.userId = userId;
        this.userName = userName;
        this.serverTime = serverTime;
    }

    // Getters et Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    // public Long getUserId() {
    //     return userId;
    // }

    // public void setUserId(Long userId) {
    //     this.userId = userId;
    // }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }
}