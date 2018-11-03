package com.example.octostalker;

import android.app.Application;
import android.content.Context;
import com.example.octostalker.data.UserFactory;
import com.example.octostalker.data.UserService;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class OctoStalkerApplication extends Application {

    private UserService peopleService;
    private Scheduler scheduler;

    private static OctoStalkerApplication get(Context context) {
        return (OctoStalkerApplication) context.getApplicationContext();
    }

    public static OctoStalkerApplication create(Context context) {
        return OctoStalkerApplication.get(context);
    }

    public UserService getPeopleService() {
        if (peopleService == null) {
            peopleService = UserFactory.create();
        }

        return peopleService;
    }

    public Scheduler subscribeScheduler() {
        if (scheduler == null) {
            scheduler = Schedulers.io();
        }

        return scheduler;
    }

    public void setPeopleService(UserService peopleService) {
        this.peopleService = peopleService;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
}
