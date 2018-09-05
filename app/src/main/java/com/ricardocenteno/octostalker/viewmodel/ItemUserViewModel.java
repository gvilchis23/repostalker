package com.ricardocenteno.octostalker.viewmodel;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableInt;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import com.ricardocenteno.octostalker.OctoStalkerApplication;
import com.ricardocenteno.octostalker.data.UserService;
import com.ricardocenteno.octostalker.model.User;
import com.ricardocenteno.octostalker.view.callback.UserClickCallback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class ItemUserViewModel extends BaseObservable {
    private User user;
    private Context context;
    public ObservableInt followersBadge;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private UserClickCallback callbacks;
    private HashMap<String,Integer> follows = new HashMap<>();

    public ItemUserViewModel(User user, Context context, @Nullable UserClickCallback callback) {
        this.user = user;
        this.context = context;
        this.callbacks = callback;

        followersBadge = new ObservableInt(View.GONE);
        /*if (user!=null && user.getFollowers()==null)
            getFollowers(user.getLogin());*/
    }

    public String getFollowers() {
        if(follows.containsKey(user.getLogin())){
            followersBadge.set(View.VISIBLE);
            return String.valueOf(follows.get(user.getLogin()));
        }else {
            followersBadge.set(View.GONE);
            return null;
        }
    }

    public String getImgProfile() {
        return user.getAvatarUrl();
    }

    public String getName() {
        return user.getLogin();
    }

    @BindingAdapter("imageURL")
    public static void setImageProfile(CircleImageView imgProfile, String url) {
        Picasso.get().load(url).into(imgProfile);
    }

    public void onItemClick(View view) {
        if (callbacks!=null)
            callbacks.onClick(user);
    }

    public void setUser(User user) {
        this.user = user;
        notifyChange();
    }

    private void getFollowers(String userId) {
        OctoStalkerApplication application = OctoStalkerApplication.create(context);
        if (application!=null) {
            UserService peopleService = application.getPeopleService();
            Disposable disposable = peopleService.getUser(userId)
                    .subscribeOn(application.subscribeScheduler())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(user -> {
                        if (user.getFollowers() != null) {
                            follows.put(user.getLogin(),user.getFollowers());
                            user.setFollowers(user.getFollowers());
                            followersBadge.set(View.VISIBLE);
                            notifyChange();
                        } else {
                            followersBadge.set(View.GONE);
                        }
                    }, throwable -> {
                        followersBadge.set(View.GONE);
                    });
            compositeDisposable.add(disposable);
        }
    }
}
