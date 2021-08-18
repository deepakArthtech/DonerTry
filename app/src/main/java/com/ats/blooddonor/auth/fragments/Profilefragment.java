package com.ats.blooddonor.auth.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ats.blooddonor.Model.AuthResponse;
import com.ats.blooddonor.Model.SharedPreManager;
import com.ats.blooddonor.Model.UserResponse;
import com.ats.blooddonor.R;
import com.ats.blooddonor.network.APIService;
import com.ats.blooddonor.network.RetroInstance;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profilefragment extends Fragment {

    APIService apiService;
    String token;
    @BindView(R.id.tv_name)
    TextView name;
    @BindView(R.id.tv_location)
    TextView location;
//    @BindView(R.id.img_profile)
//    ImageView profileImg;
    @BindView(R.id.blood_group_profile)
    TextView blood_group;
    @BindView(R.id.updated_at)
    TextView updated_at;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profilefragment, container, false);

        ButterKnife.bind(this,view);

        apiService = RetroInstance.getRetroClient().create(APIService.class);
       token=new SharedPreManager(getContext()).getAcces_token();
        Log.i("AccessTokenIs",token);
        getUserInfo();
        return view;
    }

    public void getUserInfo(){

        apiService.user("Bearer "+token).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                Log.i("response ", String.valueOf(response.body()));
                String full_name = response.body().getFullName();
                String address = response.body().getAddress();
//                String url = response.body().getAvatar();
                String bloodtype = response.body().getBloodGroup();
                String profile_time = response.body().getUpdatedAt();
                name.setText(full_name);
                location.setText(address);
                blood_group.setText(bloodtype);
                updated_at.setText(profile_time);
//                Picasso.get().load(url).into(profileImg);
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });


    }
}