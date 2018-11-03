package com.example.octostalker.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.octostalker.R;
import com.example.octostalker.view.fragments.UsersFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new UsersFragment(), UsersFragment.TAG)
                    .commit();
        }
    }
}
