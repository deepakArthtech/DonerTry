package com.ats.blooddonor.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.blooddonor.Model.RequestBlood;
import com.ats.blooddonor.Model.RequestModel;
import com.ats.blooddonor.Model.SharedPreManager;
import com.ats.blooddonor.R;
import com.ats.blooddonor.network.APIService;
import com.ats.blooddonor.network.RetroInstance;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestBloodFragment extends Fragment {

    @BindView(R.id.imageView2)
    ImageView backPointer;
    @BindView(R.id.gender)
    Spinner spi_gender;
    @BindView(R.id.group_radioFirst)
    RadioGroup rg1;
    @BindView(R.id.group_radioSecond)
    RadioGroup rg2;
    @BindView(R.id.plus)
    TextView plus;
    @BindView(R.id.minus)
    TextView minus;
    @BindView(R.id.spi_group_request)
    Spinner spi_blood;
    @BindView(R.id.txt_unit)
    TextView txt_unit;
    @BindView(R.id.reason)
    EditText reason;
    @BindView(R.id.hospital)
    EditText hospital;
    @BindView(R.id.info)
    EditText et_info;
    @BindView(R.id.publish)
    TextView tv_publish;
    @BindView(R.id.request_progress)
    ProgressBar progressBar;


    public static int counter = 0;
    private boolean isChecking = true;
    private int mCheckedId = R.id.blood;
    String val;
    String[] blood_group = {" A+ ", " O+ ", " A- ", " O- ", " B- ", " B+ ", " AB- ", " AB+" };
    String[] genderlist = {"Male", "Female"};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_request_blood, container, false);
        ButterKnife.bind(this,view);


        ArrayAdapter aa = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, blood_group);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spi_blood.setAdapter(aa);



        ArrayAdapter genderColumn = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item,genderlist);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spi_gender.setAdapter(genderColumn);

        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    rg2.clearCheck();
                    mCheckedId = checkedId;
                }
                isChecking = true;
            }
        });

        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    rg1.clearCheck();
                    mCheckedId = checkedId;
                }
                isChecking = true;
            }
        });

        return view;
    }

    @OnClick(R.id.imageView2)
    void backFragment(){
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @OnClick(R.id.plus)
    void getAdd(){

        if (counter<5) {
            counter++;
            String val1 = String.valueOf(counter);
            txt_unit.setText(val1);
        }

    }

    @OnClick(R.id.minus)
    void getMinus(){

        if (counter>0) {
            counter--;
            String val2 = String.valueOf(counter);
            txt_unit.setText(val2);
        }
    }

    @OnClick(R.id.publish)
    void onPublicClick()
    {
        requestBlood();
    }

    private void requestBlood()
    {
        progressBar.setVisibility(View.VISIBLE);
        APIService  apiService = RetroInstance.getRetroClient().create(APIService.class);
        Call<RequestBlood> call = apiService.requestBlood(txt_unit.getText().toString(),spi_blood.getSelectedItem().toString(),reason.getText().toString(),
                hospital.getText().toString(),"MySelf",et_info.getText().toString(),"Bearer "+new SharedPreManager(getContext()).getAcces_token());

        call.enqueue(new Callback<RequestBlood>() {
            @Override
            public void onResponse(Call<RequestBlood> call, Response<RequestBlood> response) {

                progressBar.setVisibility(View.GONE);

//                Log.e("ResponseBloodRequest",new Gson().toJson(response.errorBody()));
                if (response.isSuccessful())
                {
                    Toast.makeText(getContext(),"Request Published",Toast.LENGTH_SHORT).show();
                }else {

                    Toast.makeText(getContext(),"No user found near you.",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RequestBlood> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });



    }

}