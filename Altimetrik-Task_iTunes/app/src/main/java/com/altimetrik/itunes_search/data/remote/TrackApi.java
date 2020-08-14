package com.altimetrik.itunes_search.data.remote;

import com.altimetrik.itunes_search.model.TrackResponse;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TrackApi {

    /**
     * Calling Search API request
     * @param term
      * @return This will return a Call object with type {@link TrackResponse}
     */
    @GET("/search")
    Single<Response<TrackResponse>> searchTracks(@Query("term") String term);

}
