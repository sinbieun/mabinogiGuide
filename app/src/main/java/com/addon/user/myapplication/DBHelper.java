/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package com.addon.user.myapplication;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.addon.user.myapplication.db.TradeItem;
import com.addon.user.myapplication.db.TradeContent;
import com.addon.user.myapplication.db.TitleFirst;
import com.addon.user.myapplication.db.TitleSecond;

public class DBHelper extends SQLiteOpenHelper {
    
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE TRADEITEM (_id INTEGER PRIMARY KEY AUTOINCREMENT, town TEXT, item TEXT, weight INT, slotValue INT);)");
        db.execSQL("CREATE TABLE TRADECONTENT (_id INTEGER PRIMARY KEY AUTOINCREMENT, startTown TEXT, tradeMeans TEXT, endTown TEXT, equalData TEXT, time INT);)");
        db.execSQL("CREATE TABLE TITLEFIRST(_id INTEGER PRIMARY KEY AUTOINCREMENT, titleId INT, titleNumber VARCHAR(2), titleName VARCHAR(30), titleKind VARCHAR(10), titleCategory VARCHAR(20), beforeComment TEXT, afterComment TEXT, howGet TEXT);)");
        db.execSQL("CREATE TABLE TITLESECOND(_id INTEGER PRIMARY KEY AUTOINCREMENT, titleId INT, titleTrans VARCHAR(10), titleStat VARCHAR(30), titleTransInt VARCHAR(4), isPersent TEXT);)");
        insert(db);
    }
    
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    
    public void insert(SQLiteDatabase db) {
        TradeItem tradeItem = new TradeItem();
        tradeItem.insert(db);
        TradeContent tradeContent = new TradeContent();
        tradeContent.insert(db);
        TitleFirst titleFirst = new TitleFirst();
        titleFirst.insert(db);
        TitleSecond titleSecond = new TitleSecond();
        titleSecond.insert(db);
    }
}
