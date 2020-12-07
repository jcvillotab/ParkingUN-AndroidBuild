package com.example.parkingun;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.parkingun.interfaces.productAPI;
import com.example.parkingun.models.Parking;
import com.example.parkingun.models.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback , GoogleMap.OnMarkerClickListener{

    private GoogleMap mMap;
    private User user;
    private Marker parking1;
    private Marker parking2;
    private Marker parking3;
    private Marker parking4;
    private Marker parking5;
    private Marker parking6;
    private Marker parking7;
    private Marker parking8;
    private Marker parking9;
    private Marker parking10;

    private Marker unalPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent i = getIntent();
        user = (User) i.getSerializableExtra("Credentials");
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Listeners
        googleMap.setOnMarkerClickListener(this);

        //Markers Parking
        LatLng park1 = new LatLng(4.635766195897006, -74.08453887647495);
        parking1 = googleMap.addMarker(new MarkerOptions().position(park1).title("Parqueadero Derecho"));

        LatLng park2 = new LatLng(4.632982481909863, -74.08366955910104);
        parking2 = googleMap.addMarker(new MarkerOptions().position(park2).title("Parqueadero BiciRun Calle 26"));

        LatLng park3 = new LatLng(4.63829174981494, -74.08485208507527);
        parking3 = googleMap.addMarker(new MarkerOptions().position(park3).title("Parqueadero Edificio Ciencia y Tecnología"));

        LatLng park4 = new LatLng(4.63839363932783, -74.0835192493212);
        parking4 = googleMap.addMarker(new MarkerOptions().position(park4).title("Parqueadero Aulas de Ingeniería"));

        LatLng park5 = new LatLng(4.635789171239537, -74.08684303367633);
        parking5 = googleMap.addMarker(new MarkerOptions().position(park5).title("Parqueadero Agronomía"));

        LatLng park6 = new LatLng(4.640295147890939, -74.08212657496358);
        parking6 = googleMap.addMarker(new MarkerOptions().position(park6).title("Parqueadero Biología"));

        LatLng park7 = new LatLng(4.63711859652085, -74.08266110495396);
        parking7 = googleMap.addMarker(new MarkerOptions().position(park7).title("Parqueadero El Viejo"));

        LatLng park8 = new LatLng(4.634599074739043, -74.08562879289693);
        parking8 = googleMap.addMarker(new MarkerOptions().position(park8).title("Parqueadero Odontología"));

        LatLng park9 = new LatLng(4.636980299486396, -74.08054093689522);
        parking9 = googleMap.addMarker(new MarkerOptions().position(park9).title("Parqueadero Ciencias Económicas"));

        LatLng park10 = new LatLng(4.635133863292353, -74.08044831036703);
        parking10 = googleMap.addMarker(new MarkerOptions().position(park10).title("Parqueadero Bicirun Calle 45"));

        LatLng unalP = new LatLng(4.635433, -74.08281317091877);
        unalPosition = googleMap.addMarker(new MarkerOptions().position(unalP).title("Universidad Nacional de Colombia"));

        //Camera
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(unalP, 16.0f));
    }



    @Override
    public boolean onMarkerClick(Marker marker) {
        int id;
        if(marker.equals(parking1)){
            id = 1;
        }else if(marker.equals(parking2)){
            id = 2;
        }else if(marker.equals(parking3)){
            id = 3;
        }else if(marker.equals(parking4)){
            id = 4;
        }else if(marker.equals(parking5)){
            id = 5;
        }else if(marker.equals(parking6)){
            id = 6;
        }else if(marker.equals(parking7)){
            id = 7;
        }else if(marker.equals(parking8)){
            id = 8;
        }else if(marker.equals(parking9)){
            id = 9;
        }else if(marker.equals(parking10)){
            id = 10;
        }else{
            id = 0;
        }

        if(id != 0 ){
            returnParkingDetails(id);
        }

        return false;
    }

    private void returnParkingDetails(int id){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://52.171.139.242:44372/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        productAPI productAPI = retrofit.create(productAPI.class);
        Call<Parking> call = productAPI.returnParking(id);


        call.enqueue(new Callback<Parking>() {
            @Override
            public void onResponse(Call<Parking> call, Response<Parking> response) {
                try {
                    if(response.isSuccessful()){
                        Parking parkObject = response.body();
                        Intent i = new Intent(MapActivity.this, ParkingActivity.class);
                        i.putExtra("ParkingDetails", parkObject);
                        i.putExtra("userCredentials", user);
                        startActivity(i);
                    }
                }catch (Exception ex){
                    Toast.makeText(MapActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Parking> call, Throwable t) {
                Toast.makeText(MapActivity.this, "Error de conexion", Toast.LENGTH_SHORT);
            }
        });


    }
}