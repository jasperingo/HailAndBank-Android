package com.example.hailandbank.ui;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hailandbank.R;

import org.jetbrains.annotations.NotNull;


public class OrdersRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;

    private final int VIEW_TYPE_LOADING = 1;

    public OrdersRecyclerViewAdapter() {

    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.progress_bar_list_item, parent, false);

            return new ProgressViewHolder(view);
        }

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orders_list_item, parent, false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 9 ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    public static class UserViewHolder extends RecyclerView.ViewHolder {

        public UserViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }

    }



}



