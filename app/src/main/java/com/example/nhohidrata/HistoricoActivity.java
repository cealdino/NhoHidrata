package com.example.nhohidrata;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class HistoricoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        ListView lv = findViewById(R.id.lvHistorico);
        Button btnVoltar = findViewById(R.id.btnVoltarHist);

        // Criar uma lista de exemplo
        ArrayList<String> registros = new ArrayList<>();
        registros.add("Hoje - 08:30: 250ml");
        registros.add("Hoje - 10:15: 300ml");
        registros.add("Hoje - 12:00: 500ml");
        registros.add("Ontem - Total: 2100ml");

        // O Adapter é o que "cola" os textos na ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                registros
        );

        lv.setAdapter(adapter);

        // Fechar a tela e voltar para a Main
        btnVoltar.setOnClickListener(v -> finish());
    }
}