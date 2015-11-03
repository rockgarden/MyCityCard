package com.eastcom.mycitycard;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.nfc.tech.IsoDep;
import android.os.Handler;
import android.os.Message;

/**
 * Created by rockgarden on 15/11/2.
 */
public class MycardApplication extends Application {
    private String textData ="default";

    //public static LinkedList<ActivityBase> activityList = new LinkedList<ActivityBase>();
    // public static String AID = "A0000005980000051200000000000001"; //
    // 给个默认的AID用作非接圈存时候用
    public static String AID = "A00000059807560001";
    public static String READAID = "";
    // public static String READAID = "4144442E41505032";
    // public static String READAID = "315041592E5359532E4444463031";

    public static boolean isLogin = false;
    private IsoDep isoDep;
    private String testValue;

    public static boolean channelOnUse = true;

    // 正在进行请求标志位
    public static boolean isRequestOnWay = false;

    // @date 2015-08-20
    // 处理进度框显示
    public static Handler progressHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //ProgressFragment.showMsg((String) msg.obj);
        }
    };

    public String getTestValue() {
        return testValue;
    }

    public void setTestValue(String testValue) {
        this.testValue = testValue;
    }

    public IsoDep getIsoDep() {
        return isoDep;
    }

    public void setIsoDep(IsoDep isoDep) {
        this.isoDep = isoDep;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Thread.setDefaultUncaughtExceptionHandler((Thread.UncaughtExceptionHandler) this);
    }

//    public static void exit() {
//        for (ActivityBase activity : activityList) {
//            activity.finish();
//        }
//        activityList.clear();
//        System.exit(0);
//    }
//
//    public static void clearUserData(Context context) {
//        CacheHelper cacheHelper = CacheHelper.getInstance(context);
//        cacheHelper.clear();
//    }
//
//    @Override
//    public void uncaughtException(Thread thread, Throwable ex) {
//        ZLog log = ZLog.getInstance();
//        ex.printStackTrace();
//        log.writeLog();
//        System.exit(0);
//    }

    public void setTextData(String textData) {
        this.textData = textData;
    }

    public String getTextData() {
        return textData;
    }

    @Override
    public ApplicationInfo getApplicationInfo() {
        return super.getApplicationInfo();
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
