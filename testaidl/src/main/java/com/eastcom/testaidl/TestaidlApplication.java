package com.eastcom.testaidl;

import android.app.Application;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by rockgarden on 15/11/10.
 */
public class TestaidlApplication extends Application {
    public static RequestQueue queue;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        queue = Volley.newRequestQueue(getApplicationContext());
    }

    public static RequestQueue getHttpQueue() {
        return queue;
    }

}
