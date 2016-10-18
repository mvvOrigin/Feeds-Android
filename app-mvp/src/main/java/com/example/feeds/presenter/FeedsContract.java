package com.example.feeds.presenter;

import android.content.Context;

import com.example.feeds.model.Feed;

import java.util.List;

public interface FeedsContract {

    interface View {

        void showFeeds(List<Feed> feeds);

        void showRefreshing(boolean refreshing);

        Context getContext();
    }

    interface Presenter {

        void onRefresh();

        void onDetach();
    }
}
