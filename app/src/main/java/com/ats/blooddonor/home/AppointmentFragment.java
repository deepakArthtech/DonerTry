package com.ats.blooddonor.home;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ats.blooddonor.R;
//import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
//import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;
//
//import org.joda.time.DateTime;

import butterknife.BindView;

public class AppointmentFragment extends Fragment {
//
//    @BindView(R.id.datePicker)
//    HorizontalPicker datePicker;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_appointment, container, false);
//
//        datePicker
//                .setDays(20)
//                .setOffset(10)
//                .setDateSelectedColor(Color.DKGRAY)
//                .setDateSelectedTextColor(Color.WHITE)
//                .setMonthAndYearTextColor(Color.DKGRAY)
//                .setTodayButtonTextColor(Color.WHITE)
//                .setTodayDateTextColor(Color.WHITE)
//                .setTodayDateBackgroundColor(Color.GRAY)
//                .setUnselectedDayTextColor(Color.DKGRAY)
//                .setDayOfWeekTextColor(Color.DKGRAY)
//                .setUnselectedDayTextColor(Color.WHITE)
//                .showTodayButton(false)
//                .init();
//
//        // or on the View directly after init was completed
//        datePicker.setBackgroundColor(Color.LTGRAY);
//        datePicker.setDate(new DateTime().plusDays(4));

        return  view;
    }
}