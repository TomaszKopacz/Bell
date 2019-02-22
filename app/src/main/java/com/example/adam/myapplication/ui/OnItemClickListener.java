package com.example.adam.myapplication.ui;

import android.view.View;

public interface OnItemClickListener<T> {
    void onItemClick(View view, T object);
}
