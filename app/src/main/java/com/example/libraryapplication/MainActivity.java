package com.example.libraryapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.libraryapplication.Adapter.RecyclerViewAdapter;
import com.example.libraryapplication.Model.Book;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private BottomNavigationView bottomNavigationView;
    private MyDatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDatabaseHelper = new MyDatabaseHelper(this);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_MAINACTIVITY);
        bottomNavigationView.setSelectedItemId(R.id.home_menu_BOTTOMNAVMENU);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home_menu_BOTTOMNAVMENU:
                        return true;

                    case R.id.add_new_book_menu_BOTTOMNAVMENU:
                        StartAddNewBookActivity();
                        return  true;

                    case R.id.search_nav_BOTTOMNAVMENU:
                        StartSearchActivity();
//                        StartBookDetailsActivity();
                        return true;
                }

                return false;
            }
        });

        List<Book> bookList = myDatabaseHelper.getAllBooks();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_MAINACTIVITY);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new RecyclerViewAdapter(bookList, this);
        recyclerView.setAdapter(mAdapter);
    }

    private void StartAddNewBookActivity()
    {
        Intent intent = new Intent(MainActivity.this, AddNewBookActivity.class);
        startActivity(intent);
    }
    private void StartSearchActivity()
    {
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);
    }
    private void StartBookDetailsActivity()
    {
        Intent intent = new Intent(MainActivity.this, BookDetailsActivity.class);
        startActivity(intent);
    }
}