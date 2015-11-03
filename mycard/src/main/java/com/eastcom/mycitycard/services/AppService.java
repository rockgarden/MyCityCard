package com.eastcom.mycitycard.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.eastcom.mycitycard.IMyAidlInterface;
import com.litesuits.android.log.Log;

public class AppService extends Service {

    private String data="card key";
    private boolean running=false;

    public AppService() {
        Log.setTag("MyPay");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.

        return new IMyAidlInterface.Stub() {
            /**
             * Demonstrates some basic types that you can use as parameters
             * and return values in AIDL.
             *
             * @param anInt
             * @param aLong
             * @param aBoolean
             * @param aFloat
             * @param aDouble
             * @param aString
             */
            @Override
            public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

            }

            @Override
            public void setData(String data) throws RemoteException {
                AppService.this.data=data;
            }

        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("AppService onStartCommand");
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
        Log.i("AppService onCreate");
        new Thread(){
            @Override
            public void run(){
                super.run();
                running=true;
                while (running){
                    Log.i(data);
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("AppService onDestroy");
        running=false;
    }

}
