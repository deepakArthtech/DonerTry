package com.ats.blooddonor.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("request_type")
    @Expose
    private String requestType;
    @SerializedName("user_receiver")
    @Expose
    private Object userReceiver;
    @SerializedName("user_validator")
    @Expose
    private Object userValidator;
    @SerializedName("blood_unit")
    @Expose
    private Integer bloodUnit;
    @SerializedName("blood_type")
    @Expose
    private String bloodType;
    @SerializedName("request_raison")
    @Expose
    private String requestRaison;
    @SerializedName("hostipal_name")
    @Expose
    private String hostipalName;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("canceled")
    @Expose
    private Integer canceled;
    @SerializedName("request_for")
    @Expose
    private String requestFor;
    @SerializedName("additional_info")
    @Expose
    private String additionalInfo;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public Object getUserReceiver() {
        return userReceiver;
    }

    public void setUserReceiver(Object userReceiver) {
        this.userReceiver = userReceiver;
    }

    public Object getUserValidator() {
        return userValidator;
    }

    public void setUserValidator(Object userValidator) {
        this.userValidator = userValidator;
    }

    public Integer getBloodUnit() {
        return bloodUnit;
    }

    public void setBloodUnit(Integer bloodUnit) {
        this.bloodUnit = bloodUnit;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getRequestRaison() {
        return requestRaison;
    }

    public void setRequestRaison(String requestRaison) {
        this.requestRaison = requestRaison;
    }

    public String getHostipalName() {
        return hostipalName;
    }

    public void setHostipalName(String hostipalName) {
        this.hostipalName = hostipalName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCanceled() {
        return canceled;
    }

    public void setCanceled(Integer canceled) {
        this.canceled = canceled;
    }

    public String getRequestFor() {
        return requestFor;
    }

    public void setRequestFor(String requestFor) {
        this.requestFor = requestFor;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
