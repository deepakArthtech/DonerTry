package com.ats.blooddonor.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ats.blooddonor.Adapter.FindDonorAdapter;
import com.ats.blooddonor.Model.FindDonorModel;
import com.ats.blooddonor.R;
import com.ats.blooddonor.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DonorListFragment extends Fragment {

    @BindView(R.id.rv_donor)
    RecyclerView rv_donor;
    @BindView(R.id.iv_listBack)
    ImageView backPointer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donor_list, container, false);
        ButterKnife.bind(this,view);


        rv_donor.setLayoutManager(new LinearLayoutManager(getContext()));

        FindDonorAdapter findDonorAdapter = new FindDonorAdapter(getContext(), Utils.donorList);

        rv_donor.setAdapter(findDonorAdapter);


        return view;

    }

    @OnClick(R.id.iv_listBack)
    void backPointer(){
        getActivity().getSupportFragmentManager().popBackStack();
    }
}