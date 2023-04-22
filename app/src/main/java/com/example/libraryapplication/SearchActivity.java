package com.example.libraryapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SearchActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    EditText inputSearchEditText;
    Button searchByTitleButton, searchByAuthorButton, searchByGenreButton, searchByLanguageButton, sortMaxPagesButton, sortMinPagesButton;

    public static final String CLICKED_BUTTON_KEY = "buttonClicked";
    public static final String SEARCH_TEXT_KEY = "searchText";
    public static final String SEARCH_BY_TITLE_BUTTON = "searchByTitle";
    public static final String SEARCH_BY_AUTHOR_BUTTON = "searchByAuthor";
    public static final String SEARCH_BY_GENRE_BUTTON = "searchByGenre";
    public static final String SEARCH_BY_LANGUAGE = "searchByLanguage";
    public static final String SORT_MAX_PAGES_BUTTON = "sortMaxPages";
    public static final String SORT_MIN_PAGES_BUTTON = "sortMinPages";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setTitle("Пошук");

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_SEARCHACTIVITY);
        bottomNavigationView.setSelectedItemId(R.id.search_nav_BOTTOMNAVMENU);

        inputSearchEditText = (EditText) findViewById(R.id.input_search_text_SEARCHACTIVITY);

        searchByTitleButton = (Button) findViewById(R.id.search_by_title_button_SEARCHACTIVITY);
        searchByAuthorButton = (Button) findViewById(R.id.search_by_author_button_SEARCHACTIVITY);
        searchByGenreButton = (Button) findViewById(R.id.search_by_genre_button_SEARCHACTIVITY);
        searchByLanguageButton = (Button) findViewById(R.id.search_by_language_button_SEARCHACTIVITY);
        sortMaxPagesButton = (Button) findViewById(R.id.sort_by_max_pages_button_SEARCHACTIVITY);
        sortMinPagesButton = (Button) findViewById(R.id.sort_by_min_pages_button_SEARCHACTIVITY);

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

        searchByTitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartSearchResultActivity(SEARCH_BY_TITLE_BUTTON, inputSearchEditText.getText().toString());
            }
        });

        searchByAuthorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartSearchResultActivity(SEARCH_BY_AUTHOR_BUTTON, inputSearchEditText.getText().toString());
            }
        });

        searchByGenreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartSearchResultActivity(SEARCH_BY_GENRE_BUTTON, inputSearchEditText.getText().toString());
            }
        });

        searchByLanguageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartSearchResultActivity(SEARCH_BY_LANGUAGE, inputSearchEditText.getText().toString());
            }
        });

        sortMaxPagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartSearchResultActivity(SORT_MAX_PAGES_BUTTON, "");
            }
        });

        sortMinPagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartSearchResultActivity(SORT_MIN_PAGES_BUTTON, "");
            }
        });

    }
    private void StartSearchResultActivity(String buttonName, String searchText)
    {
        if (!TextUtils.isEmpty(searchText) || buttonName.equals(SORT_MIN_PAGES_BUTTON) || buttonName.equals(SORT_MAX_PAGES_BUTTON))
        {
            Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
            intent.putExtra(CLICKED_BUTTON_KEY, buttonName);
            intent.putExtra(SEARCH_TEXT_KEY, searchText);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Помилка! Введіть текст пошуку.", Toast.LENGTH_SHORT).show();
        }
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