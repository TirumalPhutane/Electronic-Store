package com.example.electronic_store.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.electronic_store.R;
import com.example.electronic_store.activity.ChangePasswordActivity;
import com.example.electronic_store.activity.LoginActivity;
import com.example.electronic_store.activity.StatusActivity;
import com.example.electronic_store.activity.UpdateProfileActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class UpdateRecyclerAdapter extends RecyclerView.Adapter<UpdateRecyclerAdapter.MyViewHolder> {

    Context context;
    String [] options;

    public UpdateRecyclerAdapter(Context context, String[] options) {
        this.context = context;
        this.options = options;
    }

    @NonNull
    @Override
    public UpdateRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_update, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpdateRecyclerAdapter.MyViewHolder holder, int position) {
        holder.textOption.setText(options[position]);
    }

    @Override
    public int getItemCount() {
        return options.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView textOption;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textOption = itemView.findViewById(R.id.textOption);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(options[getAdapterPosition()].equals("Update profile Details"))
                context.startActivity(new Intent(context, UpdateProfileActivity.class));
            else if (options[getAdapterPosition()].equals("Change password"))
                context.startActivity(new Intent(context, ChangePasswordActivity.class));
            else if (options[getAdapterPosition()].equals("Sign out"))
            {
                new MaterialAlertDialogBuilder(context)
                        .setTitle("Sign out")
                        .setMessage("Are you sure, you want to sign out from application?")
                        .setPositiveButton("Sign out", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                context.getSharedPreferences("electronic_store", Context.MODE_PRIVATE).edit().putBoolean("login_status",false).apply();
                                context.startActivity(new Intent(context, LoginActivity.class));
                            }
                        })
                        .setNegativeButton("Cancel",null)
                        .show();
            }
        }
    }
}
