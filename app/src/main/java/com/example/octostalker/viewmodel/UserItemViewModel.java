package com.example.octostalker.viewmodel;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableInt;
import android.view.View;
import com.example.octostalker.OctoStalkerApplication;
import com.example.octostalker.data.UserService;
import com.example.octostalker.model.User;
import com.example.octostalker.view.activity.FollowersActivity;
import com.squareup.picasso.Picasso;
import java.util.HashMap;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class UserItemViewModel extends BaseObservable {
    private User user;
    private Context context;
    public ObservableInt followersBadge;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private HashMap<String,Integer> follows = new HashMap<>();

    public UserItemViewModel(User user, Context context) {
        this.user = user;
        this.context = context;

        if (user!=null && user.getFollowers()==null) {
            followersBadge = new ObservableInt(View.GONE);
            getFollowers(user.getLogin());
        }else
            followersBadge = new ObservableInt(View.VISIBLE);
    }

    public String getFollowers() {
        if(user.getFollowers()!=null) {
            followersBadge.set(View.VISIBLE);
            return String.valueOf(user.getFollowers());
        }
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
        Intent intent = new Intent(context,FollowersActivity.class);
        intent.putExtra("user",user.getLogin());
        context.startActivity(intent);
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
