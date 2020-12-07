package com.example.parkingun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parkingun.interfaces.productAPI;
import com.example.parkingun.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    EditText name, surname, email, password, identification, username;
    Button registerButton;
    User credentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        name = (EditText) findViewById(R.id.name);
        surname = (EditText) findViewById(R.id.surname);
        email = (EditText) findViewById(R.id.email);
        username = (EditText) findViewById(R.id.username);
        identification = (EditText) findViewById(R.id.identification);
        registerButton = (Button) findViewById(R.id.registerButton);
        password = (EditText) findViewById(R.id.password);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    public void registerUser(){
        boolean check = checkValues();
        User user;
        if(check){
            user = createUser();
            Retrofit retrofit = new Retrofit.Builder().baseUrl("http://52.171.139.242:44372/")
                    .addConverterFactory(GsonConverterFactory.create()).build();
            productAPI productAPI = retrofit.create(productAPI.class);
            Call<String> call = productAPI.registerUser(user);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Toast.makeText(RegisterActivity.this, "Registrado Exitosamente", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(i);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });

        }

    }

    private User createUser(){
        User user = new User();
        user.setName(name.getText().toString());
        user.setSurname(surname.getText().toString());
        user.setId(15);
        user.setEmail(email.getText().toString());
        user.setPasswordF(password.getText().toString());
        user.setIdentification(Long.parseLong(identification.getText().toString()));
        user.setUsername(username.getText().toString());

        return user;
    }

    private boolean checkValues() {
        if(name.getText().toString().equals("")){
            Toast.makeText(RegisterActivity.this, "No ingreso ningun nombre", Toast.LENGTH_SHORT).show();
            return false;
        }else if(surname.getText().toString().equals("")){
            Toast.makeText(RegisterActivity.this, "No ingreso ningun apellido", Toast.LENGTH_SHORT).show();
            return false;
        }else if(email.getText().toString().equals("")){
            Toast.makeText(RegisterActivity.this, "No ingreso ningun email", Toast.LENGTH_SHORT).show();
            return false;
        }else if(username.getText().toString().equals("")){
            Toast.makeText(RegisterActivity.this, "No ingreso el nombre de usuario", Toast.LENGTH_SHORT).show();
            return false;
        }else if(identification.getText().toString().equals("")){
            Toast.makeText(RegisterActivity.this, "No ingreso la identificacion", Toast.LENGTH_SHORT).show();
            return false;
        }else if(password.getText().toString().equals("")) {
            Toast.makeText(RegisterActivity.this, "No ingreso la contraseña", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }


}



