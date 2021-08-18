package com.ats.blooddonor.auth.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;;
import com.ats.blooddonor.Model.AuthResponse;
import com.ats.blooddonor.Model.SharedPreManager;
import com.ats.blooddonor.Model.User;
import com.ats.blooddonor.R;
import com.ats.blooddonor.activities.MainActivity;
import com.ats.blooddonor.auth.AuthActivity;
import com.ats.blooddonor.home.ContactUsFragment;
import com.ats.blooddonor.home.HomeActivity;
import com.ats.blooddonor.home.HomeFragment;
import com.ats.blooddonor.network.APIService;
import com.ats.blooddonor.network.RetroInstance;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {


    @BindView(R.id.btn_continue)
    Button btn_login;
    @BindView(R.id.login_username)
    EditText login_username;
    @BindView(R.id.login_password)
    EditText login_password;
    @BindView(R.id.tv_register)
    TextView tv_login;
    Boolean isUserName, isPasswordValid;
    public static User user;
    APIService apiService;
    AuthResponse authResponse;
    SharedPreManager sharedPreManager;
    Window window;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this,view);
        apiService = RetroInstance.getRetroClient().create(APIService.class);
        sharedPreManager = new SharedPreManager(getContext());
        return view;
    }


    @OnClick(R.id.tv_register)
    void onRegisterClick(){
//        ((AuthActivity) getActivity()).getFragmentManager().popBackStack();
        ((AuthActivity) getActivity()).loadFragmentFirst(new SignupFragment(),"signupFragment");
    }

    @OnClick(R.id.btn_continue)
    void onContinueClick(){
//        ((AuthActivity) getActivity()).getFragmentManager().popBackStack();
        setValidation();
    }

    public void setValidation(){

        if(login_username.getText().toString().trim().length()==0){
            login_username.setError("Username is not entered");
            isUserName = false;
        }

        else{
            isUserName = true;
        }

        if (login_password.getText().toString().isEmpty()) {
            login_password.setError("Plese Enter Password");
            isPasswordValid = false;
        } else if (login_password.getText().length() < 8) {
            login_password.setError("Password should be minimum 8 Characteres");
            isPasswordValid = false;
        } else  {
            isPasswordValid = true;
        }
        if (isUserName && isPasswordValid) {
           getLogin();
//            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack()
        }

    }

    public void getLogin()
    {

        apiService.signin(login_username.getText().toString(), login_password.getText().toString()).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {

                Log.d("LoginFragment", "onResponse response:: " + response);
                authResponse = response.body();

                if (response.isSuccessful()) {

                    if(response.code()==200){
                        sharedPreManager.saveUser(authResponse.getAccess_token(),authResponse.getToken_type());
                        Intent i = new Intent(getActivity(), HomeActivity.class);
                        startActivity(i);

                        getActivity().finish();
//                        ((AuthActivity) getActivity()).loadFragment(new ContactUsFragment(),"signupFragment");


//                        Log.d("LoginFragment", "token type " + authResponse.getAccess_token());
//                        Log.d("LoginFragment", "aceess token " + authResponse.getToken_type());
                    }

                }
                else {
                    Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {


            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (sharedPreManager.isLoggedIn()) {

        }
    }
}