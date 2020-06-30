package com.uprank.uprank.teacher.firebase;

import android.app.Application;
import android.content.Context;

/**
 * Created by Rahul Hooda on 14/7/17.
 */
public class BaseApplication extends Application {


    private static Application sApplication;

    public static Context getAppContext() {
        return getApplication().getApplicationContext();
    }

    public static Application getApplication() {

        return sApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }
}
