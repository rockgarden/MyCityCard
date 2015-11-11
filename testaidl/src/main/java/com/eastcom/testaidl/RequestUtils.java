package com.eastcom.testaidl;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

/**
 * Created by rockgarden on 15/11/10.
 */
public class RequestUtils {
    public static AsyncHttpClient client = new AsyncHttpClient();

    public static void ClientGet(String url, NetCallBack cb) {
        client.get(url, cb);
    }

    public static void ClientPost(String url, RequestParams params,
                                  NetCallBack cb) {
        client.post(url, params, cb);
    }
}