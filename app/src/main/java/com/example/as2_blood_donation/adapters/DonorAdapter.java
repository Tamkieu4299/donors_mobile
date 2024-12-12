package com.example.as2_blood_donation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.as2_blood_donation.R;
import com.example.as2_blood_donation.models.Donor;

import java.util.ArrayList;

public class DonorAdapter extends RecyclerView.Adapter<DonorAdapter.DonorViewHolder> {
    private Context context;
    private ArrayList<Donor> donors;

    public DonorAdapter(Context context, ArrayList<Donor> donors) {
        this.context = context;
        this.donors = donors;
    }

    @NonNull
    @Override
    public DonorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.donor_recycler_view_row, parent, false);
        return new DonorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonorViewHolder holder, int position) {
        Donor donor = donors.get(position);
        holder.nameTextView.setText(donor.getName());
        holder.bloodTypeTextView.setText(donor.getBloodType().toString());
        holder.emailTextView.setText(donor.getEmail());

    }

    @Override
    public int getItemCount() {
        return donors.size();
    }

    public static class DonorViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, bloodTypeTextView, emailTextView;

        public DonorViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.donorNameText);
            bloodTypeTextView = itemView.findViewById(R.id.donorBloodTypeText);
            emailTextView = itemView.findViewById(R.id.donorEmailText);
        }
    }
}
