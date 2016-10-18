package com.example.feeds.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.feeds.R;
import com.example.feeds.databinding.FeedItemBinding;
import com.example.feeds.model.Feed;
import com.example.feeds.viewmodel.ItemViewModel;

import java.util.List;

public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.ViewHolder>{

    private ViewHolder viewHolder;

    private List<Feed> feeds;

    public FeedsAdapter(List<Feed> feeds) {
        this.feeds = feeds;
    }

    public void setFeeds(List<Feed> feeds) {
        this.feeds = feeds;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_item,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindFeed(feeds.get(position));
    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private FeedItemBinding binding;

        private ItemViewModel viewModel;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = FeedItemBinding.bind(itemView);
            binding.setViewModel(viewModel = new ItemViewModel(itemView.getContext()));
        }

        public void bindFeed(Feed feed){
            binding.setFeed(feed);
            binding.getRoot().setOnClickListener(view -> viewModel.onClicked(feed));
        }

        public ItemViewModel getViewModel() {
            return viewModel;
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        viewHolder.getViewModel().onDetach();
    }
}
