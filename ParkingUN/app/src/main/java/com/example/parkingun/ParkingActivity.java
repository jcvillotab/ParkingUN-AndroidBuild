package com.example.parkingun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkingun.interfaces.productAPI;
import com.example.parkingun.models.Parking;
import com.example.parkingun.models.ParkingLog;
import com.example.parkingun.models.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ParkingActivity extends AppCompatActivity {

    TextView tvNearPlaces, tvCapacity, tvTitle, tvActual;
    ProgressBar occupation;
    Button reserveButton, retireButton;
    User userCredentials;
    Parking parkingInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvNearPlaces = (TextView) findViewById(R.id.tvNearPlaces);
        tvCapacity = (TextView) findViewById(R.id.tvCapacity);
        tvActual = (TextView) findViewById(R.id.tvActual);
        occupation = (ProgressBar) findViewById(R.id.occupationBar);
        reserveButton = (Button) findViewById(R.id.reserveButton);
        retireButton = (Button) findViewById(R.id.retireButton);
        Intent i = getIntent();
        userCredentials = (User) i.getSerializableExtra("userCredentials");
        parkingInfo = (Parking) i.getSerializableExtra("ParkingDetails");

        tvNearPlaces.setText(parkingInfo.getNearbyPlaces());
        tvCapacity.setText(Integer.toString(parkingInfo.getCapacity()));
        tvTitle.setText(parkingInfo.getNameP());
        occupation.setProgress(((parkingInfo.actual*100)/parkingInfo.capacity));
        tvActual.setText(Integer.toString(parkingInfo.actual));

        checkIfParked(userCredentials.getId(), parkingInfo.getId());


        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkIfExist(userCredentials.getId(), parkingInfo.getId());
            }
        });

        retireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retire(userCredentials.getId(), parkingInfo.getId());
            }
        });
    }

    private void checkIfExist(int id, int idPark){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://52.171.139.242:44372/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        productAPI productAPI = retrofit.create(productAPI.class);
        Call<String> call = productAPI.checkIfExist(id, idPark);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try{
                    if(response.isSuccessful()){
                        String result = response.body();
                        if(result.contains("Exist")){
                            Toast.makeText(ParkingActivity.this, "Usted ya tiene una bicicleta en este parqueadero", Toast.LENGTH_SHORT).show();
                        }else{
                            registerParking();
                        }
                    }
                }catch (Exception ex){
                    Toast.makeText(ParkingActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(ParkingActivity.this, "Error de conexion", Toast.LENGTH_SHORT);
            }
        });
    }

    private void registerParking(){
        Calendar calendar  = Calendar.getInstance();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://52.171.139.242:44372/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        productAPI productAPI = retrofit.create(productAPI.class);
        ParkingLog log = new ParkingLog();
        log.id = 2;
        log.idParking= parkingInfo.id;
        log.idUser = userCredentials.getId();
        log.dateStart = calendar.getTime();
        log.dateEnd = calendar.getTime();


        Call<String> call = productAPI.reserve(log);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    String result = response.body();
                    Toast.makeText(ParkingActivity.this, result, Toast.LENGTH_SHORT).show();
                    retireButton.setVisibility(View.VISIBLE);
                    updateCapacity(log.idParking);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void retire(int id, int idPark){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://52.171.139.242:44372/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        productAPI productAPI = retrofit.create(productAPI.class);
        Call<String> call = productAPI.retire(id, idPark);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    String result = response.body();
                    Toast.makeText(ParkingActivity.this, result, Toast.LENGTH_SHORT).show();
                    retireButton.setVisibility(View.INVISIBLE);
                    updateCapacity(idPark);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void checkIfParked(int id, int idPark){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://52.171.139.242:44372/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        productAPI productAPI = retrofit.create(productAPI.class);
        Call<String> call = productAPI.checkIfExist(id, idPark);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try{
                    if(response.isSuccessful()){
                        String result = response.body();
                        if(result.contains("Exist")){
                            retireButton.setVisibility(View.VISIBLE);
                        }else{
                            retireButton.setVisibility(View.INVISIBLE);
                        }
                    }
                }catch (Exception ex){
                    Toast.makeText(ParkingActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(ParkingActivity.this, "Error de conexion", Toast.LENGTH_SHORT);
            }
        });
    }

    private void updateCapacity(int id){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://52.171.139.242:44372/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        productAPI productAPI = retrofit.create(productAPI.class);
        Call<Parking> call = productAPI.returnParking(id);


        call.enqueue(new Callback<Parking>() {
            @Override
            public void onResponse(Call<Parking> call, Response<Parking> response) {
                try {
                    if (response.isSuccessful()) {
                        parkingInfo = response.body();
                        occupation.setProgress(((parkingInfo.actual*100)/parkingInfo.capacity));
                        tvActual.setText(Integer.toString(parkingInfo.actual));
                    }
                }catch(Exception ex){

                }
        }

            @Override
            public void onFailure(Call<Parking> call, Throwable t) {

            }
        });
    }
}