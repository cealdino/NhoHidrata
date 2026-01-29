package com.example.nhohidrata;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class NotificacoesActivity extends AppCompatActivity {

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacoes);

        spinner = findViewById(R.id.spinnerIntervalo);
        Button btnAtivar = findViewById(R.id.btnLembrar);

        // Criar a lista de opções para o Spinner
        String[] opcoes = {"1 minuto (Teste)", "1 hora", "2 horas", "3 horas"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, opcoes);
        spinner.setAdapter(adapter);

        btnAtivar.setOnClickListener(v -> {
            configurarAlarme();
        });
    }

    private void configurarAlarme() {
        String selecionado = spinner.getSelectedItem().toString();
        long tempoMillis;

        // Definir o tempo baseado na escolha do Spinner
        if (selecionado.contains("1 minuto")) tempoMillis = 60 * 1000;
        else if (selecionado.contains("1 hora")) tempoMillis = 60 * 60 * 1000;
        else if (selecionado.contains("2 horas")) tempoMillis = 2 * 60 * 60 * 1000;
        else tempoMillis = 3 * 60 * 60 * 1000;

        Intent intent = new Intent(this, Alarme.class);
        // FLAG_IMMUTABLE é obrigatória para Android 12+ (incluindo o teu Android 15)
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (alarmManager != null) {
            // Configura o alarme para repetir no intervalo escolhido
            alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + tempoMillis,
                    tempoMillis,
                    pendingIntent
            );

            Toast.makeText(this, "Lembrete definido: " + selecionado, Toast.LENGTH_LONG).show();
        }
    }
}