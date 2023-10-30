package com.example.electronic_store.utils;

import com.example.electronic_store.Entity.Cart;
import com.example.electronic_store.Entity.OrderDetail;
import com.example.electronic_store.Entity.Price;
import com.example.electronic_store.Entity.Product;
import com.example.electronic_store.Entity.Quantity;
import com.example.electronic_store.Entity.User;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface API {

    String BASE_URL = "http://192.168.0.115:9999";

    @POST("/user/register")
    Call<JsonObject> registerUser(@Body User user);

    @POST("/user/login")
    Call<JsonObject> loginUser(@Body User user);

    @GET("/user/{id}")
    Call<JsonObject> getUserById(@Path("id")int id);

    @POST("/product/product/{cat}")
    Call<JsonObject> getListbyCategory(@Path("cat") String cat);

    @POST("/cart/cart/{id}")
    Call<JsonObject> addToCart(@Path("id")int id, @Body Cart cart);

    @GET("/cart/cart/{id}")
    Call<JsonObject> getUserItemsFromCart(@Path("id")int id);

    @PUT("/cart/quantity/{id}")
    Call<JsonObject> updateCart(@Path("id")int id, @Body Quantity quantity);

    @POST("/cart/cartdelete/{id}")
    Call<JsonObject> deleteCart(@Path("id")int id, @Body Quantity quantity);

    @POST("/order/{id}")
    Call<JsonObject> placeOrder(@Path("id")int id, @Body Price price);

    @GET("/order/orderdetails/{id}")
    Call<JsonObject> getOrderDetails(@Path("id")int id);

    @GET("/product/view")
    Call<JsonObject> getAllCategories();

    @PUT("/user/update/{id}")
    Call<JsonObject> updateDetails(@Path("id")int id, @Body User user);

    @PUT("/user/changepassword/{id}")
    Call<JsonObject> changePassword(@Path("id")int id, @Body User user);

    @POST("/order/buynow/{id}")
    Call<JsonObject> buyNow(@Path("id")int id, @Body OrderDetail orderDetail);
 }
