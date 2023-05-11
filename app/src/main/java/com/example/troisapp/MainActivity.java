package com.example.troisapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.i("TEST_THREAD", "START");
                synchronized (this){
                    try {
                        wait(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("TEST_THREAD", "START");
                        Intent intent = new Intent(MainActivity.this, WelcomeScreen.class);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "Enter", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }
}