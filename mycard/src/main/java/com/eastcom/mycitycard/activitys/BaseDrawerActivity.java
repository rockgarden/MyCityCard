package com.eastcom.mycitycard.activitys;

import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.eastcom.mycitycard.R;
import com.eastcom.mycitycard.util.CircleTransformation;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by rockgarden on 15/11/17.
 */
public class BaseDrawerActivity extends BaseActivity {

    @InjectView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @InjectView(R.id.ivMenuUserProfilePhoto)
    ImageView ivMenuUserProfilePhoto;

    private int headerSize;
    private String profilePhoto;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentViewWithoutInject(R.layout.activity_drawerlayout);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.flContentRoot);
        LayoutInflater.from(this).inflate(layoutResID, viewGroup, true);
        injectViews(); //注册

        setupHeader();
    }

    @Override
    protected void setupToolbar() {
        super.setupToolbar();
        if (getToolbar() != null) {
            getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
            });
        }
    }

    @OnClick(R.id.DrawerLayoutHeader)
    public void onDrawerLayoutHeaderClick(final View v) {
        drawerLayout.closeDrawer(Gravity.LEFT);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int[] startingLocation = new int[2];
                v.getLocationOnScreen(startingLocation);
                startingLocation[0] += v.getWidth() / 2;
                UserProfileActivity.startUserProfileFromLocation(startingLocation, BaseDrawerActivity.this);
                overridePendingTransition(0, 0);
            }
        }, 200);
    }

    private void setupHeader() {
        this.headerSize = getResources().getDimensionPixelSize(R.dimen.head_size);
        this.profilePhoto = getResources().getString(R.string.user_profile_photo);
        Picasso.with(this)
                .load(profilePhoto)
                .placeholder(R.drawable.img_circle_placeholder)
                .resize(headerSize, headerSize)
                .centerCrop()
                .transform(new CircleTransformation())
                .into(ivMenuUserProfilePhoto);
    }
}
