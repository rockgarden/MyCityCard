package com.eastcom.testaidl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiverB extends BroadcastReceiver {
    public MyReceiverB() {
    }

    public static final String ACTION="com.eastcom.testaidl.intent.action.MyReceiverB";

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("receiveB-receive message:"+intent.getStringExtra("code"));
        abortBroadcast();
    }
}
