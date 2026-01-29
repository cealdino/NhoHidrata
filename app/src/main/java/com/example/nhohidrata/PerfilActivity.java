package com.example.nhohidrata;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PerfilActivity extends AppCompatActivity {

    private EditText etNome, etPeso, etIdade;
    private SharedPreferences prefs; // Faltava declarar isto aqui!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        etNome = findViewById(R.id.etNome);
        etPeso = findViewById(R.id.etPeso);
        etIdade = findViewById(R.id.etIdade);
        Button btnGuardar = findViewById(R.id.btnGuardar);
        Button btnVoltar = findViewById(R.id.btnVoltar);

        prefs = getSharedPreferences("DadosUsuario", MODE_PRIVATE);

        // Carregar dados salvos
        etNome.setText(prefs.getString("nome", ""));
        float pesoSalvo = prefs.getFloat("peso", 0);
        if (pesoSalvo > 0) etPeso.setText(String.valueOf(pesoSalvo));
        int idadeSalva = prefs.getInt("idade", 0);
        if (idadeSalva > 0) etIdade.setText(String.valueOf(idadeSalva));

        btnGuardar.setOnClickListener(v -> {
            String pesoStr = etPeso.getText().toString();
            String idadeStr = etIdade.getText().toString();
            String nome = etNome.getText().toString();

            if (!pesoStr.isEmpty() && !idadeStr.isEmpty()) {
                float peso = Float.parseFloat(pesoStr);
                int idade = Integer.parseInt(idadeStr);
                int mlPorQuilo;

                if (idade <= 30) mlPorQuilo = 40;
                else if (idade <= 55) mlPorQuilo = 35;
                else if (idade <= 65) mlPorQuilo = 30;
                else mlPorQuilo = 25;

                int metaCalculada = (int) (peso * mlPorQuilo);

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("nome", nome);
                editor.putFloat("peso", peso);
                editor.putInt("idade", idade);
                editor.putInt("meta", metaCalculada);
                editor.apply();

                Toast.makeText(this, "Meta definida: " + metaCalculada + "ml", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Preencha o peso e a idade!", Toast.LENGTH_SHORT).show();
            }
        });

        btnVoltar.setOnClickListener(v -> finish());
    }
} // Agora fechou corretamente!}