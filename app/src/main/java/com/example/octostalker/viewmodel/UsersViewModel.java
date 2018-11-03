package com.example.octostalker.viewmodel;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;
import com.example.octostalker.OctoStalkerApplication;
import com.example.octostalker.R;
import com.example.octostalker.data.UserService;
import com.example.octostalker.model.User;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class UsersViewModel extends Observable {

    public ObservableInt peopleProgress;
    public ObservableInt peopleRecycler;
    public ObservableInt peopleLabel;
    public ObservableInt userLabel;
    public ObservableField<String> messageLabel;
    public ObservableField<String> userName;


    private List<User> users;
    private Context context;
    private CompositeDisposable compositeDisposable;

    public UsersViewModel(@NonNull Context context, User user) {

        this.context = context;
        this.users = new ArrayList<>();
        compositeDisposable = new CompositeDisposable();
        userLabel = user!=null ? new ObservableInt(View.VISIBLE):new ObservableInt(View.GONE);
        peopleProgress = new ObservableInt(View.GONE);
        peopleRecycler = new ObservableInt(View.GONE);
        peopleLabel = new ObservableInt(View.VISIBLE);
        messageLabel = new ObservableField<>(context.getString(R.string.loading));
        userName = new ObservableField<>(context.getString(R.string.follows));
        initializeViews();
        if (user!=null && user.getLogin()!=null) {
            userName.set(user.getLogin() + " " + context.getString(R.string.follows));
            fetchFollowers(user.getLogin());
        }else
            fetchPeopleList();
    }

    public void initializeViews() {
        peopleLabel.set(View.GONE);
        peopleRecycler.set(View.GONE);
        peopleProgress.set(View.VISIBLE);
    }

    private void fetchPeopleList() {

        OctoStalkerApplication peopleApplication = OctoStalkerApplication.create(context);
        if(peopleApplication!=null) {
            UserService peopleService = peopleApplication.getPeopleService();


            Disposable disposable = peopleService.getOrganizationMember("bypasslane")
                    .subscribeOn(peopleApplication.subscribeScheduler())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(users -> {
                        changePeopleDataSet(users);
                        setSuccessView();
                    }, throwable -> {
                        setErrorView(throwable.getMessage() + " " +context.getString(R.string.error_loading_users));
                    });

            compositeDisposable.add(disposable);
        }
    }

    private void fetchFollowers(String username) {
        OctoStalkerApplication peopleApplication = OctoStalkerApplication.create(context);
        UserService peopleService = peopleApplication.getPeopleService();
        Disposable disposable = peopleService.getFollowingUser(username)
                .subscribeOn(peopleApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users -> {
                    changePeopleDataSet(users);
                    setSuccessView();
                }, throwable -> {
                    setErrorView(throwable.getMessage() + " " + context.getString(R.string.error_loading_users));
                });

        compositeDisposable.add(disposable);
    }

    private void setErrorView(String error){
        messageLabel.set(error);
        userLabel.set(View.GONE);
        peopleProgress.set(View.GONE);
        peopleLabel.set(View.VISIBLE);
        peopleRecycler.set(View.GONE);
    }

    private void setSuccessView(){
        peopleProgress.set(View.GONE);
        peopleLabel.set(View.GONE);
        peopleRecycler.set(View.VISIBLE);
    }

    private void changePeopleDataSet(List<User> peoples) {
        users.addAll(peoples);
        setChanged();
        notifyObservers();
    }

    public List<User> getUsers() {
        return users;
    }

    private void unSubscribeFromObservable() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void reset() {
        unSubscribeFromObservable();
        compositeDisposable = null;
        context = null;
    }
}