package com.example.nhohidrata;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class agua_ingerida extends AppCompatActivity {

    private SharedPreferences prefs;
    private int qtdSelecionada = 0;
    private Button confirmar;
    private MaterialCardView cardDicaLembrete;
    private Button btnRejeitarDica, btnIrParaAlertas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agua_ingerida);

        View root = findViewById(R.id.rootIngestAgua);
        if (root != null) {
            ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        prefs = getSharedPreferences("DadosUsuario", MODE_PRIVATE);


        Button qtd1 = findViewById(R.id.qtd1);
        Button qtd2 = findViewById(R.id.qtd2);
        Button qtd3 = findViewById(R.id.qtd3);
        Button qtd4 = findViewById(R.id.qtd4);
        confirmar = findViewById(R.id.confirmar);

        cardDicaLembrete = findViewById(R.id.cardDicaLembrete);
        btnRejeitarDica = findViewById(R.id.btnRejeitarDica);
        btnIrParaAlertas = findViewById(R.id.btnIrParaAlertas);

        Button voltarPerfil = findViewById(R.id.voltarPerfil);
        voltarPerfil.setOnClickListener(v -> finish());


        View.OnClickListener selecionarQtdListener = v -> {
            qtdSelecionada = Integer.parseInt(v.getTag().toString());
            Toast.makeText(this, "Selecionado: " + qtdSelecionada + " ml", Toast.LENGTH_SHORT).show();
        };

        qtd1.setOnClickListener(selecionarQtdListener);
        qtd2.setOnClickListener(selecionarQtdListener);
        qtd3.setOnClickListener(selecionarQtdListener);
        qtd4.setOnClickListener(selecionarQtdListener);


        confirmar.setOnClickListener(v -> {
            if (qtdSelecionada == 0) {
                Toast.makeText(this, "Escolhe uma quantidade primeiro!", Toast.LENGTH_SHORT).show();
                return;
            }


            int totalAtual = prefs.getInt("agua_ingerida", 0);
            int novoTotal = totalAtual + qtdSelecionada;
            prefs.edit().putInt("agua_ingerida", novoTotal).apply();


            String hora = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
            String dataAtual = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
            String registo = dataAtual + " - " + hora + ": " + qtdSelecionada + " ml";

            Set<String> historico = prefs.getStringSet("historico", new HashSet<>());
            Set<String> novoHistorico = new HashSet<>(historico);
            novoHistorico.add(registo);
            prefs.edit().putStringSet("historico", novoHistorico).apply();

            Toast.makeText(this, "Ingestão registada: +" + qtdSelecionada + " ml", Toast.LENGTH_SHORT).show();


            verificarExibicaoDica();

            qtdSelecionada = 0;
        });


        btnRejeitarDica.setOnClickListener(v -> {
            int totalRejeicoes = prefs.getInt("contador_rejeicoes", 0);
            prefs.edit().putInt("contador_rejeicoes", totalRejeicoes + 1).apply();
            cardDicaLembrete.setVisibility(View.GONE);
        });

        btnIrParaAlertas.setOnClickListener(v -> {
            Intent intent = new Intent(this, NotificacoesActivity.class);
            startActivity(intent);

        });
    }

    private void verificarExibicaoDica() {
        boolean lembreteAtivo = prefs.getBoolean("lembrete_ativo", false);
        int totalRejeicoes = prefs.getInt("contador_rejeicoes", 0);


        if (!lembreteAtivo && totalRejeicoes < 3) {
            cardDicaLembrete.setVisibility(View.VISIBLE);
            cardDicaLembrete.setAlpha(0f);
            cardDicaLembrete.animate().alpha(1f).setDuration(500);
        } else {
            cardDicaLembrete.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean lembreteAtivo = prefs.getBoolean("lembrete_ativo", false);
        int totalRejeicoes = prefs.getInt("contador_rejeicoes", 0);

        if (lembreteAtivo || totalRejeicoes >= 3) {
            cardDicaLembrete.setVisibility(View.GONE);
        }

    }
}