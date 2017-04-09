package com.modanisa.flickr.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by muhammadkorany on 4/8/17.
 */

public class MessagesUtils {

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
