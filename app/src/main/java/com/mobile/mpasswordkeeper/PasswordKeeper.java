package com.mobile.mpasswordkeeper;

/**
 * Created by 836137 on 10-05-2017.
 */
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.mobile.mpasswordkeeper.database.DaoMaster;
import com.mobile.mpasswordkeeper.database.DaoSession;


public class PasswordKeeper extends Application {

    private DaoMaster.DevOpenHelper helper;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        helper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
