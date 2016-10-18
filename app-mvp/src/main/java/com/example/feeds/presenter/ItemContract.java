package com.example.feeds.presenter;

import android.content.Context;

import com.example.feeds.model.Feed;

public interface ItemContract {

    interface View {

        void setLikes(int likes, boolean isLike);

        void setShared(int shared);

        Context getContext();
    }

    interface Presenter {

        void onLiked(android.view.View check, Feed feed);

        void onShared(Feed feed);

        void onClicked(Feed feed);

        void onDetach();
    }
}
