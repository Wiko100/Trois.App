package com.example.troisapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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

//        prodPict = findViewById(R.id.details_prod_pict);
        prodName = findViewById(R.id.details_title_prod);
        prodDesc = findViewById(R.id.details_prod_desc);
        prodPrice = findViewById(R.id.details_prod_price);

        Bundle data = getIntent().getExtras();
        GetSetProduct club = data.getParcelable("product");

//        Picasso.Builder builder = new Picasso.Builder(this);
//        builder.build().load(club.getPicture())
//                .placeholder(R.drawable.ic_launcher_background)
//                .error(R.drawable.ic_launcher_foreground)
//                .into(prodPict);
        prodName.setText(club.getProduct());
        prodDesc.setText(club.getDescription());
        prodPrice.setText(club.getPrice());

    }
}