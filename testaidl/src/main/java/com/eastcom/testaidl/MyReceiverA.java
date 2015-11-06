package com.eastcom.testaidl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiverA extends BroadcastReceiver {
    public MyReceiverA() {
    }

    public static final String ACTION="com.eastcom.testaidl.intent.action.MyReceiverA";

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("receiveA-receive message:"+intent.getStringExtra("code"));
    }
}
