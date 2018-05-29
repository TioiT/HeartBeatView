package com.lish.heartbeatview.twoStep;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class HeartBeatTwoView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private int mresetColor;    //移动后的轨迹颜色，灰色
    private int mBeatColor;     //心跳的颜色，红色
    private Paint paint;
    private PointsFactoryTwo pointsFactory;
    private SurfaceHolder mSurfaceHolder;
    private long mAnimationDuration;
    private long mAnimationStartDelay;
    private ValueAnimator mValueAnimator;
    private boolean animationStarted;
    private float redStartIndex;
    private float[] redRangePoint;
    private boolean isDrawing;
    private float grayStartIndex;
    private float[] grayRangePoint;
    private float grayEndIndex;
    private float redEndIndex;

    public HeartBeatTwoView(Context context) {
        this(context, null);
    }

    public HeartBeatTwoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeartBeatTwoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //想让SurfaveView显示图片或者视频必须要调用 setZOrderOnTop 方法，
        // 把SurfaceView置于Activity显示窗口的最顶层才能正常显示
        setZOrderOnTop(true);
        //初始化surfaceHolder
        mSurfaceHolder = getHolder();
        mSurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
        mSurfaceHolder.addCallback(this);
        //初始化 paint
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);// 开启抗锯齿
        paint.setStrokeWidth(5);

        //定义两种颜色，心跳颜色-红，轨迹颜色-灰
        mBeatColor = Color.RED;
        mresetColor = Color.GRAY;

        //定义动画时间和延时时间
        mAnimationDuration = 7000L;
        mAnimationStartDelay = 1000L;
    }

    /**
     * 设置心跳轨迹
     *
     * @param path
     */
    public void setPath(Path path) {
        pointsFactory = new PointsFactoryTwo(path);
    }


    /**
     * 开始心跳动画
     */
    public void startAnimation() {
        if (!animationStarted) {
            animationStarted = true;
            mValueAnimator = ValueAnimator.ofFloat(-1.3F, 1F).setDuration(mAnimationDuration);
            mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
            mValueAnimator.setStartDelay(mAnimationStartDelay);
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float currentValue = (float) animation.getAnimatedValue();
                    grayEndIndex = currentValue;
                    redStartIndex = grayStartIndex = grayEndIndex + 1.3F;
                    redEndIndex = grayEndIndex + 1.0F;
                    if(grayEndIndex < 0) {
                      grayEndIndex = 0;
                    }
                    if(redEndIndex < 0) {
                        redEndIndex = 0;
                    }
                    if(redStartIndex > 1) {
                        redStartIndex = grayStartIndex = 1;
                    }
                    redRangePoint = getRangePoint(redStartIndex, redEndIndex);
                    grayRangePoint = getRangePoint(grayStartIndex, grayEndIndex);
                }
            });

            mValueAnimator.start();
        }

    }

    /**
     * 获取指定范围内的点
     *
     * @param start 线条的起始索引
     * @param end   线条的结束索引
     *              由这两个索引确定点的范围
     * @return
     */
    public float[] getRangePoint(float start, float end) {
        return pointsFactory.getRangePoints(start, end);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        restart();
    }

    private void restart() {
        isDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stop();
    }

    /**
     * 停止动画
     */
    private void stop() {
        isDrawing = false;
        if(mValueAnimator != null ) {
            if(mValueAnimator.isRunning()) {
                mValueAnimator.cancel();
            }
        }
    }

    @Override
    public void run() {
        while (isDrawing){
            Canvas canvas = mSurfaceHolder.lockCanvas();
            if (canvas == null) return;
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

            if(grayRangePoint != null) {
                if(grayRangePoint.length > 0) {
                    paint.setColor(mresetColor);
                    canvas.drawPoints(grayRangePoint,paint);
                }
            }
            if(redRangePoint != null) {
                if(redRangePoint.length > 0) {
                    paint.setColor(mBeatColor);
                    canvas.drawPoints(redRangePoint,paint);
                }
            }
            mSurfaceHolder.unlockCanvasAndPost(canvas);
        }

    }
}
