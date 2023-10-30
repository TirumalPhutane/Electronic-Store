package com.example.electronic_store.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.electronic_store.Entity.User;
import com.example.electronic_store.R;
import com.example.electronic_store.utils.RetrofitClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editEmail, editPassword;
    CheckBox checkBoxRememberMe;
    Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        checkBoxRememberMe = findViewById(R.id.checkBoxRememberMe);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btnLogin)
        {
            User user = new User();
            user.setEmail(editEmail.getText().toString());
            user.setPassword(editPassword.getText().toString());

            if (checkBoxRememberMe.isChecked())
                getSharedPreferences("electronic_store", MODE_PRIVATE).edit().putBoolean("login_status",true).apply();

            RetrofitClient.getInstance().getApi().loginUser(user).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    JsonArray array = response.body().getAsJsonObject().get("data").getAsJsonArray();
                    if(array.size()!=0)
                    {
                        JsonObject object = array.get(0).getAsJsonObject();
                        Log.e("JSONObject", object.toString());
                        getSharedPreferences("electronic_store", MODE_PRIVATE).edit().putInt("id",object.get("uid").getAsInt()).apply();
                        getSharedPreferences("electronic_store", MODE_PRIVATE).edit().putString("pass",object.get("password").getAsString()).apply();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                    else
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if (v.getId()==R.id.btnRegister)
            startActivity(new Intent(this, RegisterActivity.class));
    }
}