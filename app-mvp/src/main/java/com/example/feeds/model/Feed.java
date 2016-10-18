package com.example.feeds.model;

import com.google.gson.annotations.SerializedName;

public class Feed {

    @SerializedName("id")
    private int id;

    @SerializedName("auth_name")
    private String auth_name;

    @SerializedName("auth_photo")
    private String auth_photo;

    @SerializedName("text")
    private String text;

    @SerializedName("image")
    private String image;

    @SerializedName("isLiked")
    private boolean isLiked;

    @SerializedName("likes")
    private int likes;

    @SerializedName("shares")
    private int shares;

    public Feed() {}

    public int getId() {
        return id;
    }

    public String getAuth_name() {
        return auth_name;
    }

    public String getAuth_photo() {
        return auth_photo;
    }

    public String getText() {
        return text;
    }

    public String getImage() {
        return image;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Feed)) return false;

        Feed feed = (Feed) o;

        if (getId() != feed.getId()) return false;
        if (isLiked() != feed.isLiked()) return false;
        if (getLikes() != feed.getLikes()) return false;
        if (getShares() != feed.getShares()) return false;
        if (getAuth_name() != null ? !getAuth_name().equals(feed.getAuth_name()) : feed.getAuth_name() != null)
            return false;
        if (getAuth_photo() != null ? !getAuth_photo().equals(feed.getAuth_photo()) : feed.getAuth_photo() != null)
            return false;
        if (getText() != null ? !getText().equals(feed.getText()) : feed.getText() != null)
            return false;
        return getImage() != null ? getImage().equals(feed.getImage()) : feed.getImage() == null;

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getAuth_name() != null ? getAuth_name().hashCode() : 0);
        result = 31 * result + (getAuth_photo() != null ? getAuth_photo().hashCode() : 0);
        result = 31 * result + (getText() != null ? getText().hashCode() : 0);
        result = 31 * result + (getImage() != null ? getImage().hashCode() : 0);
        result = 31 * result + (isLiked() ? 1 : 0);
        result = 31 * result + getLikes();
        result = 31 * result + getShares();
        return result;
    }
}
