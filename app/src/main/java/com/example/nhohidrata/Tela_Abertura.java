package com.example.nhohidrata;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Tela_Abertura extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_abertura);

        Button btnComecar = findViewById(R.id.btnComecar);
        btnComecar.setOnClickListener(v -> {
            Intent intent = new Intent(this,  MainActivity.class);
            startActivity(intent);
        });
    }
}