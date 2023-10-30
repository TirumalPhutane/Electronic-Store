package com.example.electronic_store.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.electronic_store.Entity.User;
import com.example.electronic_store.R;
import com.example.electronic_store.activity.LoginActivity;
import com.example.electronic_store.utils.RetrofitClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editName, editAddress, editEmail, editMobile, editPassword, editConfirmPassword, editPincode;
    RadioButton radioMale, radioFemale;
    Button btnRegister, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editName = findViewById(R.id.editName);
        editAddress = findViewById(R.id.editAddress);
        editEmail = findViewById(R.id.editEmail);
        editMobile = findViewById(R.id.editMobile);
        editPassword = findViewById(R.id.editPassword);
        editConfirmPassword = findViewById(R.id.editConfirmPassword);
        editPincode = findViewById(R.id.editPincode);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        btnRegister = findViewById(R.id.btnRegister);
        btnCancel = findViewById(R.id.btnCancel);

        btnRegister.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btnRegister)
        {
            User user = validateUser();
            if(user!=null)
            {
                RetrofitClient.getInstance().getApi().registerUser(user).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "Something Went Wrong.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        else if (v.getId()==R.id.btnCancel)
            finish();
    }

    private User validateUser() {
        String password = editPassword.getText().toString();
        String confirmPassord = editConfirmPassword.getText().toString();

        String gender = "";
        if(radioMale.isChecked())
            gender = "Male";
        if(radioFemale.isChecked())
            gender = "Female";

        if(password.equals(confirmPassord))
        {
            User user = new User();
            user.setUname(editName.getText().toString());
            user.setEmail(editEmail.getText().toString());
            user.setMobileNo(editMobile.getText().toString());
            user.setPassword(password);
            user.setAddress(editAddress.getText().toString());
            user.setPincode(editPincode.getText().toString());
            user.setGender(gender);
            return user;
        }
        else
        {
            Toast.makeText(this, "Password is not matching.", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}