package com.example.libraryapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.libraryapplication.Model.Book;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class BookDetailsActivity extends AppCompatActivity {

    ImageView bookImageView;
    EditText editBookTitleText, editBookAuthorText, editBookGenreText, editBookLanguageText, editBookPagesText, editBookUrlText;
    Button updateBookInfoButton, deleteBookButton, readBookButton;
    String currentBookId;
    Book currentBook;
    MyDatabaseHelper myDatabaseHelper;
    private static final int PICK_IMAGE_REQUEST = 1;
    private byte[] updatedBookImageInBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        getSupportActionBar().setTitle("Загальна інформація");

        currentBookId = getIntent().getStringExtra("id");
        myDatabaseHelper = new MyDatabaseHelper(this);
        currentBook = myDatabaseHelper.findBookById(Integer.parseInt(currentBookId));
        bookImageView = (ImageView) findViewById(R.id.edit_book_image_BOOKDETAILSACTIVITY);

        editBookTitleText = (EditText) findViewById(R.id.edit_book_title_BOOKDETAILSACTIVITY);
        editBookAuthorText = (EditText) findViewById(R.id.edit_author_BOOKDETAILSACTIVITY);
        editBookGenreText = (EditText) findViewById(R.id.edit_genre_BOOKDETAILSACTIVITY);
        editBookLanguageText = (EditText) findViewById(R.id.edit_language_BOOKDETAILSACTIVITY);
        editBookPagesText = (EditText) findViewById(R.id.edit_number_of_pages_BOOKDETAILSACTIVITY);
        editBookUrlText = (EditText) findViewById(R.id.edit_url_BOOKDETAILSACTIVITY);

        updateBookInfoButton = (Button) findViewById(R.id.update_book_info_button_BOOKDETAILSACTIVITY);
        deleteBookButton = (Button) findViewById(R.id.delete_book_button_BOOKDETAILSACTIVITY);
        readBookButton = (Button) findViewById(R.id.read_book_button_BOOKDETAILSACTIVITY);


        if (currentBook != null)
            DisplayCurrentBook();


        bookImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenFileChooser();
            }
        });

        updateBookInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateBookInfo();
            }
        });

        deleteBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBook();
            }
        });

        readBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readBook();
            }
        });
    }

    private void readBook()
    {
        Intent readBookActivity = new Intent(BookDetailsActivity.this, ReadBookActivity.class);
        readBookActivity.putExtra("id", currentBookId);
        startActivity(readBookActivity);
    }

    private void deleteBook()
    {
        boolean result = myDatabaseHelper.deleteBook(currentBook);

        if (result){
            Toast.makeText(this, "Книгу було успішно видалено.", Toast.LENGTH_SHORT).show();
            startMainActivity();
        }
        else {
            Toast.makeText(this, "Сталась помилка під час видалення. Спробуйте пізніше.", Toast.LENGTH_SHORT).show();
        }
    }
    private void updateBookInfo()
    {
        Book updatedBook = getUpdatedBook();

        if (updatedBook != null)
        {
            boolean updateResult = myDatabaseHelper.updateBook(updatedBook);

            if (updateResult)
            {
                Toast.makeText(this, "Інформація була успішно оновлена.", Toast.LENGTH_SHORT).show();
                startMainActivity();
            }
            else
                Toast.makeText(this, "Помилка під час оновлення інформації! Спробуйте пізніше.", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "Помилка! Заповніть усі поля.", Toast.LENGTH_SHORT).show();
    }

    private void startMainActivity()
    {
        Intent intent = new Intent(BookDetailsActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private Book getUpdatedBook()
    {
        String title = editBookTitleText.getText().toString();
        String author = editBookAuthorText.getText().toString();
        String genre = editBookGenreText.getText().toString();
        String language = editBookLanguageText.getText().toString();
        String pages = editBookPagesText.getText().toString();
        String url = editBookUrlText.getText().toString();

        if (!DataIsEmpty(title, author, pages, genre, language, url))
        {
            Book updatedBook = new Book(
                    currentBook.getId(),
                    Integer.parseInt(pages),
                    title,
                    author,
                    genre,
                    language,
                    url,
                    updatedBookImageInBytes == null ? currentBook.getImage() : updatedBookImageInBytes
            );
            return updatedBook;
        }
        else
            return null;
    }

    private boolean DataIsEmpty(String title, String author, String pages, String genre, String language, String url)
    {
        return TextUtils.isEmpty(title) || TextUtils.isEmpty(author) || TextUtils.isEmpty(pages)
                || TextUtils.isEmpty(genre) || TextUtils.isEmpty(language) || TextUtils.isEmpty(url);
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
                updatedBookImageInBytes = outputStream.toByteArray();
                bookImageView.setImageBitmap(bitmap);
                Toast.makeText(this, "Зображення успішно додано.", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(BookDetailsActivity.this, "Помилка!Спробуйте пізніше.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void OpenFileChooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void DisplayCurrentBook()
    {
        Glide.with(this).load(currentBook.getImage()).into(bookImageView);
        editBookTitleText.setText(currentBook.getTitle());
        editBookAuthorText.setText(currentBook.getAuthor());
        editBookGenreText.setText(currentBook.getGenre());
        editBookLanguageText.setText(currentBook.getLanguage());
        editBookPagesText.setText(String.valueOf(currentBook.getPages()));
        editBookUrlText.setText(currentBook.getUrl());
    }
}