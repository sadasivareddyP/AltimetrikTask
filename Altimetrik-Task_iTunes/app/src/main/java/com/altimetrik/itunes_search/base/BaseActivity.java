package com.altimetrik.itunes_search.base;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.altimetrik.itunes_search.model.Track;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseActivity extends DaggerBaseActivity {

    // we can use Local Database like Room to store instead of using static.
    public static ArrayList<Track> cartTracks = new ArrayList<>();
    private ViewDataBinding activityMainBinding;

    @LayoutRes
    protected abstract int layoutRes();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Obtain binding object using the Data Binding library
        activityMainBinding = DataBindingUtil.setContentView(this, layoutRes());
        ButterKnife.bind(this);

    }

    public ViewDataBinding getActivityMainBinding() {
        return activityMainBinding;
    }

}
