package com.example.projetofinaldanilo;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.IntentService;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;


public class MyIntentService extends IntentService {
    static String name;

    public MyIntentService() {
        super("MyIntentService");
    }

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null){
            ActivityManager activityManager = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
            if(activityManager != null) {
                List<ActivityManager.AppTask> tasks = activityManager.getAppTasks();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    name = tasks.get(0).getTaskInfo().topActivity.getClassName();
                } else {
                    name = activityManager.getRunningTasks(1).get(0).topActivity.getClassName(); //Deprecated
                }

                Toast.makeText(this, "O nome da tela atual é: \n" + name, Toast.LENGTH_LONG).show();
                Log.d("FASM","A tela principal, no momento, é: " + name);
            }
        }
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("DTU","Service destruído");
    }

}