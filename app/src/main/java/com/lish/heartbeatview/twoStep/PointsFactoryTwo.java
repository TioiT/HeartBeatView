package com.lish.heartbeatview.twoStep;

import android.graphics.Path;
import android.graphics.PathMeasure;

import java.util.Arrays;

public class PointsFactoryTwo {

    private static final int POINTS_SPACE = 1;//点间距
    private int totlePoints;    //总点数
    private float[] pointsData; //所有点集合
    private float[] tempPoints; //临时存放点集合

    public PointsFactoryTwo(Path path) {
        initPath(path);
    }

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


        int startIndex = (int) (totlePoints * start);
        int endIndex = (int) (totlePoints * end);
        if (startIndex <= endIndex)
            return null;
        //必须是偶数，因为需要float[]{x,y}这样x和y组成一个坐标点
        if (startIndex % 2 != 0) {
            //直接减，不用担心 < 0  因为0是偶数，
            --startIndex;
        }
        if (endIndex % 2 != 0) {
            //不用检查越界
            ++endIndex;
        }
        //根据起止点裁剪
        float[] floats = Arrays.copyOfRange(pointsData, endIndex, startIndex);
        if(floats != null) {
            return  floats;
        }else{
            return null;
        }
    }
    public float[] getPointsData(){
        return pointsData;
    }

    public int getPointDataSize(){
        return pointsData.length;
    }
}
