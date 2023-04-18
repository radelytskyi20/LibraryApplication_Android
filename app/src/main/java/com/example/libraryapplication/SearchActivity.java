package com.example.libraryapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SearchActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setTitle("Пошук");

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_SEARCHACTIVITY);
        bottomNavigationView.setSelectedItemId(R.id.search_nav_BOTTOMNAVMENU);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home_menu_BOTTOMNAVMENU:
                        StartMainActivity();
                        return true;

                    case R.id.add_new_book_menu_BOTTOMNAVMENU:
                        StartAddNewBookActivity();
                        return  true;

                    case R.id.search_nav_BOTTOMNAVMENU:
                        return true;
                }
                return false;
            }
        });
    }
    private void StartAddNewBookActivity()
    {
        Intent intent = new Intent(SearchActivity.this, AddNewBookActivity.class);
        startActivity(intent);
    }
    private void StartMainActivity()
    {
        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
        startActivity(intent);
    }
}