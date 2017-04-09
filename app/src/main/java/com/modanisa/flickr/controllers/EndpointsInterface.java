package com.modanisa.flickr.controllers;

import com.modanisa.flickr.models.APIUrls;
import com.modanisa.flickr.models.PhotosResponseModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by muhammadkorany on 4/8/17.
 */

public interface EndpointsInterface {

    @GET(APIUrls.REST_URL)
    Call<PhotosResponseModel> getPhotos(@Query("method") String method, @Query("api_key") String api_key, @Query("tag") String tag
            , @Query("page") int page, @Query("perpage") int perpage
            , @Query("format") String format, @Query("nojsoncallback") String nojsoncallback);

    //https://api.flickr.com/services/rest/?method=flickr.tags.getClusterPhotos&api_key=f64b5341b77a779d52cf41749905e098&tag=fashion&format=json


}
