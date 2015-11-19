package com.rockgarden.myapplication.activitys;

import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rockgarden.myapplication.BaseActivity;
import com.rockgarden.myapplication.R;
import com.rockgarden.myapplication.anim.CircleTransformation;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by rockgarden on 15/11/19.
 */
public class BaseDrawerActivity extends BaseActivity {

    @InjectView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @Optional
    @InjectView(R.id.ivMenuUserProfilePhoto)
    ImageView ivMenuUserProfilePhoto;

    private int avatarSize;
    private String profilePhoto;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentViewWithoutInject(R.layout.activity_drawer);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.flContentRoot);
        LayoutInflater.from(this).inflate(layoutResID, viewGroup, true);
        injectViews();

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

    @OnClick(R.id.vGlobalMenuHeader)
    public void onGlobalMenuHeaderClick(final View v) {
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
        this.avatarSize = getResources().getDimensionPixelSize(R.dimen.global_menu_avatar_size);
        this.profilePhoto = getResources().getString(R.string.user_profile_photo);
        Picasso.with(this)
                .load(profilePhoto)
                .placeholder(R.drawable.img_circle_placeholder)
                .resize(avatarSize, avatarSize)
                .centerCrop()
                .transform(new CircleTransformation())
                .into(ivMenuUserProfilePhoto);
    }
}
