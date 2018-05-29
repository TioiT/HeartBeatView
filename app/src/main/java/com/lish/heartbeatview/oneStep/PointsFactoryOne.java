package com.lish.heartbeatview.oneStep;

import android.graphics.Path;
import android.graphics.PathMeasure;

import java.util.Arrays;

/**
 * 将心跳轨迹转化为点，并对点进行处理
 */
public class PointsFactoryOne {

    private static final int POINTS_SPACE = 1;//点间距
    private int totlePoints;    //总点数
    private float[] pointsData; //所有点的集合
    private float[] tempPoints; //临时存放点的集合

    public PointsFactoryOne(Path path) {
        initPath(path);
    }

    /**
     * 将 path 转化为 坐标点
     * @param path
     */
    private void initPath(Path path) {
        PathMeasure pathMeasure = new PathMeasure(path, false);
        float length = pathMeasure.getLength();
        totlePoints = (int) (length / POINTS_SPACE)+1;
        pointsData = new float[totlePoints * 2];
        tempPoints = new float[2];
        int index = 0;
        for (int i = 0; i < totlePoints; i++) {
            float lengthSpace = i * (length / (totlePoints - 1));
            pathMeasure.getPosTan(lengthSpace, tempPoints, null);
            pointsData[index] = tempPoints[0];
            pointsData[index+1] = tempPoints[1];
            index+=2;
        }
        totlePoints = pointsData.length;
    }

    /**
     * 拿到start和end之间的x,y数据
     * 获取范围内的数据点
     * @param start 开始百分比
     * @param end   结束百分比
     * @return 裁剪后的数据
     */
    float[] getRangePoints(float start, float end) {
        if (start >= end)
            return null;

        int startIndex = (int) (totlePoints * start);
        int endIndex = (int) (totlePoints * end);

        //必须是偶数，因为需要float[]{x,y}，这样x和y 才是一个点
        if (startIndex % 2 != 0) {
            //直接减，不用担心 < 0  0是偶数
            --startIndex;
        }
        if (endIndex % 2 != 0) {
            //不用检查越界
            ++endIndex;
        }
        //根据起始点，从所有点的集合数据中复制一份新的坐标点集合
        return Arrays.copyOfRange(pointsData, startIndex, endIndex);
    }
    public float[] getPointsData(){
        return pointsData;
    }

    public int getPointDataSize(){
        return pointsData.length;
    }
}
