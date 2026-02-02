package com.example.nhohidrata;

import android.content.SharedPreferences;
import android.os.Bundle;
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

        // 🔹 Spinner
        Spinner spinner = findViewById(R.id.spinner_agua);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.qtd_agua,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // 🔹 Botão
        Button btnAdicionar = findViewById(R.id.adicionar_qtd_agua);

        btnAdicionar.setOnClickListener(v -> {

            // Ex: "300 ml"
            String valorSpinner = spinner.getSelectedItem().toString();

            // 🔹 Extrair apenas o número
            int mlSelecionado = Integer.parseInt(
                    valorSpinner.replace("ml", "").trim()
            );

            // 🔹 Buscar total já ingerido
            int totalAtual = prefs.getInt("agua_ingerida", 0);

            // 🔹 Somar
            int novoTotal = totalAtual + mlSelecionado;

            // 🔹 Guardar
            prefs.edit()
                    .putInt("agua_ingerida", novoTotal)
                    .apply();

            Toast.makeText(
                    this,
                    "Ingestão registada: +" + mlSelecionado + " ml\nTotal hoje: " + novoTotal + " ml",
                    Toast.LENGTH_SHORT
            ).show();
        });
    }
}
