package com.example.nhohidrata;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Aqui é onde a "mágica" acontece quando o tempo acaba
        Toast.makeText(context, "HORA DE BEBER ÁGUA! 💧", Toast.LENGTH_LONG).show();
    }
}
