package com.ricardocenteno.octostalker.data;

import android.util.Log;

import com.ricardocenteno.octostalker.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FakeRandomCompany {
    private static final String USER_NAME_TEST = "rickstart";
    private static final String USER_AVATAR_TEST = "https://avatars1.githubusercontent.com/u/74135";

    public static List<User> getUserList() {
        List<User> users = new ArrayList<>();
        Random rand = new Random();
        int n = rand.nextInt(99);
        for (int i = 0; i < n; i++) {
            users.add(getUser());
        }
        return users;
    }

    public static User getUser() {
        User user = new User();
        user.setLogin(USER_NAME_TEST);
        Random rand = new Random();
        int n = rand.nextInt(99);
        if (n>0)
            user.setFollowers(n);
        user.setAvatarUrl(USER_AVATAR_TEST+n+"?v=4");
        return user;
    }

}
