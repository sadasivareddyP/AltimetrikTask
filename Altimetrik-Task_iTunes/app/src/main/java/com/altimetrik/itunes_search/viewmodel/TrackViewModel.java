package com.altimetrik.itunes_search.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.altimetrik.itunes_search.data.remote.TrackRepository;
import com.altimetrik.itunes_search.model.ResponseResult;

public class TrackViewModel extends ViewModel {

    /**
     * LiveData representing the response result of the API call
     */
    private MutableLiveData<ResponseResult> mutableLiveData;

    /**
     * Call Search API and retrieve a LiveData to be observed by the UI
     * @param term
      */
    public void searchTrack(TrackRepository trackRepository, String term){
        mutableLiveData = trackRepository.searchTrack(term);
    }

    /**
     * Expose the LiveData {@link com.altimetrik.itunes_search.model.TrackResponse} query so the UI can observe it.
     */
    public LiveData<ResponseResult> getTrackRepository()
    {
        return mutableLiveData;
    }

}
