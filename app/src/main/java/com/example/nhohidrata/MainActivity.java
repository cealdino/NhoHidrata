package com.example.nhohidrata;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private TextView metaParaAtingir, aguaIngerida;
    private CircularProgressIndicator progressHidratacao;


    private boolean modoTeste = true;
    private int horaSimulada = 8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        metaParaAtingir = findViewById(R.id.metaParaAtingir);
        aguaIngerida = findViewById(R.id.aguaIngerida);
        progressHidratacao = findViewById(R.id.metaAtingida);

        Button btnBeberAgua = findViewById(R.id.btnBeberAgua);
        Button voltarInicio = findViewById(R.id.voltarInicio);


        Button btnSimular = findViewById(R.id.btnSimularHora);
        btnSimular.setOnClickListener(v -> {
            if (modoTeste) {
                horaSimulada += 2; // Incrementa de 2 em 2 horas
                if (horaSimulada >= 24) horaSimulada = 0;

                Toast.makeText(this, "Simulando: " + horaSimulada + ":00", Toast.LENGTH_SHORT).show();
                horaDodia(); // Executa a lógica com o novo valor
            }
        });

        voltarInicio.setOnClickListener(v -> finish());

        MaterialCardView cardPerfil = findViewById(R.id.cardPerfil);
        MaterialCardView cardHistorico = findViewById(R.id.cardHistorico);
        MaterialCardView cardAlerta = findViewById(R.id.cardAlertas);

        btnBeberAgua.setOnClickListener(v -> {
            Intent intent = new Intent(this, agua_ingerida.class);
            startActivity(intent);
        });

        cardPerfil.setOnClickListener(v ->
                startActivity(new Intent(this, PerfilActivities.class)));

        cardHistorico.setOnClickListener(v ->
                startActivity(new Intent(this, HistoricoActivity.class)));

        cardAlerta.setOnClickListener(v ->
                startActivity(new Intent(this, NotificacoesActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizarTela();
        horaDodia();
    }

    private void horaDodia() {
        int horaParaOCalculo;

        if (modoTeste) {
            horaParaOCalculo = horaSimulada;
        } else {

            Calendar agora = Calendar.getInstance();
            horaParaOCalculo = agora.get(Calendar.HOUR_OF_DAY);
        }

        if (horaParaOCalculo == 0) {
            fecharCicloDoDia();
        } else {
            verificarEstadoHidratacao(horaParaOCalculo);
        }
    }

    private void fecharCicloDoDia() {
        SharedPreferences prefs = getSharedPreferences("DadosUsuario", MODE_PRIVATE);
        int meta = prefs.getInt("meta", 0);
        int ingerido = prefs.getInt("agua_ingerida", 0);

        if (ingerido >= meta && meta > 0) {
            Toast.makeText(this, "🎉 Parabéns! Meta atingida hoje!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "💙 Não atingiste a meta, amanhã é um novo dia!", Toast.LENGTH_LONG).show();
        }

        prefs.edit().putInt("agua_ingerida", 0).apply();
        atualizarTela();
    }

    private void verificarEstadoHidratacao(int hora) {
        SharedPreferences prefs = getSharedPreferences("DadosUsuario", MODE_PRIVATE);
        int meta = prefs.getInt("meta", 0);
        int ingerido = prefs.getInt("agua_ingerida", 0);

        if (meta == 0) return;

        int percentagem = (ingerido * 100) / meta;


        if (percentagem < 30) {
            if (hora >= 18) {
                Toast.makeText(this, "Já é tarde e bebeste pouca água", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Hidrataste pouco até agora", Toast.LENGTH_SHORT).show();
            }

        } else if (percentagem < 60) {
            if (hora >= 20) {
                Toast.makeText(this, "Já é noite, tenta beber mais água", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, " Bom ritmo! Continua!", Toast.LENGTH_SHORT).show();
            }

        } else if (percentagem < 100) {
            Toast.makeText(this, "Estás quase lá!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "🎉 Meta atingida!", Toast.LENGTH_LONG).show();
        }
    }

    private void atualizarTela() {
        SharedPreferences prefs = getSharedPreferences("DadosUsuario", MODE_PRIVATE);
        int meta = prefs.getInt("meta", 0);
        int ingerido = prefs.getInt("agua_ingerida", 0);

        metaParaAtingir.setText(meta + " ml por dia");
        aguaIngerida.setText(ingerido + " ml ingeridos");

        if (meta > 0) {
            int progresso = (ingerido * 100) / meta;
            progressHidratacao.setProgress(progresso);

            if (progresso < 50)
                progressHidratacao.setIndicatorColor(0xFF4FC3F7);
            else if (progresso < 100)
                progressHidratacao.setIndicatorColor(0xFF0288D1);
            else
                progressHidratacao.setIndicatorColor(0xFF2E7D32);
        } else {
            progressHidratacao.setProgress(0);
        }
    }
}




