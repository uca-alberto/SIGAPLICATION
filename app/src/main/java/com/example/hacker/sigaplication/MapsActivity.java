package com.example.hacker.sigaplication;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.hacker.sigaplication.Activitys.CommentsGet;
import com.example.hacker.sigaplication.Api.Api;
import com.example.hacker.sigaplication.Modals.CommentModal;
import com.example.hacker.sigaplication.Modals.CommentPlaces;
import com.example.hacker.sigaplication.Model.PlaceModel;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks
,GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private GoogleMap mMap;
    private static final int MY_PERMISSION_REQUEST_CODE =7192;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST=300193;
    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;
    private Location mlastLocation;
    ImageButton Modal;
    ImageButton Modal21;
    ImageButton Moda2;

    private static int UPDATE_INTERVAL = 5000;
    private static int FATEST_INTERVAL = 3000;
    private  static int DISPLACEMENT = 10;
    DatabaseReference ref;
    GeoFire geoFire;
    Marker marker;
    String names;
    VerticalSeekBar verticalSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Modal = findViewById(R.id.modal);
        Modal21=findViewById(R.id.moda2l);
        Moda2 = findViewById(R.id.moda22);
        Moda2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MapsActivity.this,CommentsGet.class));
            }
        });
        names = Build.BOARD;
        Modal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentModal commentModal = new CommentModal();
                commentModal.show(getSupportFragmentManager(),"Example");
            }
        });
        Modal21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MapsActivity.this,CommentsGet.class));
                CommentPlaces commentPlaces = new CommentPlaces();
                commentPlaces.show(getSupportFragmentManager(),"Example");
                //finish();
            }
        });

        ref= FirebaseDatabase.getInstance().getReference("MyLocation");
        geoFire = new GeoFire(ref);
        verticalSeekBar = findViewById(R.id.verticalseekbar);
        verticalSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mMap.animateCamera(CameraUpdateFactory.zoomTo(i),200,null);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        setUpLocation();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case  MY_PERMISSION_REQUEST_CODE:
            {

                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    if(chekPlayServices()){
                        buildGoogleApiCliente();
                        CreateLocationrequest();
                        displayLocation();
                    }
                }

            }
        }
    }

    private void setUpLocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
            },MY_PERMISSION_REQUEST_CODE);
        }else
        {
            if(chekPlayServices()){
                buildGoogleApiCliente();
                CreateLocationrequest();
                displayLocation();
            }
        }
    }

    private void displayLocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED ) {
        return;
        }
        mlastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if(mlastLocation !=null){
            final double latitud = mlastLocation.getLatitude();
            final double longitud = mlastLocation.getLongitude();
             geoFire.setLocation("You", new GeoLocation(latitud, longitud), new GeoFire.CompletionListener() {
                 @Override
                 public void onComplete(String key, DatabaseError error) {
                     if(marker != null){
                         marker.remove();
                     }
                     marker = mMap.addMarker(new MarkerOptions().position(new LatLng(latitud,longitud)).title("you"));
                     mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitud,longitud),12.0f));
                 }
             });
        }
    }

    private void CreateLocationrequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FATEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    private void buildGoogleApiCliente() {
        googleApiClient = new GoogleApiClient.Builder(this)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(LocationServices.API)
        .build();

        googleApiClient.connect();

    }

    private boolean chekPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if(resultCode != ConnectionResult.SUCCESS){
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)){
                GooglePlayServicesUtil.getErrorDialog(resultCode,this,PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }else {
                Toast.makeText(this,"This divice is not supported",Toast.LENGTH_SHORT).show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng dangerous_area = new LatLng(11.9184 ,-86.14467);
        mMap.addCircle(new CircleOptions().center(dangerous_area).radius(500)
        .strokeColor(Color.BLUE).fillColor(0x220000FF).strokeWidth(5.0f));

        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(dangerous_area.latitude,dangerous_area.longitude),5.0f);
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                String places=" Reparto Shek";
                String dangerous="Peligroso";
                Sandblast(places,dangerous);
                sendNotification("PELIGRO",String.format(" Entraste A Una Zona Peligrosa",key));
            }

            @Override
            public void onKeyExited(String key) {
                sendNotification("PELIGRO",String.format(" Saliste De Una Zona De Peligro",key));
            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
                //Log.d("MOVE",String.format("%s Moved in dangerous area [%f/%f]",key,location.latitude,location.longitude));
            }

            @Override
            public void onGeoQueryReady() {

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {
                Log.e("ERROR",""+error);
            }
        });
    }
    private void sendNotification(String s, String format) {
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(s)
                .setContentText(format);
        NotificationManager manager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this,MapsActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(contentIntent);
        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            notification = builder.build();
        }
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
         notification.defaults |= Notification.DEFAULT_SOUND;
         notification.defaults |= Notification.DEFAULT_VIBRATE;

         manager.notify(new Random().nextInt(),notification);
    }

    @Override
    public void onLocationChanged(Location location) {
        mlastLocation = location;
        displayLocation();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        displayLocation();
        starlocationUpdates();
    }

    private void starlocationUpdates() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED ) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,locationRequest,this);
    }

    @Override
    public void onConnectionSuspended(int i) {
    googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void Sandblast(String lugar,String peligro){
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = new Date();
        String filename = timeStampFormat.format(myDate);
        final PlaceModel placeModel = new PlaceModel();
        placeModel.setPlace_Visit(lugar);
        placeModel.setUser(names);
        placeModel.setDate(filename);
        placeModel.setDangerNivel(peligro);

        Call<PlaceModel> call = Api.instance().PostPlaces(placeModel);
        call.enqueue(new Callback<PlaceModel>() {
            @Override
            public void onResponse(Call<PlaceModel> call, Response<PlaceModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(MapsActivity.this,"Has entrado en una zona", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MapsActivity.this,response.raw().toString(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<PlaceModel> call, Throwable t) {
                Toast.makeText(MapsActivity.this,"Revisar Conexion De Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
