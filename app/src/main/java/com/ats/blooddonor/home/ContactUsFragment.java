package com.ats.blooddonor.home;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.blooddonor.Model.AuthResponse;
import com.ats.blooddonor.Model.ContactModel;
import com.ats.blooddonor.Model.SharedPreManager;
import com.ats.blooddonor.R;
import com.ats.blooddonor.network.APIService;
import com.ats.blooddonor.network.RetroInstance;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ContactUsFragment extends Fragment {

    APIService apiService;
    @BindView(R.id.name)
    EditText full_name;
    @BindView(R.id.mail)
    EditText email;
    @BindView(R.id.contact)
    EditText phone;
    @BindView(R.id.sms)
    EditText message;
    @BindView(R.id.subbutton)
    TextView subbutton;
    String token;
    SharedPreManager sharedPreManager;
    AuthResponse authResponse;
    Dialog dialog;
    @BindView(R.id.iV_back)
    ImageView backPointer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);

        ButterKnife.bind(this,view);
        dialog = new Dialog(getContext());

        apiService = RetroInstance.getRetroClient().create(APIService.class);
        // Inflate the layout for this fragment

//        sharedPreManager = new SharedPreManager(getActivity());
        sharedPreManager = new SharedPreManager(getActivity());
        token=sharedPreManager.getAcces_token();
        Log.i("AccessTokenIs",token);
        ((HomeActivity)getActivity()).hideToolbar();

//       contactAdmin();

        return view;
    }

    @OnClick(R.id.subbutton)
    void onSubmit(){
        contactAdmin();
    }


    public void contactAdmin(){

        ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading");
        progressDialog.show();
        Log.i("AccessTokenIs",token);

        apiService.contactUs(full_name.getText().toString(), email.getText().toString(), phone.getText().toString(), message.getText().toString(),"Bearer "+token).enqueue(new Callback<ContactModel>() {
            @Override
            public void onResponse(Call<ContactModel> call, Response<ContactModel> response) {

                showdialog();
            }

            @Override
            public void onFailure(Call<ContactModel> call, Throwable t) {

                progressDialog.dismiss();

            }
        });

    }

    public void showdialog() {

        dialog.setContentView(R.layout.dialog_contact);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.round_corners));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        dialog.setCancelable(false);
        TextView tv_continue = dialog.findViewById(R.id.tv_continue);
        tv_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dialog.dismiss();
//                dialog.setCancelable(false);
                Intent i = new Intent(getActivity(), HomeActivity.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });

    }

    @OnClick(R.id.iV_back)
    void backFragment(){
        getActivity().getSupportFragmentManager().popBackStack();
    }

}