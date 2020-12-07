package com.example.parkingun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkingun.interfaces.productAPI;
import com.example.parkingun.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    EditText et_Email, et_Password;
    TextView tvRegister;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_Email = (EditText) findViewById(R.id.et_Email);
        et_Password = (EditText) findViewById(R.id.et_Password);
        tvRegister = (TextView) findViewById(R.id.tvRegister);
        loginButton = (Button) findViewById(R.id.loginButton);


        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findUser(et_Email.getText().toString());
            }
        });
    }

    private void findUser(String id){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://52.171.139.242:44372/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        productAPI productAPI = retrofit.create(productAPI.class);
        Call<User> call = productAPI.findUser(id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try{
                    if(response.isSuccessful()){
                        User credentials = response.body();
                        if(credentials.getUsername().equals(et_Email.getText().toString()) && credentials.getPasswordF().equals(et_Password.getText().toString())){
                            Intent i = new Intent(LoginActivity.this, MapActivity.class);
                            Toast.makeText(LoginActivity.this, "Login Correcto", Toast.LENGTH_SHORT).show();
                            i.putExtra("Credentials", credentials);
                            startActivity(i);
                        }else if(credentials.getUsername().equals("default")){
                            Toast.makeText(LoginActivity.this, "Usuario no Registrado", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LoginActivity.this, "Credenciales Incorrectas", Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch(Exception ex){
                    Toast.makeText(LoginActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error de conexion", Toast.LENGTH_SHORT);
            }
        });
    }
}