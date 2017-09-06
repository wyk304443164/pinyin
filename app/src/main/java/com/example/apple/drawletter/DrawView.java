package com.example.apple.drawletter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;

import java.util.List;

import model.TouchPoint;
import tools.Utils;

/**
 * Created by WYK on 2017/8/29.
 * 所有的业务逻辑在这边
 */

public class DrawView extends DrawView_old2 {

    //需要被碰撞到的位置
    private List<TouchPoint> touchList;

    private int centerDistance = 5;//默认的 误差范围 *2 左右 5px *2

    private IOnTouchSuccess mIOnTouchSuccess;//按结束的回调

    private Bitmap mBitmap;//连线的图标

    public DrawView(Context context, AttributeSet attr) {
        super(context, attr);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
    }

    @Override
    void onTouchXY(float x, float y) {

        Log.d("xy", (double) x / ((double) getWidth()) + " " + (double) y / ((double) getHeight()));

        for (int i = 0; i < touchList.size(); i++) {
            TouchPoint touchPoint = touchList.get(i);
            //在范围内:预先设置的坐标点，加上误差在范围内
            if (touchPoint.x < (x + centerDistance) && touchPoint.x > (x - centerDistance)
                    && touchPoint.y < (y + centerDistance) && touchPoint.y > (y - centerDistance)) {
                //如果是第一个,或者上一个已经被选中了,并且当前的没有被选中,那么就true
                if ((i == 0 || touchList.get(i - 1).isTouched) && !touchPoint.isTouched) {
                    //选中
                    touchPoint.isTouched = true;

                    Utils.MyToast("选中 " + i);

                    setBackground(getResources().getDrawable(touchPoint.res));

                    //判断是否已经按结束
                    if ((i == touchList.size() - 1) && mIOnTouchSuccess != null) {
                        //说明是最后一个
                        mIOnTouchSuccess.onTouchSuccess();
                    }
                }
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //开始把图标画上去
        int bitmapWidth = mBitmap.getWidth();
        int bitmapHeight = mBitmap.getHeight();
        for (int i = 0; i < touchList.size(); i++) {
            TouchPoint touchPoint = touchList.get(i);
            if (!touchPoint.isTouched) {
                //如果没有被选中过
                canvas.drawBitmap(mBitmap, touchPoint.x - bitmapWidth / 2, touchPoint.y - bitmapHeight / 2, null);
            }
        }
    }

    /**
     * 设置需要被点击到的位置
     *
     * @param touchList
     */

    public void setTouchList(List<TouchPoint> touchList) {
        this.touchList = touchList;
    }

    /**
     * 设置误差
     *
     * @param centerDistance
     */

    public void setCenterDistance(int centerDistance) {
        this.centerDistance = centerDistance;
    }

    /**
     * 设置结束的回调
     *
     * @param IOnTouchSuccess
     */

    public void setOnTouchSuccessListener(IOnTouchSuccess IOnTouchSuccess) {
        mIOnTouchSuccess = IOnTouchSuccess;
    }

    /**
     * 全部完成的回调
     */

    public interface IOnTouchSuccess {
        void onTouchSuccess();
    }

}
