package com.example.nhohidrata;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvProgresso;
    private int totalIngerido = 0;
    private int metaDiaria;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvProgresso = findViewById(R.id.tvProgresso);
        Button btn500 = findViewById(R.id.btn500);
        Button btn1L = findViewById(R.id.btn1L);
        Button btn15L = findViewById(R.id.btn15L);
        Button btnBeber = findViewById(R.id.btnBeberAgua);

        prefs = getSharedPreferences("DadosUsuario", MODE_PRIVATE);

        // 1. Carregar a meta calculada no Perfil (ou 2000 como padrão)
        metaDiaria = prefs.getInt("meta", 2000);
        atualizarTextoProgresso();

        // 2. Lógica dos botões de quantidade (conforme o teu caderno)
        btn500.setOnClickListener(v -> adicionarAgua(500));
        btn1L.setOnClickListener(v -> adicionarAgua(1000));
        btn15L.setOnClickListener(v -> adicionarAgua(1500));

        // Botão Beber padrão (podes definir quanto ele soma, ex: 250ml)
        btnBeber.setOnClickListener(v -> adicionarAgua(250));

        // 3. Navegação para as outras telas
        findViewById(R.id.btnPerfil).setOnClickListener(v ->
                startActivity(new Intent(this, PerfilActivity.class)));

        findViewById(R.id.btnNotificacoes).setOnClickListener(v ->
                startActivity(new Intent(this, NotificacoesActivity.class)));
    }

    private void adicionarAgua(int quantidade) {
        totalIngerido += quantidade;
        atualizarTextoProgresso();
        Toast.makeText(this, "Boa! +" + quantidade + "ml", Toast.LENGTH_SHORT).show();
    }

    private void atualizarTextoProgresso() {
        tvProgresso.setText(totalIngerido + " / " + metaDiaria + " ml");
    }

    @Override
    protected void onResume() {
        super.onResume(); // O comando correto é super.onResume()
        // Atualiza a meta caso tenhas acabado de vir do Perfil
        metaDiaria = prefs.getInt("meta", 2000);
        atualizarTextoProgresso();
    }
    }
