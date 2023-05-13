package com.example.troisapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

public class ProductDetails extends AppCompatActivity {
    private ImageView prodPict;
    private TextView prodName;
    private TextView prodDesc;
    private TextView prodPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        prodPict = findViewById(R.id.details_prod_pict);
        prodName = findViewById(R.id.details_title_prod);
        prodDesc = findViewById(R.id.details_prod_desc);
        prodPrice = findViewById(R.id.details_prod_price);

        Bundle data = getIntent().getExtras();
        GetSetProduct product = data.getParcelable("product");

        Glide.with(ProductDetails.this)
                .load(data.getString("prodPict"))
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(prodPict);
        prodName.setText(data.getString("prodTitle"));
        prodDesc.setText(data.getString("prodDesc"));
        prodPrice.setText(data.getString("prodPrice"));

    }
}