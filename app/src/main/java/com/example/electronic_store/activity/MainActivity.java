package com.example.electronic_store.activity;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.electronic_store.R;
import com.example.electronic_store.activity.LoginActivity;
import com.example.electronic_store.adapter.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager2 viewPager2;
    TabLayout tablayout;
    FragmentAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        viewPager2 = findViewById(R.id.viewPager2);
        tablayout = findViewById(R.id.tablayout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.app4);
        getSupportActionBar().setTitle("");
        toolbar.setTitleTextColor(Color.BLACK);

        fragmentAdapter = new FragmentAdapter(this);
        viewPager2.setAdapter(fragmentAdapter);

        new TabLayoutMediator(tablayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position)
                {
                    case 0:
                        tab.setIcon(R.drawable.home);
                        tab.setText("Home");
                        break;
                    case 1:
                        tab.setIcon(R.drawable.bag);
                        tab.setText("Orders");
                        break;
                    case 2:
                        tab.setIcon(R.drawable.account);
                        tab.setText("Profile");
                        break;
                }
            }
        }).attach();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals("cart"))
            startActivity(new Intent(this, CartActivity.class));
        else if (item.getTitle().equals("info")) {
            startActivity(new Intent(this, ContactUsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}