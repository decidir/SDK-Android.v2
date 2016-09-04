package com.android.decidir.example;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by biandra on 17/08/16.
 */
public class DecidirApp extends Application {

    private static DecidirApp instance;
    //	private Activity currentActivity;
    private boolean activityVisible;

    public DecidirApp() {
//		super();
        instance = this;
    }

    public static Context getAppContext() {
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //RoboGuice.injectMembers(this, this);
    }

//	public Activity getCurrentActivity() {
//		return currentActivity;
//	}
//
//	public void setCurrentActivity(Activity currentActivity) {
//		this.currentActivity = currentActivity;
//	}

    public void activityResumed() {
        activityVisible = true;
    }

    public void activityPaused() {
        activityVisible = false;
    }

    public boolean isActivityVisible() {
        return activityVisible;
    }

    public int getAppVersion() throws PackageManager.NameNotFoundException
    {
        PackageInfo packageInfo = getAppContext().getPackageManager().getPackageInfo(getAppContext().getPackageName(), 0);
        return packageInfo.versionCode;
    }

}
