package com.example.libraryapplication.Comparator;

import com.example.libraryapplication.Model.Book;

import java.util.Comparator;

public class BookComparatorMaxToMin implements Comparator<Book>
{
    public int compare(Book b1, Book b2)
    {
        return b2.getPages() - b1.getPages();
    }
}
