package com.example.libraryapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.libraryapplication.Model.Book;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME = "BookLibrary.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_MY_LIBRARY = "my_library";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_PAGES = "pages";
    public static final String COLUMN_GENRE = "genre";
    public static final String COLUMN_LANGUAGE = "language";
    public static final String COLUMN_IMAGE = "image";


    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_MY_LIBRARY =
                "CREATE TABLE " + TABLE_MY_LIBRARY + " ("
                        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_TITLE + " TEXT, "
                        + COLUMN_AUTHOR + " TEXT, "
                        + COLUMN_PAGES + " INTEGER, "
                        + COLUMN_GENRE + " TEXT, "
                        + COLUMN_LANGUAGE + " TEXT, "
                        + COLUMN_IMAGE + " BLOB"
                        + ")";

        sqLiteDatabase.execSQL(CREATE_TABLE_MY_LIBRARY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MY_LIBRARY);
        onCreate(sqLiteDatabase);
    }
    public boolean AddBook(Book book)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, book.getTitle());
        cv.put(COLUMN_AUTHOR, book.getAuthor());
        cv.put(COLUMN_PAGES, book.getPages());
        cv.put(COLUMN_GENRE, book.getGenre());
        cv.put(COLUMN_LANGUAGE, book.getLanguage());
        cv.put(COLUMN_IMAGE, book.getImage());

        long result = db.insert(TABLE_MY_LIBRARY, null, cv);
        db.close();

        return result != -1;
    }
    public List<Book> getAllBooks()
    {
        List<Book> bookList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MY_LIBRARY, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String author = cursor.getString(2);
                int pages = cursor.getInt(3);
                String genre = cursor.getString(4);
                String language = cursor.getString(5);
                byte[] image = cursor.getBlob(6);

                Book book = new Book(id, pages, title, author, genre, language, image);
                bookList.add(book);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return bookList;
    }
}
