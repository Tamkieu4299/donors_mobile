package com.example.as2_blood_donation.api;

import com.example.as2_blood_donation.models.ApiResponseObject;
import com.example.as2_blood_donation.models.DonationSite;
import com.example.as2_blood_donation.models.LoginRequest;
import com.example.as2_blood_donation.models.RegisterRequest;
import com.example.as2_blood_donation.models.RouteResponse;
import com.example.as2_blood_donation.models.Site;
import com.example.as2_blood_donation.models.ApiResponse;
import com.example.as2_blood_donation.models.SiteCreate;
import com.example.as2_blood_donation.models.User;
import com.example.as2_blood_donation.models.UserSession;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("/api/v1/site/add")
    Call<Void> createSite(@Body SiteCreate site);

    @GET("/api/v1/site/search/")
    Call<ApiResponse<DonationSite>> getDonationSites();

    @GET("/api/v1/site/search/{id}")
    Call<ApiResponseObject<Site>> getSiteDetails(@Path("id") int siteId);

    @GET("/api/v1/donation/search_by_site/{id}")
    Call<ApiResponse<Site>> getDonationBySite(@Path("id") int siteId);

    @POST("/api/v1/auth/token")
    @Headers("Content-Type: application/json")
    Call<ApiResponseObject<UserSession>> login(@Body LoginRequest loginRequest);

    @POST("/api/v1/donation/register")
    Call<ApiResponseObject<Void>> registerVolunteer(@Body Map<String, Integer> requestBody);

    @PUT("/api/v1/donation/approve/{site_id}/{donor_id}")
    Call<ApiResponseObject<Void>> approveDonation(
            @Path("site_id") int siteId,
            @Path("donor_id") int donorId,
            @Query("volume_of_blood") int volumeOfBlood
    );

    @GET("/api/v1/map/nearest-site")
    Call<ApiResponseObject<DonationSite>> getNearestSite(
            @Query("user_lat") double userLat,
            @Query("user_lng") double userLng
    );

    @POST("/api/v1/map/get-route")
    Call<RouteResponse> getRoute(@Body Map<String, Double> requestBody);

    @POST("/api/v1/site/filter/")
    Call<ApiResponse<DonationSite>> filterDonationSites(
            @Query("skip") int skip,
            @Query("limit") int limit,
            @Body Map<String, Object> filter
    );

    @POST("/api/v1/auth/register")
    Call<ApiResponseObject<RegisterRequest>> registerUser(@Body RegisterRequest userRequest);

}
