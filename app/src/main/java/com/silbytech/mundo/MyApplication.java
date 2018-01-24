package com.silbytech.mundo;

import android.app.Application;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import java.io.File;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class MyApplication extends Application {
    private static MyApplication instance;
    public static final String PREFS = "prefs";


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }

    public static MyApplication getInstance() {
        return instance;
    }

    /*******************************************************************************
     * This method will clear all of the Application data from the device
     *******************************************************************************/
    public void clearApplicationData() {
        File cacheDirectory = getCacheDir();
        File applicationDirectory = new File(cacheDirectory.getParent());
        if (applicationDirectory.exists()) {
            String[] fileNames = applicationDirectory.list();
            for (String fileName : fileNames) {
                if (!fileName.equals("lib")) {
                    deleteFile(new File(applicationDirectory, fileName));
                }
            }
        }
    }


    /*******************************************************************************
     * This method will delete all file of the app from the device
     * @param file - file to remove
     * @return - true at completion
     ********************************************************************************/
    public static boolean deleteFile(File file) {
        boolean deletedAll = true;
        if (file != null) {
            if (file.isDirectory()) {
                String[] children = file.list();
                for (int i = 0; i < children.length; i++) {
                    deletedAll = deleteFile(new File(file, children[i])) && deletedAll;
                }
            } else {
                deletedAll = file.delete();
            }
        }
        return deletedAll;
    }
}
