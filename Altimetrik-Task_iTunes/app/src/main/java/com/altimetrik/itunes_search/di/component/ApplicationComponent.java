package com.altimetrik.itunes_search.di.component;

import com.altimetrik.itunes_search.data.local.SharedPreferenceManager;
import com.altimetrik.itunes_search.data.remote.TrackRepository;
import com.altimetrik.itunes_search.di.module.SharedPreferenceManagerModule;
import com.altimetrik.itunes_search.di.module.TrackRepositoryModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {SharedPreferenceManagerModule.class, TrackRepositoryModule.class})
public interface ApplicationComponent {

    SharedPreferenceManager initializeSharedPreferenceManager();

    TrackRepository getTrackRepository();

}
