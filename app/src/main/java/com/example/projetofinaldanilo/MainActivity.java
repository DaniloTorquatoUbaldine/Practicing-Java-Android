package com.example.projetofinaldanilo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private MyBroadcastReceiver receiver;


    @SuppressLint("StaticFieldLeak")
    private static TextView wifi_status;
    private static String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button Apps = findViewById(R.id.button);
        Button Bateria = findViewById(R.id.button2);

        Intent intent = new Intent(this, MyIntentService.class);
        intent.putExtra("TELA", "Tela Home");
        startService(intent);

        wifi_status = findViewById(R.id.textView20);

        Apps.setOnClickListener(v->{
            Intent i = new Intent(this, Apps.class);
            startActivity(i);
        });

        Bateria.setOnClickListener(v->{
            Intent i = new Intent(this, Bateria.class);
            startActivity(i);
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        receiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(receiver,filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        receiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(receiver, filter);
    }

    class MyBroadcastReceiver extends BroadcastReceiver {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {
            if (checkWifiOnAndConnected()) {
                status = "Habilitado";
                Log.d("DTU", "O Status do Wi-Fi Mudou. Agora, está: " + status);
            } else {
                status = "Desabilitado";
                Log.d("DTU", "O Status do Wi-Fi Mudou. Agora, está: " + status);
            }
            wifi_status.setText("O Wi-Fi está " + status);
        }
    }

    public boolean checkWifiOnAndConnected() {
        WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiMgr.isWifiEnabled()) {
            return true;
        } else {
            return false;
        }
    }
}