package com.ats.blooddonor.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignupResponse {

    @SerializedName("access_token")
    @Expose
    public String access_token;
    @SerializedName("type_token")
    @Expose
    public  String type_token;

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setType_token(String type_token) {
        this.type_token = type_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getType_token() {
        return type_token;
    }
}
