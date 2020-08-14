package com.altimetrik.itunes_search.view.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.altimetrik.itunes_search.R;
import com.altimetrik.itunes_search.base.BaseActivity;
import com.altimetrik.itunes_search.view.adapter.CartRecyclerViewAdapter;

public class CartActivty extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        initView();

    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        createList();
    }

    private void createList() {


        // set adapter
        CartRecyclerViewAdapter adapter = new CartRecyclerViewAdapter(this, BaseActivity.cartTracks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

}