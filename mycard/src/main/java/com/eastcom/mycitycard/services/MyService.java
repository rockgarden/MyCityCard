package com.eastcom.mycitycard.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.litesuits.android.log.Log;

public class MyService extends Service {
    private boolean running=false;
    private String data="card key";
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new Binder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("MyService onStartCommand");
        new Thread(){
            @Override
            public void run(){
                super.run();
                running=true;
                while (running){
                    Log.i(data);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("MyService onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("MyService onDestroy");
        running=false;
    }
}
