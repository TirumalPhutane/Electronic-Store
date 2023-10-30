package com.example.electronic_store.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.electronic_store.Entity.Orders;
import com.example.electronic_store.Entity.Price;
import com.example.electronic_store.Entity.Product;
import com.example.electronic_store.R;
import com.example.electronic_store.adapter.CartRecyclerAdapter;
import com.example.electronic_store.adapter.ProductListRecyclerAdapter;
import com.example.electronic_store.utils.RetrofitClient;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements CartRecyclerAdapter.IMyinterface, View.OnClickListener{

    Toolbar toolBar;
    RecyclerView recyclerview;
    CartRecyclerAdapter cartRecyclerAdapter;
    List<Product> productList;
    Button btnPlaceOrder;
    TextView textTotalPrice;
    double total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        toolBar = findViewById(R.id.toolBar);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        textTotalPrice = findViewById(R.id.textTotalPrice);
        recyclerview = findViewById(R.id.recyclerview);
        productList = new ArrayList<>();
        cartRecyclerAdapter = new CartRecyclerAdapter(this, productList, this);
        recyclerview.setAdapter(cartRecyclerAdapter);
        recyclerview.setLayoutManager(new GridLayoutManager(this,1));

        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.app4);
        getSupportActionBar().setTitle("");

        btnPlaceOrder.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUseritemsFromCart();
    }

    private void getUseritemsFromCart() {
        productList.clear();
        int id = getSharedPreferences("electronic_store",MODE_PRIVATE).getInt("id",0);

        RetrofitClient.getInstance().getApi().getUserItemsFromCart(id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().getAsJsonObject().get("status").getAsString().equals("success"))
                {
                    JsonArray jsonArray = response.body().getAsJsonObject().get("data").getAsJsonArray();
                    if(jsonArray.size()!=0)
                    {
                        JsonObject object = jsonArray.get(0).getAsJsonObject();
                        getSharedPreferences("electronic_store", MODE_PRIVATE).edit().putInt("cart_id",object.get("cid").getAsInt()).apply();
                    }

                    for (JsonElement element:jsonArray)
                    {
                        Product product = new Product();
                        product.setPid(element.getAsJsonObject().get("pid").getAsInt());
                        product.setPrice(element.getAsJsonObject().get("price").getAsInt());
                        product.setProduct_name(element.getAsJsonObject().get("product_name").getAsString());
                        product.setBrand(element.getAsJsonObject().get("brand").getAsString());
                        product.setQuantity(element.getAsJsonObject().get("quantity").getAsInt());
                        product.setImage(element.getAsJsonObject().get("image").getAsString());
                        productList.add(product);
                    }
//                    double total = 0;
                    for (Product product : productList) {
                        total += product.getQuantity() * product.getPrice();
                    }

                    textTotalPrice.setText("Subtotal: ₹ "+total);
                    cartRecyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void refresh() {
        getUseritemsFromCart();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnPlaceOrder)
        {
            int id = getSharedPreferences("electronic_store",MODE_PRIVATE).getInt("id",0);
            Price price = new Price();
            price.setTotalPrice(total);

            RetrofitClient.getInstance().getApi().placeOrder(id,price).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if(response.body().getAsJsonObject().get("status").getAsString().equals("error"))
                    {
                        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(CartActivity.this);
                        builder.setMessage("Your cart is empty. Please add products to the cart.");
                        builder.setTitle("Can't Create Order!");
                        builder.setPositiveButton("OK", null);
                        builder.setNegativeButton("Add Items", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(CartActivity.this, MainActivity.class));
                            }
                        });
                        builder.show();
                    }
                    else if (response.body().getAsJsonObject().get("status").getAsString().equals("success"))
                    {
                        startActivity(new Intent(CartActivity.this, ShippingActivity.class));
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(CartActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}