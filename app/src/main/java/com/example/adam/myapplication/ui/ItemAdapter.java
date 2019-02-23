package com.example.adam.myapplication.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class ItemAdapter<T> extends RecyclerView.Adapter<ItemAdapter<T>.ItemViewHolder> {

    private List<T> list = new ArrayList<>();
    private OnItemClickListener<T> listener;

    public ItemAdapter(OnItemClickListener<T> listener) {
        this.listener = listener;
    }

    public void loadItems(List<T> items) {
        this.list = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    abstract public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position);

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.setListener(listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    abstract class ItemViewHolder extends RecyclerView.ViewHolder {

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void setListener(OnItemClickListener<T> listener) {
            listener.onItemClick(itemView, list.get(getAdapterPosition()));
        }
    }
}
