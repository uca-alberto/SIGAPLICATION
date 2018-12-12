package com.example.hacker.sigaplication.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.hacker.sigaplication.Adapter.CommentsAdapter;
import com.example.hacker.sigaplication.Api.Api;
import com.example.hacker.sigaplication.MainActivity;
import com.example.hacker.sigaplication.MapsActivity;
import com.example.hacker.sigaplication.Model.CommentsModel;
import com.example.hacker.sigaplication.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsGet extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_get);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.Combo_zonas,android.R.layout.simple_spinner_item);
        initViews();
        configureRecyclerView();
        getMarket();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
              startActivity(new Intent(CommentsGet.this,MapsActivity.class));
              finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menucomment,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.Btncomentarios:
                //startActivity(new Intent(MapsActivity.this,CommentsGet.class));
                break;
            case R.id.BtnLocalitacion:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        recyclerView= findViewById(R.id.recycler);

    }
    /**
     * To configure the RecyclerView
     */
    private void configureRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    public void getMarket()
    {
        Call<List<CommentsModel>> call = Api.instance(). getComments();
        call.enqueue(new Callback<List<CommentsModel>>() {
            @Override
            public void onResponse(Call<List<CommentsModel>> call, Response<List<CommentsModel>> response) {
                CommentsAdapter commentsAdapter = new CommentsAdapter(response.body());
                recyclerView.setAdapter(commentsAdapter);
            }

            @Override
            public void onFailure(Call<List<CommentsModel>> call, Throwable t) {

            }
        });
    }


}
