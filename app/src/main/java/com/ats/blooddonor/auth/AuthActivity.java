package com.ats.blooddonor.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.ats.blooddonor.R;
import com.ats.blooddonor.auth.fragments.LoginFragment;
import com.ats.blooddonor.auth.fragments.SelectLoginSignup;
import com.ats.blooddonor.auth.fragments.SignupFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class AuthActivity extends AppCompatActivity {

    @BindView(R.id.tv_signIn)
    TextView tv_signin;
    @BindView(R.id.tv_signup)
    TextView tv_signup;

    static String mainTag="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        loadFragmentFirst(new SelectLoginSignup(),"selectLoginSignup");
    }

    @OnClick(R.id.tv_signIn)
    void onSignInClick(){

        loadFragment(new LoginFragment(),"loginFragment");
    }

    @OnClick(R.id.tv_signup)
    void onSignupClick(){
        loadFragment(new SignupFragment(),"signupFragment");
    }

    public boolean loadFragmentFirst(Fragment fragment, String tag) {
        mainTag = tag;
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment, tag)
                    .commit();
            return true;
        }
        return false;
    }

    public boolean loadFragment(Fragment fragment, String tag) {
        mainTag = tag;
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment, tag)
                    .addToBackStack(tag)
                    .commit();
            return true;
        }
        return false;
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//
//        if (mainTag.equals("welcomeFragment")) {
//            loadFragmentFirst(new SignupFragment(), "signupFragment");
//        } else if (mainTag.equals("registerFragment")){
//            loadFragmentFirst(new SignupFragment(), "signupFragment");
//        }
//    }
}