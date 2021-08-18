package com.ats.blooddonor.network;

import android.widget.EditText;

import com.ats.blooddonor.Model.AuthResponse;
import com.ats.blooddonor.Model.CheckOtpResponse;
import com.ats.blooddonor.Model.ContactModel;
import com.ats.blooddonor.Model.FindDonorModel;
import com.ats.blooddonor.Model.LogoutModel;
import com.ats.blooddonor.Model.OtpModel;
import com.ats.blooddonor.Model.RequestBlood;
import com.ats.blooddonor.Model.SetDonorModel;
import com.ats.blooddonor.Model.SetReceiver;
import com.ats.blooddonor.Model.SignupResponse;
import com.ats.blooddonor.Model.UserResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APIService {


    @FormUrlEncoded
    @POST("signin")
    Call<AuthResponse> signin(@Field("full_name") String full_name,
                              @Field("password") String password);

    @FormUrlEncoded
    @POST("signup")
    Call<SignupResponse> signup(@Field("full_name") String full_name,
                                @Field("password") String password,
                                @Field("age") String age,
                                @Field("address") String address,
                                @Field("email") String email,
                                @Field("gender") String gender,
                                @Field("blood_group") String blood_group,
                                @Field("phone") String phone);

    @GET("user")
    Call<UserResponse> user(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("send-sms")
    Call<OtpModel> send_sms(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("check-code")
    Call<CheckOtpResponse> check_otp(@Field("phone") String phone,
                            @Field("code") String code);

    @FormUrlEncoded
    @POST("contact-us")
    Call<ContactModel> contactUs(@Field("full_name") String full_name,
                                 @Field("email") String email,
                                 @Field("phone") String phone,
                                 @Field("message") String message,
//                                 @Field("type") String type,
                                 @Header("Authorization") String token);
    @POST("logout")
    Call<LogoutModel> logout(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("request-blood")
    Call<RequestBlood> requestBlood(@Field("blood_unit") String blood_unit,
                                    @Field("blood_type") String blood_type,
                                    @Field("request_raison") String request_raison,
                                    @Field("hospital_name") String hospital_name,
                                    @Field("request_for") String request_for,
                                    @Field("additional_info") String additional_info,
                                    @Header("Authorization")String token);
    @FormUrlEncoded
    @POST("find-doner")
    Call<List<FindDonorModel>> findDonor(@Field("blood_group") String blood_group,
                                         @Field("address") String address,
                                         @Header("Authorization")String token);

    @GET("my-request")
    Call<List<Gson>> setRequest(@Header("Authorization") String token);

    @GET("set-doner")
    Call<SetDonorModel> setDoner(@Header("Authorization") String token);

    @GET("set-receiver")
    Call<SetDonorModel> setReceiver(@Header("Authorization") String token);



}
