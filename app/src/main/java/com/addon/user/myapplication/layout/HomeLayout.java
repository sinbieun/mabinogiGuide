/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package com.addon.user.myapplication.layout;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.addon.user.myapplication.MainActivity;
import com.addon.user.myapplication.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class HomeLayout extends BaseLinearLayout {
    public RelativeLayout layout;
    public MainActivity mainActivity;

    // VIEW
    private TextView errinTimeTextView;
    private TextView errinWeekdayTextView;
    private TextView errinWeekdayCommentTextView;
    private TextView mainMenu1;
    private TextView mainMenu2;
    private TextView mainMenu3;

    // Handler
    private Handler handler;

    public HomeLayout(Context context) {
        super(context);
    }

    public void initialization(Object[] obj) {

        layout = (RelativeLayout)getView(R.layout.home_layout);
        addView(layout);

        // VIEW SETTING
        setView();

        // EVENT SETTING
        setEvent();

        /*handler = new Handler()
        {
            public void handleMessage(Message msg)
            {
                // 원래 하고싶었던 일들 (UI변경작업 등...)
                // 1초 마다 실행하는 타이머 세팅
                Timer m_timer = new Timer(true);
                TimerTask m_task = new TimerTask() {
                    @Override
                    public void run() {
                        errinTimeUpdate();
                    }
                };
                m_timer.schedule(m_task, 0, 1000);
            }
        };

        new Thread(){
            public void run()
            {
                Message msg = handler.obtainMessage();
                handler.sendMessage(msg);
            }
        }.start();*/

        // 1초 마다 실행하는 타이머 세팅
        Timer m_timer = new Timer(true);
        TimerTask m_task = new TimerTask() {
            @Override
            public void run() {
                errinTimeUpdate();
            }
        };
        m_timer.schedule(m_task, 0, 1000);

        // 에린의 요일 세팅
        setErrinWeekay();
    }

    /**
     * 뷰 세팅
     */
    private void setView(){
        // 에린 시간
        errinTimeTextView = findViewById(R.id.errinTime);

        // 에린 요일
        errinWeekdayTextView = findViewById(R.id.errinWeekday);
        errinWeekdayCommentTextView = findViewById(R.id.errinWeekdayComment);

        // 메뉴 버튼
        mainMenu1 = findViewById(R.id.main_menu_1);
        mainMenu2 = findViewById(R.id.main_menu_2);
        mainMenu3 = findViewById(R.id.main_menu_3);
    }

    /**
     * 이벤트 세팅
     */
    private void setEvent(){
        mainMenu1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.navigationView.getMenu().getItem(1).setChecked(true);
                mainActivity.moveMenuForLayout("trade");
            }
        });

        mainMenu2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.navigationView.getMenu().getItem(2).setChecked(true);
                mainActivity.moveMenuForLayout("erg");
            }
        });

        mainMenu3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.navigationView.getMenu().getItem(3).setChecked(true);
                mainActivity.moveMenuForLayout("music");
            }
        });
    }
    
    public void setData(SQLiteDatabase db, Object[] obj) {
    }
    
    public void setData(Object[] obj) {
    }

    /**
     * 에린 요일 세팅
     */
    private void setErrinWeekay(){
        switch (getDateDay()){
            case "월요일" :
                errinWeekdayTextView.setText("알반 에일레르");
                errinWeekdayCommentTextView.setText("생산직 스킬의 성공 가능성이 높아진다.\n" +
                        "생활계열 스킬의 랭크업 보너스가 증가한다.(110%)\n" +
                        "생산품의 품질이 증가한다.");
                break;
            case "화요일" :
                errinWeekdayTextView.setText("벨테인");
                errinWeekdayCommentTextView.setText("같은 아이템을 던져도 던전의 모양까지 바뀐다.\n" +
                        "던전에서 아이템이 나올 가능성이 높아진다.\n" +
                        "전투관련 스킬의 랭크업 보너스가 증가한다.");
                break;
            case "수요일" :
                errinWeekdayTextView.setText("알반 헤루인");
                errinWeekdayCommentTextView.setText("채집 확률 10% 증가\n" +
                        "상점에서 5% 물건 할인\n" +
                        "은행 수수료 5% 감소\n" +
                        "스킬의 완전수련시 얻는 경험치가 증가한다.(110%)");
                break;
            case "목요일" :
                errinWeekdayTextView.setText("루나사");
                errinWeekdayCommentTextView.setText("인챈트의 성공률이 높아진다.\n" +
                        "마법 관련 스킬의 랭크업 보너스가 증가한다.\n" +
                        "장비를 사용하여 얻는 숙련도가 증가한다.(1.2배)");
                break;
            case "금요일" :
                errinWeekdayTextView.setText("알반 엘베드");
                errinWeekdayCommentTextView.setText("행동불능이 되었을 때 잃는 패널티가 줄어든다.\n" +
                        "모든 포션의 효과가 높아진다.(1.5배)\n" +
                        "아르바이트를 완수했을 때의 보상이 높아진다.");
                break;
            case "토요일" :
                errinWeekdayTextView.setText("삼하인");
                errinWeekdayCommentTextView.setText("나이를 먹고 성장에 따른 AP를 얻는다.\n" +
                        "음식의 기본 효과가 높아진다.\n" +
                        "L로드의 효과가 증가한다.\n" +
                        "조금 움직이는 동물도 스케치할 수 있다.\n" +
                        "연금술 관련 스킬의 성공가능성이 증가한다.");
                break;
            case "일요일" :
                errinWeekdayTextView.setText("임볼릭");
                errinWeekdayCommentTextView.setText("크리티컬 히트의 발생률이 높아진다.\n" +
                        "럭키피니시가 등장할 확률이 높아진다.\n" +
                        "연주 성공률이 증가한다.\n" +
                        "마법 음악 성공률이 증가한다.\n" +
                        "마법으로 길들일 확률이 증가한다.");
                break;
        }
    }

    /**
     * 에린 시간 업데이트
     */
    private void errinTimeUpdate(){
        // 현재 시간 가져오기
        long now = System.currentTimeMillis();
        Date currentTime = new Date(now);

        int year = currentTime.getYear();
        int month = currentTime.getMonth();
        int day = currentTime.getDay();
        int hrs = currentTime.getHours();
        int min = currentTime.getMinutes();
        int sec = currentTime.getSeconds();

        // 현재 시간 글자 세팅
        String hrsStr = "0";
        String minStr = "0";
        String secStr = "0";

        // 현재 시간이 2글자가 아니면 다시 설정
        if (hrs < 10) hrsStr = "0" + String.valueOf(hrs);
        if (min < 10) minStr = "0" + String.valueOf(min);
        if (sec < 10) secStr = "0" + String.valueOf(sec);

        // 자정 시간 가져오기
        Date midnightDate = new Date(year, month, day, 0, 0, 0);
        long midnightTime = midnightDate.getTime();

        // 에린시각 표시갱신 (에린의 하루는 현실시각으로 36분, 2160초)
        int erinnTime = Math.round((now - midnightTime) / 1000) % 2160;
        errinTimeTextView.setText(sec2minsec(Math.round(erinnTime * 2 / 3)));
    }

    /**
     * 에린 시간 계산
     * @param secs
     * @return
     */
    private String sec2minsec(int secs){

        double hrsDouble = Math.floor(secs / 60);
        double minDouble = secs % 60;

        String hrsStr = String.valueOf(hrsDouble).replace(".0","");
        String minStr = String.valueOf(minDouble).replace(".0","");

        if (hrsDouble < 10) hrsStr = "0" + hrsStr;
        if (minDouble < 10) minStr = "0" + minStr;

        return hrsStr + ":" + minStr;
    }

    /**
     * 요일 가져오기
     * @return
     * @throws Exception
     */
    private String getDateDay(){
        String weekDay;
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.KOREA);

        Calendar calendar = Calendar.getInstance();
        weekDay = dayFormat.format(calendar.getTime());

        //return weekDay;

        return "화요일";
    }
}
