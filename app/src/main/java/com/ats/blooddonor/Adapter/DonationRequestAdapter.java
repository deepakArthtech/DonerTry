package com.ats.blooddonor.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ats.blooddonor.Model.FindDonorModel;
import com.ats.blooddonor.R;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DonationRequestAdapter extends RecyclerView.Adapter<DonationRequestAdapter.ViewHolder> {

    Context context;
    List<Gson> dataList;

    public DonationRequestAdapter(Context context, List<Gson> dataList) {

        this.context = context;
        this.dataList = dataList;

    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.donationrequest_layout, parent, false);
        DonationRequestAdapter.ViewHolder viewHolder = new DonationRequestAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DonationRequestAdapter.ViewHolder holder, int position) {

        holder.t_name.setText("Raman");
        holder.t_area.setText("Mohali , Unknow Street, Unnammed road Mohali");
        holder.tv__bloodgroup.setText("A+");

        holder.img_profile.setImageResource(R.drawable.female);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.img_profile)
        ImageView img_profile;

        @BindView(R.id.t_name)
        TextView t_name;

        @BindView(R.id.t_area)
        TextView t_area;

        @BindView(R.id.tv__bloodgroup)
        TextView tv__bloodgroup;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
