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

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindDonorAdapter extends RecyclerView.Adapter<FindDonorAdapter.ViewHolder> {

    Context context;
    List<FindDonorModel> dataList;

    public FindDonorAdapter(Context context, List<FindDonorModel> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.donor_list_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FindDonorAdapter.ViewHolder holder, int position) {

        holder.t_name.setText(dataList.get(position).getFullName());
        holder.t_area.setText(dataList.get(position).getAddress());
        holder.tv__bloodgroup.setText(dataList.get(position).getBloodGroup());

        if (dataList.get(position).getGender().equals("female")) {
            holder.img_profile.setImageResource(R.drawable.female);
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

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
            ButterKnife.bind(this, itemView);
        }
    }

}
