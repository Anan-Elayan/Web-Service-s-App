package com.example.apiapp.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.apiapp.R;
import com.example.apiapp.view.ExchangeRatesActivity;
import com.example.apiapp.view.PrayerTimeActivity;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button btnPrayer = findViewById(R.id.btnPrayer);
        Button btnNews = findViewById(R.id.btnExchangeRates);
    }

    // action when user select prayer Time activity
    public void prayerTimeAction(View view) {

        Intent intent = new Intent(this, PrayerTimeActivity.class);
        startActivity(intent);
    }

    // action when user select exchange rats
    public void exchangeReatesAction(View view) {
        Intent intent = new Intent(this, ExchangeRatesActivity.class);
        startActivity(intent);
    }
}