package com.altimetrik.itunes_search.view.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.altimetrik.itunes_search.R;
import com.altimetrik.itunes_search.databinding.ActivityTrackDetailBinding;
import com.altimetrik.itunes_search.model.Track;
import com.altimetrik.itunes_search.model.TrackEvent;
import com.altimetrik.itunes_search.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

public class TrackDetailActivity extends BaseActivity {

    /**
     * Data binding for activity
     */
    ActivityTrackDetailBinding activityTrackDetailBinding;

    /**
     * Activity UI components
     */
    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    /**
     * Whether the activity is called from {@link HistoryHandlerActivity}
     */
    boolean fromHistory = false;

    /**
     * Override onBackPressed action when the activity is called from {@link HistoryHandlerActivity}. Call {@link MainActivity} if value is true.
     */
    @Override
    public void onBackPressed(){

        if(fromHistory){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        super.onBackPressed();
    }

    /**
     * Saving the {@link TrackDetailActivity} as the last visited activity
     */
    @Override
    public void onResume() {
        getSharedPreferenceManager().setLastActivity(getClass().getName());
        super.onResume();
    }

    /**
     * Register {@link EventBus} to this activity for selected {@link TrackEvent} data listener
     */
    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    /**
     * Unregister {@link EventBus} when activity is destroyed
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected int layoutRes() {
        return R.layout.activity_track_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtain binding object using the Data Binding library
        activityTrackDetailBinding = (ActivityTrackDetailBinding) getActivityMainBinding();

        setToolBar();

        Intent intent = getIntent();
        fromHistory = intent.getBooleanExtra("from_history", false);
        if(fromHistory){
            // Get the last selected track saved from preferences and assign as the current track to bind on UI
            Track lastTrackSaved = convertStringToTrack(getSharedPreferenceManager().getLastTrackSaved());
            activityTrackDetailBinding.setTrack(lastTrackSaved);
            setToolBarTitle(lastTrackSaved);
        }

    }

    /**
     * Method to setup the {@link Toolbar} widget and handle it's navigation
     */
    private void setToolBar(){
        setSupportActionBar(mToolBar);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        mToolBar.setNavigationOnClickListener(v -> onBackPressed());
    }

    /**
     * {@link EventBus} subscriber method. If a track is selected, saved it as the last track saved in preference then bind the track to UI
     * @param trackEvent The value of the event result
     */
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(TrackEvent trackEvent) {
        getSharedPreferenceManager().setLastTrackSaved(convertTrackToString(trackEvent.getTrack()));
        activityTrackDetailBinding.setTrack(trackEvent.getTrack());

        setToolBarTitle(trackEvent.getTrack());

    }

    /**
     * Method to setup the {@link Toolbar} widget title based on {@link Track} trackName or collectionName
     * @param track The current selected track
     */
    public void setToolBarTitle(Track track){
        mToolBar.setTitle(track.getTrackName()==null ? track.getCollectionName() : track.getTrackName());
    }

    /**
     * Helper method to convert {@link Track} object to String so we can saved it on preference
     * @param track The current selected track
     * @return The result is the Json string representation of track
     */
    public static String convertTrackToString(Track track){
        return new Gson().toJson(track);
    }

    /**
     * Helper method to convert the {@link Track} Json string representation back to {@link Track} object
     * @param track The Json string representation of {@link Track} saved in preference
     * @return The result is the {@link Track} object
     */
    public static Track convertStringToTrack(String track){
        return new Gson().fromJson(track, Track.class);
    }
}
