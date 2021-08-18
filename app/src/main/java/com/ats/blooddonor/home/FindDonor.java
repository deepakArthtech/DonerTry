package com.ats.blooddonor.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.blooddonor.Adapter.FindDonorAdapter;
import com.ats.blooddonor.Model.FindDonorModel;
import com.ats.blooddonor.Model.SharedPreManager;
import com.ats.blooddonor.R;
import com.ats.blooddonor.Utils;
import com.ats.blooddonor.network.APIService;
import com.ats.blooddonor.network.RetroInstance;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindDonor extends Fragment {

    @BindView(R.id.spi_blood_find)
    Spinner sp_bloodType;

    @BindView(R.id.imageView6)
    ImageView backPointer;

    @BindView(R.id.donerSubmit)
    TextView tv_submit;
    @BindView(R.id.spi_postal_address)
    EditText donerAddress;
    APIService apiService;


    String[] blood_group = {"A+", "O+", "A-", "O-", "B-", "B+", "AB-", "AB+" };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find_doner, container, false);

        ButterKnife.bind(this, view);

         apiService = RetroInstance.getRetroClient().create(APIService.class);

        ArrayAdapter aa = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, blood_group);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_bloodType.setAdapter(aa);

        return  view;
    }
    @OnClick(R.id.imageView6)
    void backFragment(){

        getActivity().getSupportFragmentManager().popBackStack();
    }

    @OnClick(R.id.donerSubmit)
    void getDonerList(){
         getDoners();
    }
    List<FindDonorModel> data;

    public void getDoners(){

        apiService.findDonor(sp_bloodType.getSelectedItem().toString(), donerAddress.getText().toString(), "Bearer "+new SharedPreManager(getContext()).getAcces_token()).
            enqueue(new Callback<List<FindDonorModel>>() {
            @Override
            public void onResponse(Call<List<FindDonorModel>> call, Response<List<FindDonorModel>> response) {



                if (response.isSuccessful())
                {
                    data = response.body();
                    Utils.donorList.clear();
                    Utils.donorList.addAll(data);

                    if (data.size()>0)
                    {
                        Fragment fragment = new DonorListFragment();

                        ((HomeActivity)getActivity()).loadFragment(fragment,"donorListFragment");
                    }
                }

                Log.e("ResponseFind",new Gson().toJson(response.body()));

            }

            @Override
            public void onFailure(Call<List<FindDonorModel>> call, Throwable t) {

                Log.e("ResponseFail",t.getMessage());

            }
        });

    }
}