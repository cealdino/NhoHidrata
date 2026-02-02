package com.example.nhohidrata;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private TextView metaParaAtingir;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        metaParaAtingir = findViewById(R.id.metaParaAtingir);
        Button btnBeberAgua = findViewById(R.id.btnBeberAgua);
        btnBeberAgua.setOnClickListener(v -> {
            Intent intent = new Intent(this,  PerfilActivities.class);
            startActivity(intent);

        });
    }
    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs =
                getSharedPreferences("DadosUsuario", MODE_PRIVATE);

        int meta = prefs.getInt("meta", 0);

        if (meta > 0) {
            metaParaAtingir.setText(meta + " ml por dia");
            metaParaAtingir.setTextSize(14);



        }
    }
}