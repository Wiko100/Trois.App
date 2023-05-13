package com.example.troisapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductList extends AppCompatActivity implements ProductAdapter.ListItemClickListener{
    private RecyclerView rvProd;
    private TextView titleProductList, productName;
    private String type;
    private List<GetSetProduct> productClicked;
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
        String title;

        rvProd = findViewById(R.id.rv_prod);
        titleProductList = findViewById(R.id.title_prod_list);

        Bundle data = getIntent().getExtras();
        type = data.getString("category");
        title = data.getString("title");
        titleProductList.setText(title);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        rvProd.setLayoutManager(layoutManager);

        FirebaseRecyclerOptions<GetSetProduct> options =
                new FirebaseRecyclerOptions.Builder<GetSetProduct>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child(type), GetSetProduct.class)
                        .build();

        productAdapter = new ProductAdapter(options, ProductList.this);
        productAdapter.setListener(this);
        rvProd.setAdapter(productAdapter);
    }

    @Override
    public void onListItemClick(View v, int position) {
        String getPosition = Integer.toString(position+1);
        String product;

        productName = findViewById(R.id.list_prod_name);
        product = productName.getText().toString();

        productClicked = new ArrayList<>();
        product = productName.getText().toString();
        Toast.makeText(getApplicationContext(), "You Click On "+type+"/"+getPosition+" "+product, Toast.LENGTH_SHORT).show();
    }
}