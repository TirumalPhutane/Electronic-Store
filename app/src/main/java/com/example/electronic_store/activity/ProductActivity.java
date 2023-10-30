package com.example.electronic_store.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.electronic_store.Entity.Cart;
import com.example.electronic_store.Entity.OrderDetail;
import com.example.electronic_store.Entity.Product;
import com.example.electronic_store.R;
import com.example.electronic_store.utils.API;
import com.example.electronic_store.utils.RetrofitClient;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.JsonObject;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    ImageView imageView;
    TextView textName, textPrice, textQuantity, textBrand, textMcountry, textDescription;
    RatingBar ratingBar;
    Button btnAddToCart, btnBuy;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        imageView = findViewById(R.id.imageView);
        textName = findViewById(R.id.textName);
        textPrice = findViewById(R.id.textPrice);
        textQuantity = findViewById(R.id.textQuantity);
        textBrand = findViewById(R.id.textBrand);
        textMcountry = findViewById(R.id.textMcountry);
        textDescription = findViewById(R.id.textDescription);
        ratingBar = findViewById(R.id.ratingBar);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnBuy = findViewById(R.id.btnBuy);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.app4);
        getSupportActionBar().setTitle("");

        product = (Product) getIntent().getSerializableExtra("product");

        getProductDetails();

        btnAddToCart.setOnClickListener(this);
        btnBuy.setOnClickListener(this);
    }

    private void getProductDetails() {
        textName.setText(product.getProduct_name());
        textPrice.setText("₹ "+product.getPrice());
        textQuantity.setText("Quantity: "+product.getQuantity());
        textBrand.setText("Brand: "+product.getBrand());
        textMcountry.setText("Manufacture Country: "+product.getManufacture_country());
        textDescription.setText("Description: "+product.getDescription());
        Glide.with(this).load(API.BASE_URL+"/"+product.getImage()).into(imageView);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnAddToCart) {
            int uid = getSharedPreferences("electronic_store", MODE_PRIVATE).getInt("id", 0);
            int pid = product.getPid();
            int price = product.getPrice();
            Cart cart = new Cart();
            //cart.setUid(uid);
            cart.setPid(pid);
            cart.setPrice(price);
            cart.setQuantity(1);

            RetrofitClient.getInstance().getApi().addToCart(uid, cart).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if(response.body().getAsJsonObject().get("status").getAsString().equals("success")) {
                        new MaterialAlertDialogBuilder(ProductActivity.this)
                                .setTitle("Added to Cart")
                                .setMessage("Product has been successfully added to the cart")
                                .setPositiveButton("See cart", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(ProductActivity.this, CartActivity.class));
                                    }
                                })
                                .setNegativeButton("Add More Items", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(ProductActivity.this, MainActivity.class));
                                    }
                                })
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(ProductActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(v.getId()==R.id.btnBuy)
        {
            int uid = getSharedPreferences("electronic_store", MODE_PRIVATE).getInt("id", 0);
            OrderDetail od = new OrderDetail();
            od.setQuantity(1);
            od.setPrice(product.getPrice());
            od.setPid(product.getPid());
            od.setTotalPrice(product.getPrice());

            RetrofitClient.getInstance().getApi().buyNow(uid, od).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    startActivity(new Intent(ProductActivity.this, ShippingActivity.class));
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(ProductActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}