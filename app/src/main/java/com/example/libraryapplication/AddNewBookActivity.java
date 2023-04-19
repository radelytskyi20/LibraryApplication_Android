package com.example.libraryapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.libraryapplication.Model.Book;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AddNewBookActivity extends AppCompatActivity {

    EditText titleInputText, authorInputText, pagesInputText, genreInputText, languageInputText, urlInputText;
    ImageView bookImageView;
    Button addNewBookButton;
    private byte[] bookImageInBytes;
    private static final int PICK_IMAGE_REQUEST = 1;
    private MyDatabaseHelper myDatabaseHelper;

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_book);
        getSupportActionBar().setTitle("Додати нову книгу");

        titleInputText = (EditText) findViewById(R.id.enter_book_title_ADDNEWBOOKACTIVITY);
        authorInputText = (EditText) findViewById(R.id.enter_author_ADDNEWBOOKACTIVITY);
        pagesInputText = (EditText) findViewById(R.id.enter_number_of_pages_ADDNEWBOOKACTIVITY);
        genreInputText = (EditText) findViewById(R.id.enter_genre_ADDNEWBOOKACTVITY);
        languageInputText = (EditText) findViewById(R.id.enter_language_ADDNEWBOOKACTIVITY);
        urlInputText = (EditText) findViewById(R.id.enter_url_ADDNEWBOOKACTIVITY);

        bookImageView = (ImageView) findViewById(R.id.select_book_image_ADDNEWBOOKACTIVITY);
        addNewBookButton = (Button) findViewById(R.id.add_book_button_ADDNEWBOOKACTIVITY);

        myDatabaseHelper = new MyDatabaseHelper(this);

        bookImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenFileChooser();
            }
        });

        addNewBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewBook();
            }
        });

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_ADDNEWBOOKACTIVITY);
        bottomNavigationView.setSelectedItemId(R.id.add_new_book_menu_BOTTOMNAVMENU);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home_menu_BOTTOMNAVMENU:
                        StartMainActivity();
                        return true;

                    case R.id.add_new_book_menu_BOTTOMNAVMENU:
                        return  true;

                    case R.id.search_nav_BOTTOMNAVMENU:
                        StartSearchActivity();
                        return true;
                }

                return false;
            }
        });
    }
    private void OpenFileChooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void AddNewBook()
    {
        String title = titleInputText.getText().toString();
        String author = authorInputText.getText().toString();
        String pages = pagesInputText.getText().toString();
        String genre = genreInputText.getText().toString();
        String language = languageInputText.getText().toString();
        String url = urlInputText.getText().toString();

        if (DataIsEmpty(title, author, pages, genre, language, url))
        {
            Toast.makeText(this, "Заповніть усі поля та додайте фото.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Book newBook = new Book(
                    Integer.valueOf(pages),
                    title,
                    author,
                    genre,
                    language,
                    url,
                    bookImageInBytes
            );

            boolean operationResult = myDatabaseHelper.AddBook(newBook);

            if (operationResult)
            {
                Toast.makeText(this, "Книгу успішно додано.", Toast.LENGTH_SHORT).show();
                Intent addNewBookActivity = new Intent(AddNewBookActivity.this, AddNewBookActivity.class);
                startActivity(addNewBookActivity);
            }
            else
                Toast.makeText(this, "Помилка під час запису. Спробуйте пізніше.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean DataIsEmpty(String title, String author, String pages, String genre, String language, String url)
    {
        return TextUtils.isEmpty(title) || TextUtils.isEmpty(author) || TextUtils.isEmpty(pages)
                || TextUtils.isEmpty(genre) || TextUtils.isEmpty(language) || TextUtils.isEmpty(url)
                || bookImageInBytes == null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                bookImageInBytes = outputStream.toByteArray();
                bookImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(AddNewBookActivity.this, "Помилка!Спробуйте пізніше.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void StartMainActivity()
    {
        Intent intent = new Intent(AddNewBookActivity.this, MainActivity.class);
        startActivity(intent);
    }
    private void StartSearchActivity()
    {
        Intent intent = new Intent(AddNewBookActivity.this, SearchActivity.class);
        startActivity(intent);
    }
}