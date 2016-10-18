package com.example.feeds.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.feeds.presenter.FeedsContract;
import com.example.feeds.R;
import com.example.feeds.presenter.FeedsPresenter;
import com.example.feeds.model.Feed;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedsActivity extends AppCompatActivity implements FeedsContract.View {

    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    @BindView(R.id.list)
    RecyclerView list;

    private FeedsContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new FeedsPresenter(this);

        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new FeedsAdapter(Collections.emptyList()));

        refresh.setOnRefreshListener(() -> presenter.onRefresh());

        presenter.onRefresh();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showFeeds(List<Feed> feeds) {
        ((FeedsAdapter)list.getAdapter()).setFeeds(feeds);
    }

    @Override
    public void showRefreshing(boolean refreshing) {
        refresh.setRefreshing(refreshing);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}
