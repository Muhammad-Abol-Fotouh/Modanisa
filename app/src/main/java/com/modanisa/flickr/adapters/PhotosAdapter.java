package com.modanisa.flickr.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.modanisa.flickr.models.PhotoModel;
import com.modanisa.flickr.views.customviews.PhotoItem;
import java.util.ArrayList;

/**
 * Created by muhammadkorany on 4/8/17.
 */

public class PhotosAdapter extends BaseAdapter {

    private ArrayList<PhotoModel> photoModelArrayList;
    private Context context;
    private PhotoItem photoItem;

    public PhotosAdapter(Context context, ArrayList<PhotoModel> photoModelArrayList){
        this.context = context;
        this.photoModelArrayList = photoModelArrayList;
    }

    @Override
    public int getCount() {
        return photoModelArrayList.size();
    }

    @Override
    public PhotoModel getItem(int position) {
        return photoModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            photoItem = new PhotoItem(context);
        }else{
            photoItem = (PhotoItem) convertView;
        }

        photoItem.setPhotoCellData(photoModelArrayList.get(position));

        return photoItem;
    }
}
