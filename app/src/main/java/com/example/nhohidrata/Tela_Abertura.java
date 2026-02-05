package com.example.nhohidrata;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

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

        TextView txtCuriosidade = findViewById(R.id.txtCuriosidade);
        String[] curiosidades = {
                " Água em jejum acelera seu metabolismo em 30% logo nos primeiros minutos!",
                " Hidratação é o lubrificante natural das suas articulações. Evite dores e lesões!",
                " Quer queimar calorias? A água ajuda a transformar gordura em energia pura!",
                " Cansado? Antes do café, beba água! A fadiga é o primeiro sinal de sede.",

        };

        int indice = new Random().nextInt(curiosidades.length);
        txtCuriosidade.setText(curiosidades[indice]);

        txtCuriosidade.setSelected(true);

    }
}