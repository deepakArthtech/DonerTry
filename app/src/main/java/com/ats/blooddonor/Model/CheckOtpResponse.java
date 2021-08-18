package com.ats.blooddonor.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckOtpResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("phone")
    @Expose
    private String phone;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
