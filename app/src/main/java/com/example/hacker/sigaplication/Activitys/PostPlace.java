package com.example.hacker.sigaplication.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hacker.sigaplication.Api.Api;
import com.example.hacker.sigaplication.MainActivity;
import com.example.hacker.sigaplication.Model.PlaceModel;
import com.example.hacker.sigaplication.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostPlace extends AppCompatActivity {

    EditText lugar;
    EditText peligro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_place);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lugar = findViewById(R.id.edtplaces);
        peligro = findViewById(R.id.edtpeligro);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Sandblast();
                startActivity(new Intent(PostPlace.this,MainActivity.class));
            }
        });
    }

    public void Sandblast(){
        final PlaceModel placeModel = new PlaceModel();
        placeModel.setPlace_Visit(lugar.getText().toString());
        placeModel.setUser("Alberto".toString());
        placeModel.setDate("201210210".toString());
        placeModel.setDangerNivel(peligro.getText().toString());

        Call<PlaceModel> call = Api.instance().PostPlaces(placeModel);
        call.enqueue(new Callback<PlaceModel>() {
            @Override
            public void onResponse(Call<PlaceModel> call, Response<PlaceModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(PostPlace.this,"Has entrado en una zona", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(PostPlace.this,response.raw().toString(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<PlaceModel> call, Throwable t) {
                Toast.makeText(PostPlace.this,"Revisar Conexion De Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
