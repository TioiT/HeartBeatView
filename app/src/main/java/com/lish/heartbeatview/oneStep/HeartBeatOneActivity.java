package com.lish.heartbeatview.oneStep;

import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.lish.heartbeatview.R;


public class HeartBeatOneActivity extends AppCompatActivity {

    private SeekBar sb;
    private HeartBeatOneView heartBeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_beat);
        heartBeat = findViewById(R.id.heartBeat);
        sb = findViewById(R.id.sb_one);
        init();
        initLiatener();
    }

    /**
     * 初始化 seekbar，监听到进度改变时，设置进度
     */
    private void initLiatener() {
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float[] rangePoint = heartBeat.getRangePoint(0, (float) progress / 100);
                heartBeat.setPointsData(rangePoint);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void init() {
        Path path = initPathPoint();
        heartBeat.setPath(path);
    }

    /**
     * 初始化心跳的轨迹
     * @return
     */
    private Path initPathPoint() {
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
        path.lineTo(440,960);
        path.lineTo(460,680);
        path.lineTo(500,1050);

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
        return path;
    }
}
