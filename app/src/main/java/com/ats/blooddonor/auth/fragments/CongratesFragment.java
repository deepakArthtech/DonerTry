package com.ats.blooddonor.auth.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ats.blooddonor.R;
import com.ats.blooddonor.auth.AuthActivity;
import com.ats.blooddonor.home.WelcomeFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CongratesFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_congrates, container, false);
        ButterKnife.bind(this,view);

        return view;
    }

    @OnClick(R.id.tv_continue)
    void onContinueClick()
    {
        ((AuthActivity)getActivity()).loadFragment(new WelcomeFragment(),"welcomeFragment");
    }

}