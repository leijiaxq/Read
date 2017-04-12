package com.maymeng.read.base;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

/**
 * Create by  leijiaxq
 * Date       2017/3/2 14:31
 * Describe
 */

public class BaseApplication extends Application {

    public static BaseApplication mInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
      /*  if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);*/
        CrashReport.initCrashReport(getApplicationContext(), "4850ebf45e", false);
    }

    public static BaseApplication getInstance() {
        return mInstance;
    }
}
