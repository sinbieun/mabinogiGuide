/**
 * Generated by smali2java 1.0.0.558
 * Copyright (C) 2013 Hensence.com
 */

package com.addon.user.myapplication;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.addon.user.myapplication.db.ErgEffect;
import com.addon.user.myapplication.db.ErgExp;
import com.addon.user.myapplication.db.ErgMaterial;
import com.addon.user.myapplication.db.TradeItem;
import com.addon.user.myapplication.db.TradeContent;
import com.addon.user.myapplication.db.TitleFirst;
import com.addon.user.myapplication.db.TitleSecond;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase db) {
        // 교역
        db.execSQL("CREATE TABLE TRADEITEM (_id INTEGER PRIMARY KEY AUTOINCREMENT, town TEXT, item TEXT, weight INT, slotValue INT);)");
        db.execSQL("CREATE TABLE TRADECONTENT (_id INTEGER PRIMARY KEY AUTOINCREMENT, startTown TEXT, tradeMeans TEXT, endTown TEXT, equalData TEXT, time INT);)");

        // 타이틀
        /*db.execSQL("CREATE TABLE TITLEFIRST(_id INTEGER PRIMARY KEY AUTOINCREMENT, titleId INT, titleNumber VARCHAR(2), titleName VARCHAR(30), titleKind VARCHAR(10), titleCategory VARCHAR(20), beforeComment TEXT, afterComment TEXT, howGet TEXT);)");
        db.execSQL("CREATE TABLE TITLESECOND(_id INTEGER PRIMARY KEY AUTOINCREMENT, titleId INT, titleTrans VARCHAR(10), titleStat VARCHAR(30), titleTransInt VARCHAR(4), isPersent TEXT);)");*/

        // 에르그
        db.execSQL("CREATE TABLE ERGMATERIAL(_id INTEGER PRIMARY KEY AUTOINCREMENT, weaponName VARCHAR(20), class INT, material1 VARCHAR(50), material2 VARCHAR(50), material3 VARCHAR(50), material4 VARCHAR(50), material5 VARCHAR(100), material6 VARCHAR(50));)");
        db.execSQL("CREATE TABLE ERGEXP(_id INTEGER PRIMARY KEY AUTOINCREMENT, ergLevel INT, exp INT, totalExp INT);)");
        db.execSQL("CREATE TABLE ERGEFFECT(_id INTEGER PRIMARY KEY AUTOINCREMENT, weaponName VARCHAR(20), ergLevel INT, effect1 VARCHAR(5), effect2 VARCHAR(5), effect3 VARCHAR(5), effect4 VARCHAR(5));)");

        // 저장 데이터 공간
        db.execSQL("CREATE TABLE LOCALDATATABLE(_id INTEGER PRIMARY KEY AUTOINCREMENT, useLayout VARCHAR(50), keyName VARCHAR(40), keyContent VARCHAR(20))");

        insert(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TRADEITEM");
        db.execSQL("DROP TABLE IF EXISTS TRADECONTENT");
        db.execSQL("DROP TABLE IF EXISTS ERGMATERIAL");
        db.execSQL("DROP TABLE IF EXISTS ERGEXP");
        db.execSQL("DROP TABLE IF EXISTS ERGEFFECT");

        onCreate(db);
        /*switch(oldVersion) {
            case 1:
                //upgrade logic from version 1 to 2
                break;
            case 2:
                //upgrade logic from version 2 to 3
                break;
            case 3:
                //upgrade logic from version 3 to 4
                break;
            case 3:
                //upgrade logic from version 3 to 4
                break;
            case 3:
                //upgrade logic from version 3 to 4
                break;
            case 3:
                //upgrade logic from version 3 to 4
                break;
        }*/

        /*if(oldVersion >= 1 && oldVersion <= 6){
            db.execSQL("CREATE TABLE ERGMATERIAL(_id INTEGER PRIMARY KEY AUTOINCREMENT, weaponName VARCHAR(20), class INT, material1 VARCHAR(50), material2 VARCHAR(50), material3 VARCHAR(50), material4 VARCHAR(50), material5 VARCHAR(100), material6 VARCHAR(50));)");
            db.execSQL("CREATE TABLE ERGEXP(_id INTEGER PRIMARY KEY AUTOINCREMENT, ergLevel INT, exp INT, totalExp INT);)");
            db.execSQL("CREATE TABLE ERGEFFECT(_id INTEGER PRIMARY KEY AUTOINCREMENT, weaponName VARCHAR(20), ergLevel INT, effect1 VARCHAR(5), effect2 VARCHAR(5), effect3 VARCHAR(5), effect4 VARCHAR(5));)");

            // 에르그
            ErgMaterial ergMaterial = new ErgMaterial();
            ergMaterial.insert(db);

            ErgExp ergExp = new ErgExp();
            ergExp.insert(db);

            ErgEffect ergEffect = new ErgEffect();
            ergEffect.insert(db);
        }*/
    }

    public void insert(SQLiteDatabase db) {
        // 교역
        TradeItem tradeItem = new TradeItem();
        tradeItem.insert(db);
        TradeContent tradeContent = new TradeContent();
        tradeContent.insert(db);

        // 타이틀
        /*TitleFirst titleFirst = new TitleFirst();
        titleFirst.insert(db);

        TitleSecond titleSecond = new TitleSecond();
        titleSecond.insert(db);*/

        // 에르그
        ErgMaterial ergMaterial = new ErgMaterial();
        ergMaterial.insert(db);

        ErgExp ergExp = new ErgExp();
        ergExp.insert(db);

        ErgEffect ergEffect = new ErgEffect();
        ergEffect.insert(db);
    }
}
