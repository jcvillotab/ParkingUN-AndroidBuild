package com.example.parkingun.interfaces;

import com.example.parkingun.models.Parking;
import com.example.parkingun.models.ParkingLog;
import com.example.parkingun.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface productAPI {
    @GET("api/Users?mail={mail}")
    public Call<User> findUserByMail(@Path("mail") String mail);

    @GET("api/Users/{id}")
    public Call<User> findUser(@Path("id") String id);

    @POST("api/Users")
    public Call<String> registerUser(@Body User value);

    @GET("api/Parking/{id}")
    public Call<Parking> returnParking(@Path("id") int id);

    @GET("api/ParkingLog/{id}")
    public Call<String> checkIfExist(@Path("id") int id,
                                     @Query("idPark") int idPark);

    @POST("api/ParkingLog")
    public Call<String> reserve(@Body ParkingLog value);

    @DELETE("api/ParkingLog/{id}")
    public Call<String> retire(@Path("id") int id,
                               @Query("idPark") int idPark);
}
