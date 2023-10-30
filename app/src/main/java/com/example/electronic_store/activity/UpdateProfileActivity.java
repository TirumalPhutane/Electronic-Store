package com.example.electronic_store.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
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

public class UpdateProfileActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editEmail, editMobileNo, editAddress, editPincode;
    Button btnUpdate;
    Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        toolBar = findViewById(R.id.toolBar);
        editEmail = findViewById(R.id.editEmail);
        editMobileNo = findViewById(R.id.editMobileNo);
        editAddress = findViewById(R.id.editAddress);
        editPincode = findViewById(R.id.editPincode);
        btnUpdate = findViewById(R.id.btnUpdate);

        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setLogo(R.drawable.app4);
        getSupportActionBar().setTitle("");

        btnUpdate.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnUpdate)
        {
            User user = validateUser();
            int uid = getSharedPreferences("electronic_store", MODE_PRIVATE).getInt("id",0);

            if(user!=null)
            {
                RetrofitClient.getInstance().getApi().updateDetails(uid, user).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Toast.makeText(UpdateProfileActivity.this, "Successfully Updated.", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(UpdateProfileActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    private User validateUser() {

        User user = new User();
        user.setEmail(editEmail.getText().toString());
        user.setMobileNo(editMobileNo.getText().toString());
        user.setAddress(editAddress.getText().toString());
        user.setPincode(editPincode.getText().toString());
        return user;
    }
}