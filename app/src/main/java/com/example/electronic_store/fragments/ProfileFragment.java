package com.example.electronic_store.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.electronic_store.R;
import com.example.electronic_store.adapter.UpdateRecyclerAdapter;
import com.example.electronic_store.utils.RetrofitClient;
import com.google.gson.JsonObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment implements View.OnClickListener{

    ActivityResultLauncher<Intent> activityResultLauncher;
    TextView textName,textEmail, textMobile, textAddress, textPincode, textGender;
    ImageView imageView;
    Button btneditImage;
    RecyclerView recyclerView;
    String [] options = new String[] {"Update profile Details", "Change password", "Sign out"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = view.findViewById(R.id.imageView);
        imageView.setClipToOutline(true);
        btneditImage = view.findViewById(R.id.btneditImage);
        textName = view.findViewById(R.id.textName);
        textEmail = view.findViewById(R.id.textEmail);
        textMobile = view.findViewById(R.id.textMobile);
        textAddress = view.findViewById(R.id.textAddress);
        textPincode = view.findViewById(R.id.textPincode);
        textGender = view.findViewById(R.id.textGender);
        recyclerView = view.findViewById(R.id.recyclerView);

        UpdateRecyclerAdapter updateRecyclerAdapter = new UpdateRecyclerAdapter(getContext(), options);
        recyclerView.setAdapter(updateRecyclerAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        btneditImage.setOnClickListener(this);
        getUserById();
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserById();
    }

    private void getUserById() {
        int id = getContext().getSharedPreferences("electronic_store", Context.MODE_PRIVATE).getInt("id",0);

        RetrofitClient.getInstance().getApi().getUserById(id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().getAsJsonObject().get("status").getAsString().equals("success"))
                {
                    JsonObject object = response.body().getAsJsonObject().get("data").getAsJsonArray().get(0).getAsJsonObject();
                    textName.setText("Name : "+object.get("uname").getAsString());
                    textEmail.setText("Email : "+object.get("email").getAsString());
                    textMobile.setText("Mobile : "+object.get("mobileNo").getAsString());
                    textAddress.setText("Address : "+object.get("address").getAsString());
                    textPincode.setText("Pincode : "+object.get("pincode").getAsString());
                    textGender.setText("Gender : "+object.get("gender").getAsString());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btneditImage)
        {
            if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]
                        {Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_MEDIA_IMAGES }, 22);            }


            if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_MEDIA_IMAGES)== PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]
                        {Manifest.permission.READ_MEDIA_IMAGES}, 23);
            }



            changeProfileImage(v);

        }

        activityResultLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode()== Activity.RESULT_OK)
                {
                    Intent data = result.getData();
                    Uri uri = data.getData();
                    String path = com.sunbeam.Job_portal.utils.RealPathUtils.getRealPath(getContext(),uri);
                    imageView.setImageURI(uri);
                    //Creating File to be upload over server
                    File file = new File(path);
                    RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
                    MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", file.getName(), requestBody);

//                    RetroClient.getInstance().getApi().uploadImg(applicantId,filePart).enqueue(new Callback<JsonObject>() {
//                        @Override
//                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                            Toast.makeText(getContext(), "sucesss", Toast.LENGTH_SHORT).show();
//                            imageView.setImageURI(uri);
//                        }
//                        @Override
//                        public void onFailure(Call<JsonObject> call, Throwable t) {
//                            Toast.makeText(getContext(), "fail", Toast.LENGTH_SHORT).show();
//                        }
//                    });
                }
            }
        });
    }

    private void changeProfileImage(View v) {

//        Intent iProfile = new Intent(Intent.ACTION_PICK);
//        Toast.makeText(getContext(), "ABC", Toast.LENGTH_SHORT).show();
//        iProfile.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
////        activityResultLauncher.launch(iProfile);
    }
}