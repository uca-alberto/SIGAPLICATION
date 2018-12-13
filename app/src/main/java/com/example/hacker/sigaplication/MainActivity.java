package com.example.hacker.sigaplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hacker.sigaplication.Activitys.CommentsGet;

public class MainActivity extends AppCompatActivity {
    Button Maps;
    Button Comentarios;
    Button places;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Maps = findViewById(R.id.ButonMaps);
        Maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(MainActivity.this,MapsActivity.class));
            }
        });

    }
}
