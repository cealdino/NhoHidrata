package com.example.nhohidrata;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;



public class PerfilActivities extends AppCompatActivity {
    private static final int COR_BASE = 0xFFE3F2FD; //controlo cor do card
    private TextView txtPesado; // controlo das cores de texto
    private EditText etPeso, etIdade;
    private TextView infoQtdDiaria;
    private SharedPreferences prefs;

    private String climaSelecionado = "";
    private String atividadeSelecionada = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_activities);

        txtPesado = findViewById(R.id.txtPesado);
        etPeso = findViewById(R.id.meuPeso);
        etIdade = findViewById(R.id.minhaIdade);
        infoQtdDiaria = findViewById(R.id.infoMeta);
        Button btnGuardar = findViewById(R.id.gravarQtdAgua);
        Button btnVoltar = findViewById(R.id.btnVoltar);


        MaterialCardView cardTempFrio = findViewById(R.id.cardFrio);
        MaterialCardView cardTempMod = findViewById(R.id.cardModerado);
        MaterialCardView cardTempQuente = findViewById(R.id.cardQuente);

        MaterialCardView cardSedentario = findViewById(R.id.cardParado);
        MaterialCardView cardLeve = findViewById(R.id.cardAtLeve);
        MaterialCardView cardPesado = findViewById(R.id.cardAtivo);

        prefs = getSharedPreferences("DadosUsuario", MODE_PRIVATE);


        cardTempFrio.setOnClickListener(v -> {
            climaSelecionado = "FRIO";
            resetClima(cardTempFrio, cardTempMod, cardTempQuente);
            cardTempFrio.setCardBackgroundColor(0xFFBBDEFB);
        });

        cardTempMod.setOnClickListener(v -> {
            climaSelecionado = "MODERADO";
            resetClima(cardTempFrio, cardTempMod, cardTempQuente);
            cardTempMod.setCardBackgroundColor(0xFF64B5F6);
        });

        cardTempQuente.setOnClickListener(v -> {
            climaSelecionado = "QUENTE";
            resetClima(cardTempFrio, cardTempMod, cardTempQuente);
            cardTempQuente.setCardBackgroundColor(0xFFFFCC80);
        });


        cardSedentario.setOnClickListener(v -> {
            atividadeSelecionada = "SEDENTARIO";
            resetAtividade(cardSedentario, cardLeve, cardPesado);
            cardSedentario.setCardBackgroundColor(0xFFBBDEFB);
        });

        cardLeve.setOnClickListener(v -> {
            atividadeSelecionada = "LEVE";
            resetAtividade(cardSedentario, cardLeve, cardPesado);
            cardLeve.setCardBackgroundColor(0xFF64B5F6);
        });

        cardPesado.setOnClickListener(v -> {
            atividadeSelecionada = "INTENSA";
            resetAtividade(cardSedentario, cardLeve, cardPesado);
            cardPesado.setCardBackgroundColor(0xFF1976D2);
            txtPesado.setTextColor(0xFFFFFFFF);
        });

        btnGuardar.setOnClickListener(v -> {

            if (etPeso.getText().toString().isEmpty()
                    || etIdade.getText().toString().isEmpty()
                    || climaSelecionado.isEmpty()
                    || atividadeSelecionada.isEmpty()) {

                Toast.makeText(this,
                        "Por favor preencha todos os dados: peso, idade, Clima e atividade Fisica",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            float peso = Float.parseFloat(etPeso.getText().toString());
            int idade = Integer.parseInt(etIdade.getText().toString());

            int mlPorQuilo;
            if (idade <= 30) mlPorQuilo = 40;
            else if (idade <= 55) mlPorQuilo = 35;
            else if (idade <= 65) mlPorQuilo = 30;
            else mlPorQuilo = 25;

            int meta = (int) (peso * mlPorQuilo);


            if (climaSelecionado.equals("QUENTE")) meta += 500;
            else if (climaSelecionado.equals("FRIO")) meta -= 200;

            if (atividadeSelecionada.equals("LEVE")) meta += 300;
            else if (atividadeSelecionada.equals("INTENSA")) meta += 700;

            prefs.edit().putInt("meta", meta).apply();

            infoQtdDiaria.setText( "Meta ajustada automaticamente "+ meta + " ml por dia");
        });

        btnVoltar.setOnClickListener(v -> finish());
    }

    private void resetClima(MaterialCardView... cards) {
        for (MaterialCardView c : cards)
            c.setCardBackgroundColor(COR_BASE);
    }

    private void resetAtividade(MaterialCardView... cards) {
        for (MaterialCardView card : cards) {
            card.setCardBackgroundColor(COR_BASE);
        }

        if (txtPesado != null) {
            txtPesado.setTextColor(0xFF0288D1);
        }
    }
}
