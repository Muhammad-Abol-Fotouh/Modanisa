package com.modanisa.flickr.controllers;

import retrofit2.Response;

/**
 * Created by muhammadkorany on 4/8/17.
 */

public interface APIResponseCallback {
    void onSuccess(Response response);
    void onFailure(Response response);
}
