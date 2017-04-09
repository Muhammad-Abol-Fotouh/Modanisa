package com.modanisa.flickr.controllers;

import retrofit2.Response;

/**
 * Created by muhammadkorany on 4/8/17.
 */

public interface APIResponseCallback {
    // Called when data loaded properly
    void onSuccess(Response response);

    // Called when connection to server done, but server responds with error messages
    void onFailure(Response response);
}
