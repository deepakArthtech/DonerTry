package com.ats.blooddonor.auth.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.blooddonor.Model.CheckOtpResponse;
import com.ats.blooddonor.Model.OtpModel;
import com.ats.blooddonor.R;
import com.ats.blooddonor.auth.AuthActivity;
import com.ats.blooddonor.home.HomeActivity;
import com.ats.blooddonor.home.RegistrationQues;
import com.ats.blooddonor.network.APIService;
import com.ats.blooddonor.network.RetroInstance;
import com.mukesh.OtpView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OtpFragment extends Fragment {

    @BindView(R.id.otp_view)
    OtpView otp_view;
    @BindView(R.id.verify_number)
    TextView verify_no;
    @BindView(R.id.resend_otp)
    TextView resend_otp;
//    @BindView(R.id.wrong_phone)
//    TextView wrong_phone;
    @BindView(R.id.imageView3)
    ImageView backPointer;

    APIService apiService;
    String getphone;
    Integer otp;
    Registration registration;
    OtpFragment fragment;
    @BindView(R.id.opt_timer)
    TextView opt_timer;
    SignupFragment signupFragment;
    String chronoText;
    OtpModel otpModel;
    Bundle bundle;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_otp, container, false);
        ButterKnife.bind(this,view);

        resend_otp.setEnabled(false);

        apiService = RetroInstance.getRetroClient().create(APIService.class);
        Bundle bundle = this.getArguments();
        if (bundle!=null)
        {
             otp = bundle.getInt("otp",0);
             getphone = bundle.getString("phone");
             if (otp!=0) {
                 Toast.makeText(getContext(), String.valueOf(otp), Toast.LENGTH_SHORT).show();
             }
             else {
                 getActivity().getSupportFragmentManager().popBackStack();
             }
            bundle.clear();
        }

        getChronoStop();

        return view;
    }

    private void getChronoStop() {
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                // Used for formatting digit to be in 2 digits only
                NumberFormat f = new DecimalFormat("00");
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                opt_timer.setText(f.format(min) + ":" + f.format(sec));
            }
            // When the task is over it will print 00:00:00 there
            public void onFinish() {
                opt_timer.setText("00:00");
                resend_otp.setEnabled(true);
            }
        }.start();

        }

    @OnClick(R.id.verify_number)
    void checkcode(){
        verifyOtp();
    }

    @OnClick(R.id.resend_otp)
    void resend_otp(){
        getChronoStop();
        getOtp();
    }
//
//    @OnClick(R.id.wrong_phone)
//    void backPointer(){
//        getActivity().getSupportFragmentManager().popBackStack();
//    }

    @OnClick(R.id.imageView3)
    void wrong_number(){
        getActivity().getSupportFragmentManager().popBackStack();
    }




    private void verifyOtp() {

        ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading");
        progressDialog.show();


         apiService.check_otp(getphone, otp_view.getText().toString()).enqueue(new Callback<CheckOtpResponse>() {
             @Override
             public void onResponse(Call<CheckOtpResponse> call, Response<CheckOtpResponse> response) {
                 progressDialog.dismiss();

                 if(response.isSuccessful()) {
                     String SuccessMsg = "verified";
                     String messege = response.body().getMessage();
                     if(response.code()==200){
                         if(SuccessMsg.equals(messege)) {
                             ((AuthActivity) getActivity()).loadFragment(new Registration(), "registerFragment");
                         }
                         else {
                             Toast.makeText(getContext(), "Invalid OTP", Toast.LENGTH_SHORT).show();
                         }
                 }


                 }


             }

             @Override
             public void onFailure(Call<CheckOtpResponse> call, Throwable t) {
                 progressDialog.dismiss();
             }
         });

    }

    public void getOtp()
    {
//        Double Phone_no = Double.parseDouble(phoneno.getText().toString());
        apiService.send_sms(getphone).enqueue(new Callback<OtpModel>() {
            @Override
            public void onResponse(Call<OtpModel> call, Response<OtpModel> response) {
                Log.d("SignupFragment", "onResponse response:: " + response);
                otpModel = response.body();
                bundle = new Bundle();
                Log.d("Send sms", "onResponse response:: " + response);
                if (response.code()==200) {
                    OtpFragment otpFragment = new OtpFragment();
                    bundle.putString("phone", response.body().getPhone());
                    bundle.putInt("otp",otpModel.getCode());
                    otpFragment.setArguments(bundle);
                    Toast.makeText(getContext(),String.valueOf(response.body().getCode()),Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<OtpModel> call, Throwable t) {

            }
        });
    }


}