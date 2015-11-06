package com.eastcom.mycitycard.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.litesuits.android.log.Log;

import java.util.Random;

public class MyBindService extends Service {
    public MyBindService() {
    }

    // 1在Service中创建一个Binder子类
    public class MyBinder extends Binder {
        // 2创建公共方法返回当前Service实例
        public MyBindService getService(){
            return MyBindService.this;
        }
    }

    // 3返回一个Binder实例
    private MyBinder mBinder = new MyBinder();
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

    // 4在Service中创建自定义的业务公共方法
    public int getRandom() {
        Random rd = new Random();
        return rd.nextInt(100);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.i("Service is destoried");
    }
}
