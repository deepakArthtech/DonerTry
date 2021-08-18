package com.ats.blooddonor.home;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.blooddonor.Model.SetDonorModel;
import com.ats.blooddonor.Model.SharedPreManager;
import com.ats.blooddonor.Model.SignupResponse;
import com.ats.blooddonor.R;
import com.ats.blooddonor.auth.AuthActivity;
import com.ats.blooddonor.auth.fragments.LoginFragment;
import com.ats.blooddonor.auth.fragments.SelectLoginSignup;
import com.ats.blooddonor.auth.fragments.SignupFragment;
import com.ats.blooddonor.network.APIService;
import com.ats.blooddonor.network.RetroInstance;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WelcomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match

    @BindView(R.id.iv_doner)
    ImageView iv_donor;
    @BindView(R.id.iv_receiver)
    ImageView iv_receiver;
    @BindView(R.id.iv_backWelcome)
    ImageView backPointer;
    SignupResponse signupResponse;
    Bundle bundle;
    String name, age, gender, password, email, blood_group, address,phone;
    APIService apiService;
    SharedPreManager sharedPreManager;
    String getToken;
    Dialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        ButterKnife.bind(this,view);
        apiService = RetroInstance.getRetroClient().create(APIService.class);
        Bundle bundle = this.getArguments();
        sharedPreManager = new SharedPreManager(getContext());

        signupResponse= new SignupResponse();
        dialog = new Dialog(getContext());

        if (bundle!=null)
        {
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

    @OnClick(R.id.iv_doner)
    void onDonorClick()
    {
        if (Integer.parseInt(age)>17) {
            RegistrationQues registrationQues = new RegistrationQues();
            bundle = new Bundle();
            bundle.putString("name", name);
            bundle.putString("gender", gender);
            bundle.putString("age", age);
            bundle.putString("blood_group", blood_group);
            bundle.putString("address", address);
            bundle.putString("password", password);
            bundle.putString("email", email);
            bundle.putString("phone", phone);
            registrationQues.setArguments(bundle);
            ((AuthActivity) getActivity()).loadFragment(registrationQues, "questionFragment");
        }
        else {
            Toast.makeText(getContext(),"You are not eligible as donor",Toast.LENGTH_SHORT).show();
            ((AuthActivity)getActivity()).loadFragmentFirst(new SelectLoginSignup(),"selectLoginSignup");
        }
    }

    @OnClick(R.id.iv_receiver)
    void setReceiver(){
      SetAccount();
    }

    @OnClick(R.id.iv_backWelcome)
    void backPointer()
    {
       getActivity().getSupportFragmentManager().popBackStack();
    }

//    public void getReceiver() {
//
//        String rec_token = sharedPreManager.getAcces_token();
//        apiService.setReceiver(rec_token).enqueue(new Callback<SetDonorModel>() {
//            @Override
//            public void onResponse(Call<SetDonorModel> call, Response<SetDonorModel> response) {
//                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<SetDonorModel> call, Throwable t) {
//
//            }
//        });
//
//    }


    public void SetAccount() {

        apiService.signup(name, password, age, address, email, gender, blood_group, phone).enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {

                signupResponse = response.body();
                getToken = signupResponse.getAccess_token();
                sharedPreManager.saveUser(signupResponse.getAccess_token(), signupResponse.getType_token());

                if(response.code()==200){
                    getReceiver();
                }


            }
            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {

            }
        });
    }
    public void getReceiver(){

        apiService.setReceiver("Bearer "+getToken).enqueue(new Callback<SetDonorModel>() {
            @Override
            public void onResponse(Call<SetDonorModel> call, Response<SetDonorModel> response) {

                if(response.code()==200){
                    showdialog();
                }

            }
            @Override
            public void onFailure(Call<SetDonorModel> call, Throwable t) {

            }
        });
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
                getActivity().finish();
            }
        });


    }
}