package com.example.nhohidrata;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class agua_ingerida extends AppCompatActivity {

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agua_ingerida);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.rootIngestAgua),
                (v, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                    return insets;
                }
        );

        // 🔹 SharedPreferences
        prefs = getSharedPreferences("DadosUsuario", MODE_PRIVATE);
        Button voltarPerfil = findViewById(R.id.voltarPerfil);
        voltarPerfil.setOnClickListener(v -> finish());

        Button qtd1 = findViewById(R.id.qtd1);
        Button qtd2 = findViewById(R.id.qtd2);
        Button qtd3 = findViewById(R.id.qtd3);
        Button qtd4 = findViewById(R.id.qtd4);

        View.OnClickListener listener = v -> {
            int qtdIngerida = Integer.parseInt(v.getTag().toString());
            int totalAtual = prefs.getInt("agua_ingerida", 0);
            int novoTotal = totalAtual + qtdIngerida;
            prefs.edit()
                    .putInt("agua_ingerida", novoTotal)
                    .apply();

            String hora = new SimpleDateFormat("HH:mm", Locale.getDefault())
                    .format(new Date());


            String registo = "Hoje - " + hora + ": " + qtdIngerida + " ml";

            Set<String> historico = prefs.getStringSet("historico", new HashSet<>());
            Set<String> novoHistorico = new HashSet<>(historico);
            novoHistorico.add(registo);

            prefs.edit()
                    .putStringSet("historico", novoHistorico)
                    .apply();

            Toast.makeText(
                    this,
                    "Ingestão registada: +" +qtdIngerida + " ml\nTotal hoje: " + novoTotal + " ml",
                    Toast.LENGTH_SHORT
            ).show();


        };

        qtd1.setOnClickListener(listener);
        qtd2.setOnClickListener(listener);
        qtd3.setOnClickListener(listener);
        qtd4.setOnClickListener(listener);

    }
}
