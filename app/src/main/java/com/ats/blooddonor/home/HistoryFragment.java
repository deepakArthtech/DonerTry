package com.ats.blooddonor.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ats.blooddonor.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HistoryFragment extends Fragment {

    @BindView(R.id.iv_history)
    ImageView backPointer;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_history, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.iv_history)
    void backFragment(){
        getActivity().getSupportFragmentManager().popBackStack();
    }
}