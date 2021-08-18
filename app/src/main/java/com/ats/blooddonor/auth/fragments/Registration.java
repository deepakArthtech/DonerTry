package com.ats.blooddonor.auth.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.ats.blooddonor.Model.SharedPreManager;
import com.ats.blooddonor.Model.SignupResponse;
import com.ats.blooddonor.R;
import com.ats.blooddonor.auth.AuthActivity;
import com.ats.blooddonor.home.FindDonor;
import com.ats.blooddonor.home.HomeActivity;
import com.ats.blooddonor.home.HomeFragment;
import com.ats.blooddonor.home.RegistrationQues;
import com.ats.blooddonor.home.WelcomeFragment;
import com.ats.blooddonor.network.APIService;
import com.ats.blooddonor.network.RetroInstance;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends Fragment
{

    APIService apiService;
    SignupResponse signupResponse;

    Boolean isUserName, isEmail, isPasswordValid, isGender, isAge, isBloodGroup, isPostalAddress;
    Dialog dialog;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.full_name)
    EditText full_name;
    @BindView(R.id.spi_reg_gender)
    Spinner gender;
    @BindView(R.id.et_email)
    EditText email;
    @BindView(R.id.et_phone)
    EditText phone;
    @BindView(R.id.age)
    EditText ages;
    @BindView(R.id.blood_group)
    EditText blood_group;
    @BindView(R.id.postal_address)
    EditText postal_address;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.registration_submit)
    TextView submit;
    @BindView(R.id.imageView2)
    ImageView backPointer;
    Bundle bundle;
    SharedPreManager sharedPreManager;

    String gender_type = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        apiService = RetroInstance.getRetroClient().create(APIService.class);
        ButterKnife.bind(this, view);
        dialog = new Dialog(getContext());
        sharedPreManager = new SharedPreManager(getContext());
        String[] genderlist = {"Male", "Female"};

        ArrayAdapter aa = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, genderlist);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(aa);


        return view;

    }
    @OnClick(R.id.registration_submit)
    void onSubmitClick() {

        setValidation();
    }

    public void setValidation() {

        if (full_name.getText().toString().trim().length() == 0) {
            full_name.setError("Username is not entered");
            isUserName = false;
        } else {
            isUserName = true;
        }

        if (email.getText().toString().isEmpty()) {
            email.setError("Plese Enter Email");
            isEmail = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            email.setError("Invalid Email address");
            isEmail = false;
        } else {
            isEmail = true;
        }

        if (password.getText().toString().isEmpty()) {
            password.setError("Plese Enter Password");
            isPasswordValid = false;
        } else if (password.getText().length() < 8) {
            password.setError("Password should be minimum 8 Characteres");
            isPasswordValid = false;
        } else {
            isPasswordValid = true;
        }

        if (ages.getText().toString().trim().length() == 0) {
            ages.setError("age is not entered");
            isAge = false;
        } else {
            isAge = true;
        }

        if (blood_group.getText().toString().trim().length() == 0) {
            blood_group.setError("Blood Group is not entered");
            isBloodGroup = false;
        }
//        else if (blood_group.getText().toString().trim()!= "A+"||"B+" ) {
//            password.setError("Password should be minimum 8 Characteres");
//            isPasswordValid = false;
//        }
        else {
            isBloodGroup = true;
        }

        if (postal_address.getText().toString().trim().length() < 4) {
            postal_address.setError("Plese Enter Valid Address");
            isPostalAddress = false;
        } else {
            isPostalAddress = true;
        }

        if (isUserName && isPasswordValid && isEmail && isPostalAddress && isBloodGroup && isAge) {


            WelcomeFragment welcomeFragment = new WelcomeFragment();
            progressBar.setVisibility(View.VISIBLE);
            bundle = new Bundle();

            String gender_t = gender.getSelectedItem().toString().toLowerCase();
            String name = full_name.getText().toString();
            String age = ages.getText().toString();
            String blood_type = blood_group.getText().toString();
            String address = postal_address.getText().toString();
            String get_password = password.getText().toString();
            String get_email = email.getText().toString();
            String get_phone = phone.getText().toString();
            bundle = new Bundle();
            bundle.putString("name", name);
            bundle.putString("gender", gender_t);
            bundle.putString("age", age);
            bundle.putString("blood_group", blood_type.toUpperCase());
            bundle.putString("address", address);
            bundle.putString("password", get_password);
            bundle.putString("email", get_email);
            bundle.putString("phone", get_phone);
            welcomeFragment.setArguments(bundle);
            ((AuthActivity) getActivity()).loadFragment(welcomeFragment, "RegistrationQues");
        }

    }
//    }
    @OnClick(R.id.imageView2)
    void backFragment() {
        ((AuthActivity) getActivity()).loadFragmentFirst(new LoginFragment(), "LoginFragment");
    }

}
