package com.example.troisapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeScreen extends AppCompatActivity implements View.OnClickListener{
    Button newUser;
    Button member;
    TextView admin;
    TextView staff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        newUser = findViewById(R.id.button_new_user);
        member = findViewById(R.id.button_member);
        admin = findViewById(R.id.admin);
        staff = findViewById(R.id.staff);

        newUser.setOnClickListener(this);
        member.setOnClickListener(this);
        admin.setOnClickListener(this);
        staff.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_new_user){
            Intent intent = new Intent(WelcomeScreen.this, Register.class);
            startActivity(intent);
        }else if(view.getId() == R.id.button_member){
            Intent intent = new Intent(WelcomeScreen.this, Login.class);
            startActivity(intent);
        }else if(view.getId() == R.id.admin){
            Intent intent = new Intent(WelcomeScreen.this, LoginAdmin.class);
            startActivity(intent);
        }else if(view.getId() == R.id.staff){
            Intent intent = new Intent(WelcomeScreen.this, Staff.class);
            startActivity(intent);
        }
    }
}