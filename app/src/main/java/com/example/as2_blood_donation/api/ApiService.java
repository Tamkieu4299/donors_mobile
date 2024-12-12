package com.example.as2_blood_donation.api;

import com.example.as2_blood_donation.models.Site;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/api/v1/site/add")
    Call<Void> createSite(@Body Site site);
}
