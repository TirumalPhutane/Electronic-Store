package com.example.electronic_store.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.electronic_store.Entity.User;
import com.example.electronic_store.R;
import com.example.electronic_store.utils.RetrofitClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editOldPassword, editNewPassword, editNewConfirmPassword;
    Button btnUpdatePass;
    Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        editOldPassword = findViewById(R.id.editOldPassword);
        editNewPassword = findViewById(R.id.editNewPassword);
        editNewConfirmPassword = findViewById(R.id.editNewConfirmPassword);
        btnUpdatePass = findViewById(R.id.btnUpdatePass);
        toolBar = findViewById(R.id.toolBar);

        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setLogo(R.drawable.app4);
        getSupportActionBar().setTitle("");

        btnUpdatePass.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnUpdatePass)
        {
            User user = validateUser();
            int uid = getSharedPreferences("electronic_store", MODE_PRIVATE).getInt("id",0);

            if(user!=null)
            {
                RetrofitClient.getInstance().getApi().changePassword(uid, user).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Toast.makeText(ChangePasswordActivity.this, "Successfully Updated.", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(ChangePasswordActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    private User validateUser() {
        String password = getSharedPreferences("electronic_store", MODE_PRIVATE).getString("pass",null);
        String oldPassword = editOldPassword.getText().toString();
        String newPassword = editNewPassword.getText().toString();
        String confirmPassord = editNewConfirmPassword.getText().toString();

        if(password.equals(oldPassword))
        {
            if(newPassword.equals(confirmPassord))
            {
                User user = new User();
                user.setPassword(newPassword);
                return user;
            }
            else {
                Toast.makeText(this, "New password is not matching with confirm password.", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        else {
            Toast.makeText(this, "Old password is incorrect.", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}