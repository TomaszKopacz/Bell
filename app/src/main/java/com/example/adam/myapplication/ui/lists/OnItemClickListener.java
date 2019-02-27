package com.example.adam.myapplication.ui.lists;

import android.view.View;

public interface OnItemClickListener<T> {
    void onItemClick(View view, T object);
}
