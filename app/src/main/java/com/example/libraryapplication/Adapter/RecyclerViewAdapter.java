package com.example.libraryapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.libraryapplication.BookDetailsActivity;
import com.example.libraryapplication.Model.Book;
import com.example.libraryapplication.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>
{
    List<Book> bookList;
    Context context;

    public RecyclerViewAdapter(List<Book> bookList, Context context) {
        this.bookList = bookList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_book, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bookTitleText.setText(bookList.get(position).getTitle());
        holder.bookAuthorText.setText(bookList.get(position).getAuthor());
        holder.bookPagesText.setText(String.valueOf(bookList.get(position).getPages()));
        Glide.with(this.context).load(bookList.get(position).getImage()).into(holder.bookImageView);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bookDetailsActivity = new Intent(context, BookDetailsActivity.class);
                bookDetailsActivity.putExtra("id",  String.valueOf(bookList.get(position).getId()));
                context.startActivity(bookDetailsActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView bookImageView;
        TextView bookTitleText, bookAuthorText, bookPagesText;
        ConstraintLayout parentLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            bookImageView = itemView.findViewById(R.id.book_image_ONELINEBOOK);
            bookTitleText = itemView.findViewById(R.id.book_title_text_ONELINEBOOK);
            bookAuthorText = itemView.findViewById(R.id.book_author_text_ONELINEBOOK);
            bookPagesText = itemView.findViewById(R.id.book_pages_text_ONELINEBOOK);
            parentLayout = itemView.findViewById(R.id.one_line_book_layout_ONELINEBOOK);
        }
    }
}
