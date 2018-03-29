package com.android.anmol.feeds_cleanarch.base;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by anmolsehgal on 27-03-2018.
 * <p>
 * Created on 3/2/2018.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    public static boolean isActive(@Nullable final Activity activity) {
        return activity != null && !activity.isFinishing();
    }
}
