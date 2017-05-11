package com.mobile.mpasswordkeeper;

/**
 * Created by 836137 on 10-05-2017.
 */
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.mobile.mpasswordkeeper.database.DaoMaster;
import com.mobile.mpasswordkeeper.database.DaoSession;

import org.greenrobot.greendao.database.Database;


public class PasswordKeeper extends Application {

    private DaoSession daoSession;
    public static final boolean ENCRYPTED = true;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "passwords-db-encrypted" : "passwords-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
