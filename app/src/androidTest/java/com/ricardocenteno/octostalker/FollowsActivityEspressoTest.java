package com.ricardocenteno.octostalker;

import org.junit.Rule;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import com.ricardocenteno.octostalker.view.ui.FollowsActivity;
import org.junit.Test;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.PositionAssertions.isBelow;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class FollowsActivityEspressoTest {
    @Rule
    public ActivityTestRule<FollowsActivity> rule =
            new ActivityTestRule<>(FollowsActivity.class,true,false);

    @Test
    public void demonstrateIntentPrep() {
        Intent intent = new Intent();
        intent.putExtra("user", "Test");
        rule.launchActivity(intent);
        onView(withId(R.id.txt_user_selected)).check(matches(withText("Test follows")));
    }

    @Test
    public void ensureProgressBardWork() {
        onView(withId(R.id.rv_users))
                .perform(click())
                .check(isBelow(withId(R.id.txt_user_selected)));
    }

}
