package com.example.feeds.view;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.feeds.R;
import com.example.feeds.databinding.ActivityFeedsBinding;
import com.example.feeds.viewmodel.FeedsViewModel;

import java.util.Collections;

public class FeedsActivity extends AppCompatActivity {

    private FeedsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewModel = new FeedsViewModel(this);

        ActivityFeedsBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_feeds);
        binding.setViewModel(viewModel);

        initRecyclerView(binding.recycler);

        initSwipeRefresh(binding.swipe);

        viewModel.onRefresh();
    }

    private void initRecyclerView(RecyclerView view){
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(new FeedsAdapter(Collections.emptyList()));

        viewModel.getFeeds().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                ((FeedsAdapter)view.getAdapter()).setFeeds(viewModel.getFeeds().get());
            }
        });
    }

    private void initSwipeRefresh(SwipeRefreshLayout swipe){
        swipe.setOnRefreshListener(() -> viewModel.onRefresh());

        viewModel.getIsRefreshing().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                swipe.setRefreshing(viewModel.getIsRefreshing().get());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.onDetach();
    }
}