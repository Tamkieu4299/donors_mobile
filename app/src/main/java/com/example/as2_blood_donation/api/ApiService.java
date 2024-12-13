package com.example.as2_blood_donation.api;

import com.example.as2_blood_donation.models.ApiResponseObject;
import com.example.as2_blood_donation.models.DonationSite;
import com.example.as2_blood_donation.models.Site;
import com.example.as2_blood_donation.models.ApiResponse;
import com.example.as2_blood_donation.models.SiteCreate;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @POST("/api/v1/site/add")
    Call<Void> createSite(@Body SiteCreate site);

    @GET("/api/v1/site/search/")
    Call<ApiResponse<DonationSite>> getDonationSites();

    @GET("/api/v1/site/search/{id}")
    Call<ApiResponseObject<Site>> getSiteDetails(@Path("id") int siteId);

    @GET("/api/v1/donation/search_by_site/{id}")
    Call<ApiResponse<Site>> getDonationBySite(@Path("id") int siteId);

}
