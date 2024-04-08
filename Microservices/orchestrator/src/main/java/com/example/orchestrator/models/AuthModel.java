package com.example.orchestrator.models;

public class AuthModel {
    private Integer memberID;
    private String token;

    public Integer getMemberID() {
        return memberID;
    }

    public String getToken() {
        return token;
    }

    public void setMemberID(Integer memberID) {
        this.memberID = memberID;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AuthModel() {
    }

    public AuthModel(Integer memberID, String token) {
        this.memberID = memberID;
        this.token = token;
    }
}
