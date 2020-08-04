package us.erlang.android_test;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.internal.operators.maybe.MaybeCreate;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;


@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void should_login_successfully_when_login_given_correct_username_and_password() {
        MyApplication applicationContext = (MyApplication) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        UserDBDatasource dataSource = applicationContext.getLocalDataSource();

        User user = new User("android", "password");
        when(dataSource.findByName("android")).thenReturn(new MaybeCreate(emitter -> emitter.onSuccess(user)));
        when(dataSource.caculMD5Hash("password")).thenReturn("password");

        onView(withId(R.id.user_name)).perform(typeText("android"));
        onView(withId(R.id.user_password)).perform(typeText("password"));
        onView(withId(R.id.login)).perform(click());
        onView(withText("Login successfully")).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void should_login_failed_when_login_given_invalid_password() {
        MyApplication applicationContext = (MyApplication) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        UserDBDatasource dataSource = applicationContext.getLocalDataSource();
        User user = new User("android", "123456");

        when(dataSource.findByName("android")).thenReturn(new MaybeCreate(emitter -> emitter.onSuccess(user)));
        when(dataSource.caculMD5Hash("123")).thenReturn("123");

        onView(withId(R.id.user_name)).perform(typeText("android"));
        onView(withId(R.id.user_password)).perform(typeText("123"));
        onView(withId(R.id.login)).perform(click());
        onView(withText("Password is invalid")).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void should_login_failed_when_login_given_username_does_not_exist() {
        MyApplication applicationContext = (MyApplication) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        UserDBDatasource dataSource = applicationContext.getLocalDataSource();
        User user = new User("android", "123");
        when(dataSource.findByName("notexist")).thenReturn(new Maybe<User>() {
            @Override
            protected void subscribeActual(MaybeObserver<? super User> observer) {
                observer.onComplete();
            }
        });
        when(dataSource.caculMD5Hash("123")).thenReturn("123");

        onView(withId(R.id.user_name)).perform(typeText("notexist"));
        onView(withId(R.id.user_password)).perform(typeText("123"));
        onView(withId(R.id.login)).perform(click());
        onView(withText("Username does not exist")).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }
}