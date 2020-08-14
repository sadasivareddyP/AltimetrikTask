package com.altimetrik.itunes_search.di.module;

import android.content.Context;

import com.altimetrik.itunes_search.data.local.SharedPreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Singleton
@Module(includes = ContextModule.class)
public class SharedPreferenceManagerModule {

    @Singleton
    @Provides
    SharedPreferenceManager initializeSharedPreferences(Context context){
        return new SharedPreferenceManager(context);
    }
}
