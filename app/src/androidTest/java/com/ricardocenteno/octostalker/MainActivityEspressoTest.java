package com.ricardocenteno.octostalker;

import com.ricardocenteno.octostalker.view.ui.MainActivity;

import org.junit.Rule;

import android.support.test.rule.ActivityTestRule;
import org.junit.Test;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.assertion.LayoutAssertions.noOverlaps;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.PositionAssertions.isBelow;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class MainActivityEspressoTest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);


    @Test
    public void ensureProgressBardWork() {
        onView(withId(R.id.rv_users))
                .perform(click())
                .check(isBelow(withId(R.id.progress_users)));
    }

}
