package com.ricardocenteno.octostalker.view.ui;

import android.arch.lifecycle.Lifecycle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ricardocenteno.octostalker.R;
import com.ricardocenteno.octostalker.model.User;
import com.ricardocenteno.octostalker.viewmodel.ItemUserViewModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        User user = new User();
        user.setLogin("Pedlar");
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new UsersFragment(), UsersFragment.TAG)
                    .commit();
        }
    }

    public void showUser(User user) {
        Log.e("DEBUG", user.getLogin());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,new UsersFragment() ,null)
                .addToBackStack(null)
                .commit();
    }
}
