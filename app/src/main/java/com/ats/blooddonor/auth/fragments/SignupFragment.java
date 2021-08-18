package com.ats.blooddonor.auth.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.blooddonor.Model.AuthResponse;
import com.ats.blooddonor.Model.CheckOtpResponse;
import com.ats.blooddonor.Model.OtpModel;
import com.ats.blooddonor.Model.SharedPreManager;
import com.ats.blooddonor.R;
import com.ats.blooddonor.auth.AuthActivity;
import com.ats.blooddonor.home.RegistrationQues;
import com.ats.blooddonor.network.APIService;
import com.ats.blooddonor.network.RetroInstance;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupFragment extends Fragment {

    @BindView(R.id.phone)
    EditText phoneno;

    @BindView(R.id.btn_signup)
    Button signup;

    @BindView(R.id.tv_register)
    TextView tv_register;

    APIService apiService;
    OtpModel otpModel;
    Bundle bundle;
    Boolean isPhoneValid;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(this,view);
        apiService = RetroInstance.getRetroClient().create(APIService.class);

//        String value= phoneno.getText().toString();
//        int phoneno=Integer.parseInt(value);

//        Double phone_no = Double.parseDouble(phoneno.getText().toString());
        
        return view;
    }

    @OnClick(R.id.btn_signup)
    void onSignupClick()
    {
        setValidation();
    }

//    @OnClick(R.id.btn_signup)
//    void onGetOtp(){
//
//        setValidation();
//
//    }

    public void setValidation(){

        if (phoneno.getText().toString().isEmpty()) {
            phoneno.setError("Plese Enter Phone Number");
            isPhoneValid = false;
        } else if (phoneno.getText().length() !=10) {
            phoneno.setError("Invalid Phone Number");
            isPhoneValid = false;
        } else  {
            isPhoneValid = true;
        }
        if (isPhoneValid) {
            ((AuthActivity) getActivity()).loadFragment(new OtpFragment(),"otpFragment");
            getOtp();
        }

    }


    public void getOtp()
    {
          apiService.send_sms(phoneno.getText().toString()).enqueue(new Callback<OtpModel>() {
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
                    ((AuthActivity) getActivity()).loadFragment(otpFragment,"congrat fragment");
//Log.d("Registration", "phone no " + response.body().getPhone());
                    Log.d("Registration", "OTP " + response.body().getCode());

                }
            }
            @Override
            public void onFailure(Call<OtpModel> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.tv_register)
    void toLogin(){
        ((AuthActivity) getActivity()).loadFragmentFirst(new LoginFragment(),"loginFragment");
    }

}