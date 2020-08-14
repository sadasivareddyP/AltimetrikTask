package com.altimetrik.itunes_search.di.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;


@Module
public class ContextModule {

    private Context context;

    public ContextModule(Context context){
        this.context = context;
    }

    @Provides
    public Context getContext() {
        return context.getApplicationContext();
    }
}
