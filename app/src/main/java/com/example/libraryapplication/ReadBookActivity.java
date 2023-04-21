package com.example.libraryapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.libraryapplication.Model.Book;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReadBookActivity extends AppCompatActivity {

    String currentBookId;
    Book currentBook;
    MyDatabaseHelper myDatabaseHelper;
    WebView readBookWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_book);

        myDatabaseHelper = new MyDatabaseHelper(this);
        currentBookId = getIntent().getStringExtra("id");
        currentBook = myDatabaseHelper.findBookById(Integer.parseInt(currentBookId));

        getSupportActionBar().setTitle(currentBook.getTitle());

        readBookWebView = (WebView) findViewById(R.id.display_book_web_READBOOKACTIVITY);

        WebSettings webSettings = readBookWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        new WebsiteAvailabilityTask().execute(currentBook.getUrl());
    }

    private class WebsiteAvailabilityTask extends AsyncTask<String, Void, Boolean>
    {
        @Override
        protected Boolean doInBackground(String... urls)
        {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(urls[0]).openConnection();
                connection.setRequestMethod("HEAD");
                int responseCode = connection.getResponseCode();
                return (responseCode == HttpURLConnection.HTTP_OK);
            } catch (IOException e) {
                return false;
            }
        }
        @Override
        protected void onPostExecute(Boolean websiteAvailable) {
            if (websiteAvailable) {
                readBookWebView.loadUrl(currentBook.getUrl());
                Toast.makeText(ReadBookActivity.this, "Книга " + currentBook.getTitle() + " успішно завантажена.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ReadBookActivity.this, "Помилка під час завантаження книги. Перевірте url або інтернет з'єднання та спробуйте ще раз.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}