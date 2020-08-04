package us.erlang.android_test;

import android.app.Application;


import static org.mockito.Mockito.mock;

public class MyApplication extends Application {
    private UserDBDatasource localDataSource;
    private static MyApplication application;

    public void onCreate() {
        super.onCreate();
        localDataSource = mock(UserDBDatasource.class);
        application = this;
    }

    public static MyApplication getInstance(){
        return application;
    }

    public UserDBDatasource getLocalDataSource() {
        return localDataSource;
    }
}
