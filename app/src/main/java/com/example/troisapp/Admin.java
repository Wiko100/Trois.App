package com.example.troisapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Admin extends AppCompatActivity implements View.OnClickListener{
    Button addStaff;
    Button addStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        addStaff = findViewById(R.id.button_add_staff);
        addStock = findViewById(R.id.button_add_stock);

        addStaff.setOnClickListener(this);
        addStock.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_add_staff){
            Intent intent = new Intent(Admin.this, AddStaff.class);
            startActivity(intent);
        }else if(view.getId() == R.id.button_add_stock){
            Intent intent = new Intent(Admin.this, AddStock.class);
            startActivity(intent);
        }
    }
}