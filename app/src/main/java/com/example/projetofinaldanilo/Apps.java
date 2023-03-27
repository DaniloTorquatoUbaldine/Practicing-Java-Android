package com.example.projetofinaldanilo;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Apps extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);

        PackageManager packageManager=getPackageManager();
        @SuppressLint("QueryPermissionsNeeded")
        List<ApplicationInfo> list = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        //TODO: Monitorar a tela em primeiro plano
        Intent intent = new Intent(this, MyIntentService.class);
        intent.putExtra("TELA", "Tela Apps");
        startService(intent);

        List<String> values = new ArrayList<>(0);

        for(ApplicationInfo ap:list){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (ap.enabled){
                    values.add(ap.packageName + " - está Habilitado");

                } else {
                    values.add(ap.packageName + " - está Desabilitado");
                }
            }
        }
        ListView lista = findViewById(R.id.listadeapps);
        lista.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values));
    }
}

