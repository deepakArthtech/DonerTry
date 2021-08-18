package com.ats.blooddonor.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ats.blooddonor.Adapter.FindDonorAdapter;
import com.ats.blooddonor.Model.SharedPreManager;
import com.ats.blooddonor.R;
import com.ats.blooddonor.Utils;
import com.ats.blooddonor.network.APIService;
import com.ats.blooddonor.network.RetroInstance;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DonationRequestFragment extends Fragment {

    @BindView(R.id.donation_request)
    RecyclerView donation_request;
    APIService apiService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donation_request, container, false);

        apiService = RetroInstance.getRetroClient().create(APIService.class);

        ButterKnife.bind(this, view);
        //rv_donor.setLayoutManager(new LinearLayoutManager(getContext()));
        FindDonorAdapter findDonorAdapter = new FindDonorAdapter(getContext(), Utils.donorList);
        // rv_donor.setAdapter(findDonorAdapter);
        setRequest();

        return view;

    }

    @OnClick(R.id.iv_donation)
    void onBackClick(){

        getActivity().getSupportFragmentManager().popBackStack();
//        ((HomeActivity)getActivity()).loadFragmentFirst(new HomeFragment(), "HomeFragment");
    }

    public void setRequest() {
        apiService.setRequest("Bearer " + new SharedPreManager(getContext()).getAcces_token()).enqueue(new Callback<List<Gson>>() {
            @Override
            public void onResponse(Call<List<Gson>> call, Response<List<Gson>> response) {

            }

            @Override
            public void onFailure(Call<List<Gson>> call, Throwable t) {

            }
        });
    }
}