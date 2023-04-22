package com.example.libraryapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.libraryapplication.Adapter.RecyclerViewAdapter;
import com.example.libraryapplication.Comparator.BookComparatorMaxToMin;
import com.example.libraryapplication.Comparator.BookComparatorMinToMax;
import com.example.libraryapplication.Model.Book;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

    String clickedButton, searchText;
    List<Book> searchResultList;
    MyDatabaseHelper myDatabaseHelper;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        getSupportActionBar().setTitle("Результати пошуку");

        clickedButton = getIntent().getStringExtra(SearchActivity.CLICKED_BUTTON_KEY);
        searchText = getIntent().getStringExtra(SearchActivity.SEARCH_TEXT_KEY);
        myDatabaseHelper = new MyDatabaseHelper(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_SEARCHRESULTACTIVITY);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        switch (clickedButton)
        {
            case SearchActivity.SEARCH_BY_TITLE_BUTTON:
                searchResultList = myDatabaseHelper.SearchByTitle(searchText);
                DisplayResult(searchResultList);
                break;

            case SearchActivity.SEARCH_BY_AUTHOR_BUTTON:
                searchResultList = myDatabaseHelper.SearchByAuthor(searchText);
                DisplayResult(searchResultList);
                break;

            case SearchActivity.SEARCH_BY_GENRE_BUTTON:
                searchResultList = myDatabaseHelper.SearchByGenre(searchText);
                DisplayResult(searchResultList);
                break;

            case SearchActivity.SEARCH_BY_LANGUAGE:
                searchResultList = myDatabaseHelper.SearchByLanguage(searchText);
                DisplayResult(searchResultList);
                break;

            case SearchActivity.SORT_MAX_PAGES_BUTTON:
                searchResultList = myDatabaseHelper.getAllBooks();
                Collections.sort(searchResultList, new BookComparatorMaxToMin());
                DisplayResult(searchResultList);
                break;

            case SearchActivity.SORT_MIN_PAGES_BUTTON:
                searchResultList = myDatabaseHelper.getAllBooks();
                Collections.sort(searchResultList, new BookComparatorMinToMax());
                DisplayResult(searchResultList);
                break;
        }
    }
    private void DisplayResult(List<Book> searchResultList)
    {
        mAdapter = new RecyclerViewAdapter(searchResultList, this);
        recyclerView.setAdapter(mAdapter);
        Toast.makeText(this, "Результат пошуку: " + searchResultList.size(), Toast.LENGTH_SHORT).show();
    }
}