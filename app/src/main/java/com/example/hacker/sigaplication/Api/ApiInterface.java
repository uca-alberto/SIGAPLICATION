package com.example.hacker.sigaplication.Api;

import com.example.hacker.sigaplication.Model.CommentsModel;
import com.example.hacker.sigaplication.Model.PlaceModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @GET("Comments")
    Call<List<CommentsModel>> getComments();

    @POST("Comments")
    Call<CommentsModel> PostComments(@Body CommentsModel commentsModel);

    @POST("PlaceVisits")
    Call<PlaceModel> PostPlaces(@Body PlaceModel placeModel);
}
