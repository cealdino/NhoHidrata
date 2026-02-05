package com.example.nhohidrata;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class HistoricoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        ListView lv = findViewById(R.id.lvHistorico);
        Button btnVoltar = findViewById(R.id.btnVoltarHist);

        SharedPreferences prefs = getSharedPreferences("DadosUsuario", MODE_PRIVATE);
        Set<String> historicoSet = prefs.getStringSet("historico", new HashSet<>());

        ArrayList<String> registros = new ArrayList<>(historicoSet);

        // opcional: mais recente em cima
        Collections.sort(registros, Collections.reverseOrder());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                registros
        );

        lv.setAdapter(adapter);

        btnVoltar.setOnClickListener(v -> finish());
    }
}
