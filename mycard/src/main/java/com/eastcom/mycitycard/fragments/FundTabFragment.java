package com.eastcom.mycitycard.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eastcom.mycitycard.R;

/**
 * Created by rockgarden on 15/11/10.
 */
@SuppressLint("ValidFragment")
public class FundTabFragment extends Fragment {

    private int mPosition;

    public FundTabFragment(int position) {
        mPosition = position;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_foudtab, container, false);

        TextView textView = (TextView) rootView.findViewById(R.id.fav_number);
        textView.setText("Favorite " + mPosition);

        return rootView;
    }
}
