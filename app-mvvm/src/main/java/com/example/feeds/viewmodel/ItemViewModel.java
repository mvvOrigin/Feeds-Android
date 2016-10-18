package com.example.feeds.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;
import android.widget.CheckBox;

import com.example.feeds.R;
import com.example.feeds.dagger.MainApplication;
import com.example.feeds.model.ApiService;
import com.example.feeds.model.Feed;
import com.example.feeds.util.Utils;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ItemViewModel extends BaseObservable {

    @Inject
    ApiService api;

    private Context context;

    private CompositeSubscription subscriptions;

    public ItemViewModel(Context context) {
        this.context = context;
        MainApplication.getComponent().inject(this);
        this.subscriptions = new CompositeSubscription();
    }

    public void onShared(Feed feed){
        subscriptions.add(
                api.onShared(feed.getId())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe()
        );

        feed.setShares(feed.getShares() +1);
        Utils.snack(context,R.string.feed_shared,false);
    }

    public void onLiked(View view, Feed feed){
        boolean isLike = ((CheckBox)view).isChecked();

        subscriptions.add(
                api.onLiked(feed.getId(),isLike)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe()
        );

        feed.setLikes(feed.getLikes() +(isLike ? 1 : -1));
        feed.setLiked(isLike);
        Utils.snack(context,isLike ? R.string.feed_liked : R.string.feed_disliked,false);
    }

    public void onClicked(Feed feed){
        Utils.snack(context,R.string.feed_clicked,false);
    }

    public void onDetach(){
        if (subscriptions.hasSubscriptions())
            subscriptions.unsubscribe();

        subscriptions = null;
        context = null;
    }
}
