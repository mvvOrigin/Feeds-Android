package com.example.feeds.presenter;

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

public class ItemPresenter implements ItemContract.Presenter {

    @Inject
    ApiService api;

    private ItemContract.View view;

    private CompositeSubscription subscriptions;

    public ItemPresenter(ItemContract.View view) {
        this.view = view;
        MainApplication.getComponent().inject(this);
        this.subscriptions = new CompositeSubscription();
    }

    @Override
    public void onLiked(View check, Feed feed) {
        boolean isLike = ((CheckBox)check).isChecked();

        subscriptions.add(
                api.onLiked(feed.getId(),isLike)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe()
        );

        feed.setLiked(isLike);
        feed.setLikes(feed.getLikes() + (feed.isLiked() ? 1: -1));

        view.setLikes(feed.getLikes(), isLike);
        Utils.snack(view.getContext(),isLike ? R.string.feed_liked : R.string.feed_disliked,false);
    }

    @Override
    public void onShared(Feed feed) {
        subscriptions.add(
                api.onShared(feed.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe()
        );

        feed.setShares(feed.getShares() +1);
        view.setShared(feed.getShares());

        Utils.snack(view.getContext(),R.string.feed_shared,false);
    }

    @Override
    public void onClicked(Feed feed) {
        Utils.snack(view.getContext(),R.string.feed_clicked,false);
    }

    @Override
    public void onDetach() {
        if (subscriptions.hasSubscriptions())
            subscriptions.unsubscribe();

        subscriptions = null;
        view = null;
    }
}
