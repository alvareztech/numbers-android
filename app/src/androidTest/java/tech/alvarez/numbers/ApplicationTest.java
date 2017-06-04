package tech.alvarez.numbers;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import tech.alvarez.numbers.activities.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class ApplicationTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickAddButton_showActivity() {
        onView(withId(R.id.fab)).perform(click());

        pressBack();

        onView(withId(R.id.fab)).perform(click());
    }

}