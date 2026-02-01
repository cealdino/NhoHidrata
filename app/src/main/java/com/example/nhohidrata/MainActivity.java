package com.example.nhohidrata;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnBeberAgua = findViewById(R.id.btnBeberAgua);
        btnBeberAgua.setOnClickListener(v -> {
            Intent intent = new Intent(this,  PerfilActivities.class);
            startActivity(intent);

        });
    }
}