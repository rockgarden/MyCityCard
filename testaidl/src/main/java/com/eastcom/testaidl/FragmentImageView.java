package com.eastcom.testaidl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by rockgarden on 15/11/5.
 */
public class FragmentImageView extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ImageView imageView=new ImageView(getActivity());
        Picasso.with(getActivity())
                .load("http://img.zcool.cn/community/05ef61554ace6300000115a891c243.jpg")
                .into(imageView);
//        Picasso.with(getActivity())
//                .load("http://img.zcool.cn/community/05ef61554ace6300000115a891c243.jpg")
//                .resize(150, 150).into(imageView);
//        Picasso.with(getActivity())
//                .load("http://img.zcool.cn/community/05ef61554ace6300000115a891c243.jpg")
//                .error(R.mipmap.ic_launcher).into(imageView);

        return imageView;
    }
}
