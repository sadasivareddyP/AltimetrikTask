package com.altimetrik.itunes_search.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.altimetrik.itunes_search.utility.Constants;

public class SharedPreferenceManager {

    /**
     * Preference KEY for last activity visited
     */
    private static final String PREF_KEY_LAST_ACTIVITY = "PREF_KEY_LAST_ACTIVITY";

    /**
     * Preference KEY for last track selected
     */
    private static final String PREF_KEY_LAST_TRACK_SAVED = "PREF_KEY_LAST_TRACK_SAVED";

    /**
     * Preference KEY for last date of calling Search API
     */
    private static final String PREF_KEY_LAST_SEARCH_DATE = "PREF_KEY_LAST_SEARCH_DATE";

    /**
     * Preference KEY for selected media type position
     */
    private static final String PREF_KEY_SEARCH_MEDIA_TYPE_POSITION = "PREF_KEY_SEARCH_MEDIA_TYPE_POSITION";

    /**
     * Preference KEY for selected country code
     */
    private static final String PREF_KEY_SEARCH_COUNTRY_CODE = "PREF_KEY_SEARCH_COUNTRY_CODE";

    /**
     * Preference KEY for search term value
     */
    private static final String PREF_KEY_SEARCH_TERM = "PREF_KEY_SEARCH_TERM";

    /**
     * SharedPreferences instance
     */
    private SharedPreferences sharedPreferences;

    public SharedPreferenceManager(Context context){
        sharedPreferences = getSharedPreference(context);
    }


    /**
     * Method to call a SharedPreferences instance
     * @param context Application context
     * @return The result is the default SharedPreferences instance of the application
     */
    private static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     *
     * @return The result is the last activity saved
     */
    public String getLastActivity() {
        return sharedPreferences.getString(PREF_KEY_LAST_ACTIVITY, "");
    }

    /**
     * Save activity to {@link SharedPreferences}
     *
     * @param activity The activity class name to save
     */
    public void setLastActivity(String activity) {
        sharedPreferences.edit().putString(PREF_KEY_LAST_ACTIVITY, activity).apply();
    }

    /**
     *
     * @return The result is the string representation of the last {@link com.altimetrik.itunes_search.model.Track} saved
     */
    public String getLastTrackSaved() {
        return sharedPreferences.getString(PREF_KEY_LAST_TRACK_SAVED, "");
    }

    /**
     * Save a string representation of the last {@link com.altimetrik.itunes_search.model.Track} selected
     * @param track The {@link com.altimetrik.itunes_search.model.Track} in string form to save
     */
    public void setLastTrackSaved(String track) {
        sharedPreferences.edit().putString(PREF_KEY_LAST_TRACK_SAVED, track).apply();
    }

    /**
     *
     * @return The result is the value of the latest date when the Search API is called
     */
    public String getLastSearchDate() {
        return sharedPreferences.getString(PREF_KEY_LAST_SEARCH_DATE, "");
    }

    /**
     * Save the last search date to {@link SharedPreferences}
     * @param date The value of last search date to save in {@link SharedPreferences}
     */
    public void setLastSearchDate(String date) {
        sharedPreferences.edit().putString(PREF_KEY_LAST_SEARCH_DATE, date).apply();
    }

    /**
     *
     * @return The result is the last saved term
     */
    public String getTerm() {
        return sharedPreferences.getString(PREF_KEY_SEARCH_TERM, Constants.DEFAULT_TERM);
    }

    /**
     * Save term to {@link SharedPreferences}
     * @param term The value to save in {@link SharedPreferences}
     */
    public void setTerm(String term) {
        sharedPreferences.edit().putString(PREF_KEY_SEARCH_TERM, term).apply();
    }
}
