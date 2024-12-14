package com.example.as2_blood_donation.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.as2_blood_donation.R;
import com.example.as2_blood_donation.activities.SiteDetail;
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DonorViewHolder holder, int position) {
        Donor donor = donors.get(position);

        holder.nameTextView.setText(donor.getFirstName() + " " + donor.getLastName());
        holder.emailTextView.setText(donor.getEmail());
        holder.bloodTypeTextView.setText(donor.getBloodType());

        // Check if donor is approved
        if (donor.isApproved()) {
            holder.approveButton.setVisibility(View.GONE);
            holder.approvedTickImageView.setVisibility(View.VISIBLE);
        } else {
            holder.approveButton.setVisibility(View.VISIBLE);
            holder.approvedTickImageView.setVisibility(View.GONE);

            // Handle approve button click
            holder.approveButton.setOnClickListener(view -> {
                if (context instanceof SiteDetail) {
                    ((SiteDetail) context).showApproveDonorDialog(Integer.parseInt(donor.getId()));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return donors.size();
    }

    public static class DonorViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, emailTextView, bloodTypeTextView;
        Button approveButton;
        ImageView approvedTickImageView; // Green tick for approved donors

        public DonorViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.donorNameText);
            emailTextView = itemView.findViewById(R.id.donorEmailText);
            bloodTypeTextView = itemView.findViewById(R.id.donorBloodTypeText);
            approveButton = itemView.findViewById(R.id.approveDonorButton);
            approvedTickImageView = itemView.findViewById(R.id.approvedTick); // Add this in your layout
        }
    }
}
