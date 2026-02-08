package com.example.nhohidrata;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class HistoricoActivity extends AppCompatActivity {

    private Button btnLimpar;
    private ArrayList<String> registros;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        ListView lv = findViewById(R.id.lvHistorico);
        TextView tvVazio = findViewById(R.id.tvHistoricoVazio);
        lv.setEmptyView(tvVazio);// limpar o historico e exibir mensagem
        Button btnVoltar = findViewById(R.id.btnVoltarHist);
        btnVoltar.setOnClickListener(v -> finish());

        SharedPreferences prefs = getSharedPreferences("DadosUsuario", MODE_PRIVATE);
        Set<String> historicoSet = prefs.getStringSet("historico", new HashSet<>());

        registros = new ArrayList<>(historicoSet);

        Collections.sort(registros, Collections.reverseOrder());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                registros
        );

        lv.setAdapter(adapter);

       btnLimpar = findViewById(R.id.btnLimparHist);
       atualizarEstadoBotaoLimpar();

        btnLimpar.setOnClickListener(v -> {
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Limpar Histórico")
                    .setMessage("Tem certeza que deseja apagar todos os registros?")
                    .setPositiveButton("Sim", (dialog, which) -> {
                        prefs.edit().remove("historico").apply();
                        registros.clear();
                        adapter.notifyDataSetChanged();

                        atualizarEstadoBotaoLimpar();

                    })
                    .setNegativeButton("Não", null)
                    .show();
        });

    }

    private void atualizarEstadoBotaoLimpar() {
        if (registros == null || registros.isEmpty()) {
            btnLimpar.setEnabled(false);
            btnLimpar.setAlpha(0.5f);
        } else {
            btnLimpar.setEnabled(true);
            btnLimpar.setAlpha(1.0f);
        }
    }
}
