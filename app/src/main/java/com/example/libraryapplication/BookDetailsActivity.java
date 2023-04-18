package com.example.libraryapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class BookDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        getSupportActionBar().setTitle("Загальна інформація");
    }
}