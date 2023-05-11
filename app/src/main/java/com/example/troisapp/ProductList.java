package com.example.troisapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ProductList extends AppCompatActivity implements ProductAdapter.ListItemClickListener{
    private RecyclerView rvProd;
    private List<GetSetProduct> prodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        String description = "$0.0";
        rvProd = findViewById(R.id.rv_prod);

        prodList = new ArrayList<>();

        prodList.add(new GetSetProduct(" ", "Fußball-Bundesliga", "deskripsi produk", "$0.0"));
        prodList.add(new GetSetProduct(" ", "La Liga", "deskripsi produk", "$0.0"));
        prodList.add(new GetSetProduct(" ", "Ligue 1", "deskripsi produk", "$0.0"));
        prodList.add(new GetSetProduct(" ", "English Premier League", "deskripsi produk", "$0.0"));

        ProductAdapter productAdapter = new ProductAdapter(this, prodList);
        productAdapter.setListener(this);
        rvProd.setAdapter(productAdapter);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        rvProd.setLayoutManager(layoutManager);
    }

    @Override
    public void onListItemClick(View v, int position) {
        Toast.makeText(getApplicationContext(), "You Click On "+position, Toast.LENGTH_SHORT).show();

        GetSetProduct selectedItems = prodList.get(position);
        Intent intent = new Intent(this, ProductDetails.class);
        intent.putExtra("product", selectedItems);
        startActivity(intent);
    }
}