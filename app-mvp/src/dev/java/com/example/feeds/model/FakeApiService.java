package com.example.feeds.model;

import android.content.Context;
import android.os.SystemClock;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import retrofit2.http.Field;
import rx.Observable;

public class FakeApiService implements ApiService {

    private Context context;

    private List<Feed> base;

    private List<Feed> emulated;

    public FakeApiService(Context context) {
        this.context = context;
        this.emulated = new LinkedList<>();
    }

    @Override
    public Observable<List<Feed>> getFeeds() {

        if (base == null) initBaseList();

        return Observable.create(subscriber -> {
            SystemClock.sleep(1000);

            if (emulated.size() < base.size())
                emulated.add(0,base.get(emulated.size()));

            subscriber.onNext(emulated);
        });
    }

    private void initBaseList(){
        try(InputStreamReader reader = new InputStreamReader(context.getAssets().open("feeds.json"))){
            base = new Gson().fromJson(reader,new TypeToken<List<Feed>>(){}.getType());
        }catch (IOException e){ e.printStackTrace(); }
    }

    @Override
    public Observable<String> onLiked(@Field("id") int id, @Field("isLike") boolean isLike) {
        return Observable.create(subscriber -> subscriber.onNext("Success:200"));
    }

    @Override
    public Observable<String> onShared(@Field("id") int id) {
        return Observable.create(subscriber -> subscriber.onNext("Success:200"));
    }


}
