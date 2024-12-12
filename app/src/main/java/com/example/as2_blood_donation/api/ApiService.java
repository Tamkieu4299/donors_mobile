package com.example.as2_blood_donation.api;

import com.example.as2_blood_donation.models.DonationSite;
import com.example.as2_blood_donation.models.Site;
import com.example.as2_blood_donation.models.ApiResponse;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;

public interface ApiService {
    @POST("/api/v1/site/add")
    Call<Void> createSite(@Body Site site);

    @GET("/api/v1/site/search/")
    Call<ApiResponse<DonationSite>> getDonationSites();

}
