package com.altimetrik.itunes_search.data.remote;

import androidx.lifecycle.MutableLiveData;

import com.altimetrik.itunes_search.model.ResponseResult;
import com.altimetrik.itunes_search.model.TrackResponse;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class TrackRepository {

    private CompositeDisposable disposable;

    /**
     * The API interface to use
     */

    private TrackApi trackApi;

    /**
     * Initializing the interface to implement
     */

    @Inject
    public TrackRepository(TrackApi trackApi, CompositeDisposable disposable){
        this.trackApi = trackApi;
        this.disposable = disposable;

    }

    /**
     * Calling {@link TrackApi} call for searching track
     * @param term
     * @return This will return a LiveData that the UI will observe
     */
    public MutableLiveData<ResponseResult> searchTrack(String term){
        MutableLiveData<ResponseResult> trackData = new MutableLiveData<>();

        disposable.add(trackApi.searchTracks(term)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<TrackResponse>>(){

                    @Override
                    public void onSuccess(Response<TrackResponse> trackResponseResponse) {

                        trackData.setValue(new ResponseResult<>(trackResponseResponse.body(), trackResponseResponse.raw()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        trackData.setValue(null);
                    }
                })

        );

       /* trackApi.searchTracks(term, country, media).enqueue(new Callback<TrackResponse>() {
            @Override
            public void onResponse(@NotNull Call<TrackResponse> call,
                                   @NotNull Response<TrackResponse> response) {

                trackData.setValue(new ResponseResult<>(response.body(), response.raw()));
            }

            @Override
            public void onFailure(@NotNull Call<TrackResponse> call, @NotNull Throwable t) {
                t.printStackTrace();
                trackData.setValue(null);
            }
        });*/
        return trackData;
    }

}
