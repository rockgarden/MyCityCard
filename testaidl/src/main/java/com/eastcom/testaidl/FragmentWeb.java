package com.eastcom.testaidl;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


public class FragmentWeb extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_web, container, false);
        WebView webView= (WebView) root.findViewById(R.id.webViewFragment);
        webView.loadUrl("http://www.baidu.com");
        return root;
    }

}
