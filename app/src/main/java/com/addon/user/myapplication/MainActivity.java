/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package com.addon.user.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.NavigationView;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.addon.user.myapplication.layout.ErgLayout;
import com.addon.user.myapplication.layout.HistoryLayout;
import com.addon.user.myapplication.layout.HomeLayout;
import com.addon.user.myapplication.layout.MusicLayout;
import com.addon.user.myapplication.view.NoticeItem;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;

import com.addon.user.myapplication.layout.TradeLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import static com.google.android.gms.internal.zzahn.runOnUiThread;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private long backKeyPressedTime;
    DBHelper dbHelper;
    public int deviceHeight;
    public int deviceWidth;
    public static MainActivity thisActivity;
    private Toast toast;
    public SQLiteDatabase tradeDb;

    private TextView toolbarTitleView;
    private Toolbar toolbar;

    private FirebaseAnalytics mFirebaseAnalytics;

    // VIEW
    private LinearLayout contentView;
    private LinearLayout contentLayout;
    public NavigationView navigationView;
    private AdView mAdView;

    // layout parameter
    private String whereCurrentLayout = "";
    private Handler handler;

    public MainActivity() {
        backKeyPressedTime = 0;
        deviceWidth = 0;
        deviceHeight = 0;
    }
    
    @SuppressLint("HandlerLeak")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        thisActivity = this;

        dbHelper = new DBHelper(getApplicationContext(), "tradeItem.db", null, 3);
        tradeDb = dbHelper.getReadableDatabase();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, getString(R.string.firebase_project_id));
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, getString(R.string.firebase_project_name));
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        deviceWidth = getPixelToDp(this, displayMetrics.widthPixels);
        deviceHeight = getPixelToDp(this, displayMetrics.heightPixels);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitleView = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        contentView = (LinearLayout) findViewById(R.id.contentView);
        contentLayout = findViewById(R.id.contentLayout);

        mAdView = findViewById(R.id.adView);

        // 광고 세팅 및 화면 세팅
        setAdViewAndShow();

        firstScreen();
    }

    /**
     * 광고 및 화면 세팅 호출
     */
    private void setAdViewAndShow(){

        // 광고
        MobileAds.initialize(this, getString(R.string.ad_unit_id));

        // .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
                mAdView.setVisibility(View.GONE);
                mAdView.pause();
            }
        });

        Timer m_timer = new Timer(true);
        TimerTask m_task = new TimerTask() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override public void run() {
                        // 현재 UI 스레드가 아니기 때문에 메시지 큐에 Runnable을 등록 함
                        runOnUiThread(new Runnable() {
                            public void run() {
                                contentView.getLayoutParams().height = contentLayout.getHeight() - mAdView.getHeight();
                                contentView.requestLayout();
                            }
                        });
                    }
                }).start();
            }
        };
        m_timer.schedule(m_task, 1000);
    }

    /**
     * 맨 처음 화면 지정해주기
     */
    public void firstScreen(){
        if(contentView != null) {
            contentView.removeAllViews();
        }
        HomeLayout homeLayout = new HomeLayout(this);
        contentView.addView(homeLayout);
        homeLayout.mainActivity = this;
        homeLayout.initialization(null);
        homeLayout.setData(null);

        toolbarTitleView.setText("마비노기 유틸도우미");
        whereCurrentLayout = "home";
    }
    
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if("home".equals(whereCurrentLayout)) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(Gravity.START);
                return;
            }
            if (System.currentTimeMillis() > (backKeyPressedTime + 2000)) {
                backKeyPressedTime = System.currentTimeMillis();
                showGuide();
                return;
            }
            if (System.currentTimeMillis() <= (backKeyPressedTime + 2000)) {
                finish();
                toast.cancel();
            }
        }else{
            if(contentView != null) {
                contentView.removeAllViews();
            }
            HomeLayout homeLayout = new HomeLayout(this);
            contentView.addView(homeLayout);
            homeLayout.initialization(null);
            homeLayout.setData(null);
            homeLayout.mainActivity = this;

            toolbarTitleView.setText("마비노기 유틸도우미");
            navigationView.getMenu().getItem(0).setChecked(true);
            whereCurrentLayout = "home";
        }
    }
    
    public void showGuide() {
        toast = Toast.makeText(this, "앱 종료를 하시려면 '뒤로 가기'를 한번 더 눌러주십시오.", Toast.LENGTH_SHORT);
        toast.show();
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        boolean isScreenTrans = false;
        if(id == R.id.action_history) {
            isScreenTrans = true;
            if(contentView != null) {
                contentView.removeAllViews();
            }
            HistoryLayout historyLayout = new HistoryLayout(this);
            contentView.addView(historyLayout);
            historyLayout.initialization(null);
            historyLayout.setData(null);

            toolbarTitleView.setText("히스토리");
            whereCurrentLayout = "history";
        }/*else if(id == R.id.action_settings){
            isScreenTrans = true;
            return true;
        }*/

        // 화면 전환이 됬을 경우 네비게이션 셀렉트 제거
        if(isScreenTrans) {
            for(int menuIndex = 0 ; menuIndex < navigationView.getMenu().size() ; menuIndex++){
                navigationView.getMenu().getItem(menuIndex).setChecked(false);
            }
        }

        return super.onOptionsItemSelected(item);
    }
    
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.nav_notice) {
            if(contentView != null) {
                contentView.removeAllViews();
            }
            HomeLayout homeLayout = new HomeLayout(this);
            contentView.addView(homeLayout);
            homeLayout.initialization(null);
            homeLayout.setData(null);
            homeLayout.mainActivity = this;

            toolbarTitleView.setText("마비노기 유틸도우미");
            whereCurrentLayout = "home";
        }else if(id == R.id.nav_erg) {
            if(contentView != null) {
                contentView.removeAllViews();
            }
            ErgLayout ergLayout = new ErgLayout(this);
            contentView.addView(ergLayout);
            ergLayout.mainActivity = this;
            ergLayout.setData(tradeDb, null);
            ergLayout.initialization(null);

            toolbarTitleView.setText("에르그도우미");
            whereCurrentLayout = "erg";
        }else if(id == R.id.nav_trade) {
            if(contentView != null) {
                contentView.removeAllViews();
            }
            TradeLayout tradeLayout = new TradeLayout(this);
            contentView.addView(tradeLayout);
            tradeLayout.initialization(null);
            tradeLayout.setData(tradeDb, null);
            tradeLayout.tradeLayoutAdd();

            toolbarTitleView.setText("교역도우미");
            whereCurrentLayout = "trade";
        }else if(id == R.id.nav_music) {
            if(contentView != null) {
                contentView.removeAllViews();
            }
            MusicLayout musicLayout = new MusicLayout(this);
            contentView.addView(musicLayout);
            musicLayout.initialization(null);
            musicLayout.setData(tradeDb, null);
            musicLayout.mainActivity = this;

            toolbarTitleView.setText("연주도우미");
            whereCurrentLayout = "music";
        }/*else if(id == R.id.nav_test) {
            if(contentView != null) {
                contentView.removeAllViews();
            }
            TestLayout testLayout = new TestLayout(this);
            contentView.addView(testLayout);
            testLayout.mainActivity = this;
            testLayout.initialization(null);
            testLayout.setData(tradeDb, null);

            toolbarTitleView.setText("테스트도우미");
            whereCurrentLayout = "test";
        }*/

        /*
        else if(id == R.id.nav_title) {
            if (contentView != null) {
                contentView.removeAllViews();
            }
            TitleLayout titleLayout = new TitleLayout(this);
            contentView.addView(titleLayout);
            titleLayout.setData(tradeDb, null);
            titleLayout.initialization(null);
        }*/

        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(Gravity.START);
        return true;
    }
    
    public void noticeDetail(int position, View view, ArrayList arrayList) {
        NoticeItem noticeItem = (NoticeItem)arrayList.get(position);
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("noticeItem", noticeItem);
        startActivity(intent);
    }

    /**
     * 타 레이아웃에서 다른 레이아웃으로 이동
     * @param gubun
     */
    public void moveMenuForLayout(String gubun){
        if(gubun.equals("home")) {
            if(contentView != null) {
                contentView.removeAllViews();
            }
            HomeLayout homeLayout = new HomeLayout(this);
            contentView.addView(homeLayout);
            homeLayout.mainActivity = this;
            homeLayout.initialization(null);
            homeLayout.setData(null);

            toolbarTitleView.setText("마비노기 유틸도우미");
            whereCurrentLayout = "home";
        }else if(gubun.equals("erg")) {
            if(contentView != null) {
                contentView.removeAllViews();
            }
            ErgLayout ergLayout = new ErgLayout(this);
            contentView.addView(ergLayout);
            ergLayout.mainActivity = this;
            ergLayout.setData(tradeDb, null);
            ergLayout.initialization(null);

            toolbarTitleView.setText("에르그도우미");
            whereCurrentLayout = "erg";
        }else if(gubun.equals("trade")) {
            if(contentView != null) {
                contentView.removeAllViews();
            }
            TradeLayout tradeLayout = new TradeLayout(this);
            contentView.addView(tradeLayout);
            tradeLayout.initialization(null);
            tradeLayout.setData(tradeDb, null);
            tradeLayout.tradeLayoutAdd();

            toolbarTitleView.setText("교역도우미");
            whereCurrentLayout = "trade";
        }else if(gubun.equals("music")) {
            if(contentView != null) {
                contentView.removeAllViews();
            }
            MusicLayout musicLayout = new MusicLayout(this);
            contentView.addView(musicLayout);
            musicLayout.mainActivity = this;
            musicLayout.initialization(null);
            musicLayout.setData(tradeDb, null);

            toolbarTitleView.setText("연주도우미");
            whereCurrentLayout = "music";
        }
    }

    /**
     * DP -> PX
     * @param context
     * @param DP
     * @return
     */
    public int getDpToPixel(Context context, int DP) {
        float px = 0;
        try {
            px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DP, context.getResources().getDisplayMetrics());
        } catch (Exception e) {

        }

        return (int) px;
    }

    /**
     * PX -> DP
     * @param context
     * @param pixel
     * @return
     */
    public int getPixelToDp(Context context, int pixel) {
        float dp = 0;
        try {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            dp = pixel / (metrics.densityDpi / 160f);
        } catch (Exception e) {

        }
        return (int) dp;
    }
}
