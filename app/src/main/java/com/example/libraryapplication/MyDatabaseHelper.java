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
    public static final String COLUMN_URL = "url";


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
                        + COLUMN_URL + " TEXT, "
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
        cv.put(COLUMN_URL, book.getUrl());
        long result = db.insert(TABLE_MY_LIBRARY, null, cv);
        db.close();

        return result != -1;
    }
    public List<Book> getAllBooks() {
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
                String url = cursor.getString(6);
                byte[] image = cursor.getBlob(7);

                Book book = new Book(id, pages, title, author, genre, language, url, image);
                bookList.add(book);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return bookList;
    }


    public Book findBookById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = new String[] {
                COLUMN_ID,
                COLUMN_TITLE,
                COLUMN_AUTHOR,
                COLUMN_PAGES,
                COLUMN_GENRE,
                COLUMN_LANGUAGE,
                COLUMN_URL,
                COLUMN_IMAGE
        };
        String selection = COLUMN_ID + "=?";
        String[] selectionArgs = new String[] {String.valueOf(id)};
        Cursor cursor = db.query(TABLE_MY_LIBRARY, columns, selection, selectionArgs, null, null, null, null);

        Book book = null;
        if (cursor != null && cursor.moveToFirst()) {
            int bookId = cursor.getInt(0);
            String title = cursor.getString(1);
            String author = cursor.getString(2);
            int pages = cursor.getInt(3);
            String genre = cursor.getString(4);
            String language = cursor.getString(5);
            String url = cursor.getString(6);
            byte[] image = cursor.getBlob(7);

            book = new Book(bookId, pages, title, author, genre, language, url, image);
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return book;
    }

    public boolean updateBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, book.getTitle());
        values.put(COLUMN_AUTHOR, book.getAuthor());
        values.put(COLUMN_PAGES, book.getPages());
        values.put(COLUMN_GENRE, book.getGenre());
        values.put(COLUMN_LANGUAGE, book.getLanguage());
        values.put(COLUMN_URL, book.getUrl());
        values.put(COLUMN_IMAGE, book.getImage());

        int result = db.update(TABLE_MY_LIBRARY, values, COLUMN_ID + " = ?", new String[]{String.valueOf(book.getId())});
        db.close();

        return result > 0;
    }

    public boolean deleteBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "SELECT * FROM " + TABLE_MY_LIBRARY + " WHERE " + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(queryString, new String[]{String.valueOf(book.getId())});

        if (cursor.moveToFirst()) {
            int result = db.delete(TABLE_MY_LIBRARY, COLUMN_ID + " = ?", new String[]{String.valueOf(book.getId())});
            cursor.close();
            db.close();
            return (result != 0);
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }
}
