package com.ats.blooddonor.home;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.blooddonor.Model.SetDonorModel;
import com.ats.blooddonor.Model.SetReceiver;
import com.ats.blooddonor.Model.SharedPreManager;
import com.ats.blooddonor.Model.SignupResponse;
import com.ats.blooddonor.R;
import com.ats.blooddonor.auth.AuthActivity;
import com.ats.blooddonor.auth.fragments.Registration;
import com.ats.blooddonor.auth.fragments.SignupFragment;
import com.ats.blooddonor.network.APIService;
import com.ats.blooddonor.network.RetroInstance;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationQues extends Fragment {

    @BindView(R.id.radio1)
    RadioButton radio1;
    @BindView(R.id.radio2)
    RadioButton radio2;
    @BindView(R.id.radio3)
    RadioButton radio3;
    @BindView(R.id.radio4)
    RadioButton radio4;
    @BindView(R.id.checkBox1)
    CheckBox checkBox1;
    @BindView(R.id.checkBox2)
    CheckBox checkBox2;
    @BindView(R.id.checkBox3)
    CheckBox checkBox3;
    @BindView(R.id.checkBox4)
    CheckBox checkBox4;
    @BindView(R.id.tv_registration_ques)
    TextView ques_submit;
    @BindView(R.id.iv_backQuestion)
    ImageView backPointer;

    APIService apiService;
    SharedPreManager sharedPreManager;
    SignupResponse signupResponse;
    Dialog dialog;

    String name, age, gender, password, email, blood_group, address, phone, token;


    Boolean isHealthy, isCheckboxgroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration_ques, container, false);
        ButterKnife.bind(this, view);

        apiService = RetroInstance.getRetroClient().create(APIService.class);

        sharedPreManager = new SharedPreManager(getContext());
        signupResponse = new SignupResponse();

        Bundle bundle = this.getArguments();

        dialog = new Dialog(getContext());

        if (bundle != null) {
            name = bundle.getString("name");
            gender = bundle.getString("gender");
            age = bundle.getString("age");
            blood_group = bundle.getString("blood_group");
            address = bundle.getString("address");
            password = bundle.getString("password");
            email = bundle.getString("email");
            phone = bundle.getString("phone");
        }


        return view;
    }

    private void getHealthy() {

        if (radio1.isChecked() && radio4.isChecked()) {
            isHealthy = true;
        } else {
            isHealthy = false;
        }

        if (radio2.isChecked() || radio3.isChecked()) {
            Toast.makeText(getContext(), "Sorry !You are not eligible as Doner", Toast.LENGTH_SHORT).show();
        }
        if (checkBox1.isChecked() || checkBox2.isChecked() || checkBox3.isChecked() || checkBox4.isChecked()) {
            isCheckboxgroup = false;
        } else {
            isCheckboxgroup = true;
        }

        if (isHealthy && isCheckboxgroup) {
            SetAccount();
        } else {
            Toast.makeText(getContext(), "Sorry !You are not eligible as Doner", Toast.LENGTH_SHORT).show();
        }
    }

    public void SetAccount() {

        apiService.signup(name, password, age, address, email, gender, blood_group, phone).enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {

                signupResponse = response.body();

                sharedPreManager.saveUser(signupResponse.getAccess_token(), signupResponse.getType_token());

                if (response.code() == 200) {

                    token = response.body().getAccess_token();

                    apiService.setDoner("Bearer " + token).enqueue(new Callback<SetDonorModel>() {
                        @Override
                        public void onResponse(Call<SetDonorModel> call, Response<SetDonorModel> response) {

                            Toast.makeText(getContext(), String.valueOf(response.body().getMessage()), Toast.LENGTH_SHORT).show();

                            showdialog();

                        }

                        @Override
                        public void onFailure(Call<SetDonorModel> call, Throwable t) {

                        }
                    });

                }


            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.tv_registration_ques)
    void onSubmitClick() {
        getHealthy();
    }

    @OnClick(R.id.iv_backQuestion)
    void backPointer() {
        getActivity().getSupportFragmentManager().popBackStack();
    }


    public void showdialog() {


        dialog.setContentView(R.layout.dialog_congrats);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.round_corners));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        dialog.setCancelable(false);

        TextView tv_continue = dialog.findViewById(R.id.tv_continue);

        tv_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog.setCancelable(false);
                Intent i = new Intent(getActivity(), HomeActivity.class);
                startActivity(i);
            }
        });


    }


}