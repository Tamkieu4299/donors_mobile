package com.example.as2_blood_donation.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.as2_blood_donation.R;
import com.example.as2_blood_donation.activities.SiteDetail;
import com.example.as2_blood_donation.models.DonationSite;

import java.util.ArrayList;

public class DonationSiteAdapter extends RecyclerView.Adapter<DonationSiteAdapter.DonationSiteViewHolder> {
    Context context;
    ArrayList<DonationSite> donationSites;

    public DonationSiteAdapter(Context context, ArrayList<DonationSite> donationSites) {
        this.context = context;
        this.donationSites = donationSites;
    }

    @NonNull
    @Override
    public DonationSiteAdapter.DonationSiteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.site_recyler_view_row, parent, false);
        return new DonationSiteAdapter.DonationSiteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationSiteAdapter.DonationSiteViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.nameText.setText(donationSites.get(position).getName());
        holder.latText.setText(String.valueOf(donationSites.get(position).getLatitude()));
        holder.longText.setText(String.valueOf(donationSites.get(position).getLongitude()));
        holder.addressText.setText(donationSites.get(position).getAddress());

        holder.moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SiteDetail.class);
                intent.putExtra("siteID", donationSites.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return donationSites.size();
    }

    public static class DonationSiteViewHolder extends RecyclerView.ViewHolder {
        Button moreButton;
        TextView nameText, latText, longText, addressText;
        public DonationSiteViewHolder(@NonNull View itemView) {
            super(itemView);

            moreButton = itemView.findViewById(R.id.moreButton);
            nameText = itemView.findViewById(R.id.nameText);
            latText = itemView.findViewById(R.id.latText);
            longText = itemView.findViewById(R.id.longText);
            addressText = itemView.findViewById(R.id.addressText);
        }
    }
}
