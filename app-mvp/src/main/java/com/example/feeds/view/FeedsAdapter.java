package com.example.feeds.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.feeds.presenter.ItemContract;
import com.example.feeds.R;
import com.example.feeds.model.Feed;
import com.example.feeds.presenter.ItemPresenter;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

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
        holder.bind(feeds.get(position));
    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements ItemContract.View{

        @BindView(R.id.auth_photo)
        CircleImageView auth_photo;

        @BindView(R.id.auth_name)
        TextView auth_name;

        @BindView(R.id.text)
        TextView text;

        @BindView(R.id.image)
        ImageView image;

        @BindView(R.id.like_check)
        CheckBox like_check;

        @BindView(R.id.likes)
        TextView likes;

        @BindView(R.id.share_btn)
        Button share_btn;

        @BindView(R.id.shares)
        TextView shares;

        private ItemPresenter presenter;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            presenter = new ItemPresenter(this);
        }

        public ItemPresenter getPresenter() {
            return presenter;
        }

        void bind(Feed feed){

            Picasso.with(auth_photo.getContext())
                    .load(feed.getAuth_photo())
                    .into(auth_photo);

            Picasso.with(image.getContext())
                    .load(feed.getImage())
                    .into(image);

            auth_name.setText(feed.getAuth_name());
            text.setText(feed.getText());

            likes.setText(String.valueOf(feed.getLikes()));
            shares.setText(String.valueOf(feed.getShares()));

            like_check.setChecked(feed.isLiked());
            like_check.setBackgroundResource(feed.isLiked() ? R.drawable.plus_checked : R.drawable.plus_unchecked);

            like_check.setOnClickListener(view -> presenter.onLiked(like_check, feed));

            share_btn.setOnClickListener(view -> presenter.onShared(feed));

            itemView.setOnClickListener(view -> presenter.onClicked(feed));
        }

        @Override
        public void setLikes(int count, boolean isLike) {
            likes.setText(String.valueOf(count));
            like_check.setBackgroundResource(isLike ? R.drawable.plus_checked : R.drawable.plus_unchecked);
        }

        @Override
        public void setShared(int shared) {
            shares.setText(String.valueOf(shared));
        }

        @Override
        public Context getContext() {
            return itemView.getContext();
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        viewHolder.getPresenter().onDetach();
    }
}
