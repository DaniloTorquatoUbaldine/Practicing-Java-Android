package com.example.projetofinaldanilo;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

public class Bateria extends AppCompatActivity {

    private MyBatInfoReceiver receiver;

    private TextView charge_percent;
    private TextView charge_time;
    private TextView battery_health;

    public long timeRemaning = 0L;

    private static int pluged;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bateria);

        charge_percent = findViewById(R.id.textView16);
        charge_time = findViewById(R.id.textView17);
        battery_health = findViewById(R.id.textView18);


        Intent intent = new Intent(this, MyIntentService.class);
        intent.putExtra("TELA", "Tela Bateria");
        startService(intent);

        receiver = new MyBatInfoReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(receiver,filter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        receiver = new MyBatInfoReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(receiver,filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        receiver = new MyBatInfoReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onResume(){
        super.onResume();
        receiver = new MyBatInfoReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(receiver, filter);
    }

    class MyBatInfoReceiver extends BroadcastReceiver{
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int deviceHealth = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
            pluged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);

            checkBatteryChargeTime();

            float battery_percent = level * 100 / (float)scale;
            charge_percent.setText(battery_percent + "%");

            if (deviceHealth == BatteryManager.BATTERY_HEALTH_COLD) {
                battery_health.setText("Frio");
            }
            if (deviceHealth == BatteryManager.BATTERY_HEALTH_DEAD) {
                battery_health.setText("Morta");
            }
            if (deviceHealth == BatteryManager.BATTERY_HEALTH_GOOD) {
                battery_health.setText("Boa");
            }
            if (deviceHealth == BatteryManager.BATTERY_HEALTH_OVERHEAT) {
                battery_health.setText("Aquecida");
            }
            if (deviceHealth == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE) {
                battery_health.setText("Voltagem muito alta");
            }
        }
    }

    public void checkBatteryChargeTime() {
        BatteryManager bateryM = (BatteryManager) getApplicationContext().getSystemService(Context.BATTERY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            timeRemaning = bateryM.computeChargeTimeRemaining()/600000; //
            if (pluged == 0) {                                          //
                charge_time.setText("Não está carregando" );
            } else {
                charge_time.setText("Falta(m) " + timeRemaning + " minutos para terminar de carregar" );
            }
        }
    }
}