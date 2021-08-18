package com.ats.blooddonor.home;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ats.blooddonor.Adapter.SliderAdapter;
import com.ats.blooddonor.Model.SliderData;
import com.ats.blooddonor.R;
import com.ats.blooddonor.auth.AuthActivity;
import com.ats.blooddonor.auth.fragments.Profilefragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.smarteist.autoimageslider.SliderView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends Fragment{

    @BindView(R.id.cv_donor)
    CardView cv_donor;
    @BindView(R.id.cv_blood)
    CardView cv_blood;
//    @BindView(R.id.cv_history)
//    CardView cv_history;
//    @BindView(R.id.cv_campaign)
//    CardView cv_campaign;
    @BindView(R.id.slider)
    SliderView sliderView;

//    String url1 = "https://www.geeksforgeeks.org/wp-content/uploads/gfg_200X200-1.png";
//    String url2 = "https://qphs.fs.quoracdn.net/main-qimg-8e203d34a6a56345f86f1a92570557ba.webp";
//    String url3 = "https://bizzbucket.co/wp-content/uploads/2020/08/Life-in-The-Metro-Blog-Title-22.png";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
//        bottomNavigationView.findViewById(R.id.bottomNavigationView);
//        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        ButterKnife.bind(this, view);
        ((HomeActivity) getActivity()).showToolbar();
        slider();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity)getActivity()).hideShowBottomBar(true);
    }

    @OnClick(R.id.cv_donor)
    void onFindDonorClick()
    {
        ((HomeActivity)getActivity()).hideToolbar();
        ((HomeActivity)getActivity()).loadFragmentFirst(new FindDonor(), "FindDonor");

    }
    @OnClick(R.id.cv_blood)
    void onRequestBloodClick()
    {
        ((HomeActivity)getActivity()).hideToolbar();
        ((HomeActivity)getActivity()).loadFragment(new RequestBloodFragment(), "RequestBlood");
    }
//    @OnClick(R.id.cv_history)
//    void onHistorylick()
//    {
//        ((HomeActivity)getActivity()).hideToolbar();
//        ((HomeActivity)getActivity()).loadFragmentFirst(new HistoryFragment(), "History");
//    }
//    @OnClick(R.id.cv_campaign)
//    void onCampaignClick()
//    {}
    public void slider(){
        // we are creating array list for storing our image urls.
        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();


        // adding the urls inside array list
        sliderDataArrayList.add(new SliderData(R.drawable.slider1));
        sliderDataArrayList.add(new SliderData(R.drawable.slider2));
        sliderDataArrayList.add(new SliderData(R.drawable.slider3));

        // passing this array list inside our adapter class.
        SliderAdapter adapter = new SliderAdapter(getContext(), sliderDataArrayList);
        // below method is used to set auto cycle direction in left to
        // right direction you can change according to requirement.
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        // below method is used to
        // setadapter to sliderview.
        sliderView.setSliderAdapter(adapter);

        sliderView.setScrollTimeInSec(3);
        // to set it scrollable automatically
        // we use below method.
        sliderView.setAutoCycle(true);
        // to start autocycle below method is used.
        sliderView.startAutoCycle();
    }
}