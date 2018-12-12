package com.example.hacker.sigaplication.Modals;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hacker.sigaplication.Adapter.CommentsAdapter;
import com.example.hacker.sigaplication.Adapter.CommentsPlaceAdapter;
import com.example.hacker.sigaplication.Api.Api;
import com.example.hacker.sigaplication.Model.CommentsModel;
import com.example.hacker.sigaplication.Model.PlaceModel;
import com.example.hacker.sigaplication.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentPlaces extends BottomSheetDialogFragment {
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.get_comment_place,container,false);
        recyclerView= view.findViewById(R.id.recyclerplace);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getMarket();
        return view;
    }
    public void getMarket()
    {
        Call<List<PlaceModel>> call = Api.instance().getCommentsplaces();
        call.enqueue(new Callback<List<PlaceModel>>() {
            @Override
            public void onResponse(Call<List<PlaceModel>> call, Response<List<PlaceModel>> response) {
                CommentsPlaceAdapter commentsPlaceAdapter = new CommentsPlaceAdapter(response.body());
                recyclerView.setAdapter(commentsPlaceAdapter);
            }

            @Override
            public void onFailure(Call<List<PlaceModel>> call, Throwable t) {

            }
        });
    }


}
