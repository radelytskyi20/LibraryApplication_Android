package com.example.libraryapplication.Model;

public class Book
{
    private int id, pages;
    private String title, author, genre, language, url;
    private byte[] image;

    public Book(int id, int pages, String title, String author, String genre, String language, String url, byte[] image) {
        this.id = id;
        this.pages = pages;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.language = language;
        this.url = url;
        this.image = image;

    }

    public Book(int pages, String title, String author, String genre, String language, String url, byte[] image) {
        this.pages = pages;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.language = language;
        this.url = url;
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLanguage() {
        return language;
    }

        public void setLanguage(String language) {
        this.language = language;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
