package com.eastcom.mycitycard.fragments;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.eastcom.mycitycard.R;
import com.litesuits.android.log.Log;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoadingActivityFragment extends Fragment {

    private ImageLoader loader;
    private ImageView iv_img;

    public LoadingActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_loading, container, false);
        iv_img = (ImageView) root.findViewById(R.id.iv_img);
        loadImage();
        return root;
    }

    private void loadImage() {
        loader = ImageLoader.getInstance();

        String uri = "file:///" + "/myCard/imgCache";
        loader.displayImage(
                "http://img.zcool.cn/community/05ef61554ace6300000115a891c243.jpg",
                iv_img, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String arg0, View arg1) {
                        Log.i("info", "onLoadingStarted");
                    }
                    @Override
                    public void onLoadingFailed(String arg0, View arg1,
                                                FailReason arg2) {
                        Log.i("info", "onLoadingFailed");
                    }
                    @Override
                    public void onLoadingComplete(String arg0, View arg1,
                                                  Bitmap arg2) {
                        Log.i("info", "onLoadingComplete");
                    }
                    @Override
                    public void onLoadingCancelled(String arg0, View arg1) {
                        Log.i("info", "onLoadingCancelled");
                    }
                });
    }

}
