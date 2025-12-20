package com.example.ShopNow.ExceptiomResponses;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class CredentialsErrorResponse {
    public int status;
    public String msg;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamps;
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LocalDateTime getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(LocalDateTime timestamps) {
        this.timestamps = timestamps;
    }

}
