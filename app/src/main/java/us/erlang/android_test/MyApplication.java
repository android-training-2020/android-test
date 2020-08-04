package us.erlang.android_test;

import android.app.Application;

public class MyApplication extends Application {
    private UserDBDatasource localDataSource;
    private static MyApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        localDataSource = new UserDBDatasource(getApplicationContext());
        application = this;
    }

    public static MyApplication getInstance(){
        return application;
    }

    public UserDBDatasource getLocalDataSource() {
        return localDataSource;
    }
}
