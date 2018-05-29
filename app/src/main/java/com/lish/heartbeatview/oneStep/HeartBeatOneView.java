package com.lish.heartbeatview.oneStep;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


public class HeartBeatOneView extends View {
    private int mresetColor;    //移动后的轨迹颜色，灰色
    private int mBeatColor;     //心跳的颜色，红色
    private Paint paint;
    private PointsFactoryOne pointsFactory;
    private float[] tempPointsData;

    public HeartBeatOneView(Context context) {
        this(context,null);
    }

    public HeartBeatOneView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HeartBeatOneView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
        mBeatColor = Color.RED;
        mresetColor = Color.GRAY;
    }

    public void setPath(Path path){
        pointsFactory = new PointsFactoryOne(path);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        float[] pointsData = pointsFactory.getPointsData();
        paint.setColor(mresetColor);
        canvas.drawPoints(pointsData,paint);

        if(tempPointsData != null) {
            paint.setColor(mBeatColor);
            canvas.drawPoints(tempPointsData,paint);
        }

    }

    public void setPointsData(float[] data){
        tempPointsData = data;
        postInvalidate();

    }
    public float[] getRangePoint(float start,float end){
        return pointsFactory.getRangePoints(start, end);
    }
}
