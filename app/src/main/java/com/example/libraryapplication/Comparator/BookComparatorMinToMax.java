package com.example.libraryapplication.Comparator;

import com.example.libraryapplication.Model.Book;

import java.util.Comparator;

public class BookComparatorMinToMax implements Comparator<Book>
{
    public int compare(Book book1, Book book2)
    {
        return Integer.compare(book1.getPages(), book2.getPages());
    }
}
