package com.example.feeds.util;

import android.app.Activity;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Utils {

    @BindingAdapter("bind:imageUrl")
    public static void load(ImageView view, String url){
        Picasso.with(view.getContext()).load(url).into(view);
    }

    public static void snack(Context context, int res, boolean isLong) {
        if (context instanceof Activity) {
            try {
                View view = ((ViewGroup) ((Activity) context).findViewById(android.R.id.content)).getChildAt(0);
                Snackbar.make(view, res, isLong ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT).show();
            }catch (Exception e){e.printStackTrace();}
        }
    }
}
