package com.example.electronic_store.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.electronic_store.R;

public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolBar;
    TextView textEmail, textPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        toolBar = findViewById(R.id.toolBar);
        textEmail = findViewById(R.id.textEmail);
        textPhone = findViewById(R.id.textPhone);

        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setLogo(R.drawable.app4);
        getSupportActionBar().setTitle("");

        textEmail.setOnClickListener(this);
        textPhone.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.textEmail)
        {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/html");
            intent.putExtra(Intent.EXTRA_EMAIL, "helpdesk@etchworld.com");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Contact Reason");
            intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");
            startActivity(Intent.createChooser(intent, "Send Email"));
        }
        else if (v.getId()==R.id.textPhone)
        {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+91 1800 222 2222"));
            startActivity(intent);
        }
    }
}