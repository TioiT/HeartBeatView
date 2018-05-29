package com.lish.heartbeatview.twoStep;

import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lish.heartbeatview.R;

public class HeartBeatTwoActivity extends AppCompatActivity {

    private HeartBeatTwoView heartBeatTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_beat_two);
        heartBeatTwo = findViewById(R.id.heartBeatTwo);
        init();
    }

    private void init() {
        initPathPoints();

        heartBeatTwo.startAnimation();
    }
    /**
     * 初始化心跳的轨迹
     * @return
     */
    private void initPathPoints() {
        Path path = new Path();
        path.moveTo(0,960);
        path.lineTo(200,960);
        path.lineTo(220,900);
        path.lineTo(230,1000);
        path.lineTo(270,800);
        path.lineTo(320,1200);
        path.lineTo(340,890);
        path.lineTo(360,1050);
        path.lineTo(388,820);
        path.lineTo(420,960);
        path.lineTo(520,960);

        path.lineTo(530,900);
        path.lineTo(550,1000);
        path.lineTo(570,800);
        path.lineTo(620,1200);
        path.lineTo(640,890);
        path.lineTo(660,1050);
        path.lineTo(688,820);
        path.lineTo(720,960);
        path.lineTo(740,960);
        path.lineTo(760,680);
        path.lineTo(800,1050);
        path.lineTo(820,960);
        path.lineTo(1080,960);
        heartBeatTwo.setPath(path);
    }
}
