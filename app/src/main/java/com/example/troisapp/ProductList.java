package com.example.troisapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProductList extends AppCompatActivity implements ProductAdapter.ListItemClickListener{
    private RecyclerView rvProd;
    ProductAdapter productAdapter;

    @Override
    protected void onStart() {
        super.onStart();
        productAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        productAdapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
//        String type;

        Category category = new Category();
//        type = category.getType();

        rvProd = findViewById(R.id.rv_prod);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        rvProd.setLayoutManager(layoutManager);

            FirebaseRecyclerOptions<GetSetProduct> options =
                    new FirebaseRecyclerOptions.Builder<GetSetProduct>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("Produk/Clothing"), GetSetProduct.class)
                            .build();

//            Toast.makeText(ProductList.this, category.getType(), Toast.LENGTH_SHORT).show();

            productAdapter = new ProductAdapter(options);
            productAdapter.setListener(this);
            rvProd.setAdapter(productAdapter);
    }

    @Override
    public void onListItemClick(View v, int position) {
        Toast.makeText(getApplicationContext(), "You Click On "+position, Toast.LENGTH_SHORT).show();

//        GetSetProduct selectedItems = prodList.get(position);
//        Intent intent = new Intent(this, ProductDetails.class);
//        intent.putExtra("product", selectedItems);
//        startActivity(intent);
    }
}