package com.altimetrik.itunes_search.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.altimetrik.itunes_search.data.local.SharedPreferenceManager;
import com.altimetrik.itunes_search.data.remote.TrackRepository;
import com.altimetrik.itunes_search.di.component.ApplicationComponent;
import com.altimetrik.itunes_search.di.component.DaggerApplicationComponent;
import com.altimetrik.itunes_search.di.module.ContextModule;

import javax.inject.Inject;

public abstract class DaggerBaseActivity extends AppCompatActivity {

    @Inject
    SharedPreferenceManager sharedPreferenceManager;

    /**
     * Repository of {@link com.altimetrik.itunes_search.model.Track} api call
     */
    @Inject
    TrackRepository trackRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ApplicationComponent component = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
        sharedPreferenceManager = component.initializeSharedPreferenceManager();
        trackRepository = component.getTrackRepository();

    }

    public SharedPreferenceManager getSharedPreferenceManager() {
        return sharedPreferenceManager;
    }

    public TrackRepository getTrackRepository() {
        return trackRepository;
    }

}
