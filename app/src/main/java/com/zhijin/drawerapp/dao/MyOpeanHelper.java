package com.zhijin.drawerapp.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


/**
 * Created by Administrator on 2016/11/14.
 */

public class MyOpeanHelper extends DaoMaster.OpenHelper
{
    public MyOpeanHelper(Context context, String name)
    {
        super(context, name);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        super.onUpgrade(db, oldVersion, newVersion);
    }
}
