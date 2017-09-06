package com.example.apple.drawletter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import model.TouchPoint;
import tools.Utils;

public class MainActivity extends AppCompatActivity {

    private DrawView mDrawView;
    private List<TouchPoint> touchPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mDrawView = (DrawView) findViewById(R.id.drawView);

        touchPoints = new ArrayList<>();
        touchPoints.add(new TouchPoint(.74f, .09f).setRes(R.mipmap.f0002));//这边是百分比
        touchPoints.add(new TouchPoint(.61f, .05f).setRes(R.mipmap.f0003));
        touchPoints.add(new TouchPoint(.48f, .19f).setRes(R.mipmap.f0004));
        touchPoints.add(new TouchPoint(.48f, .54f).setRes(R.mipmap.f0005));

        touchPoints.add(new TouchPoint(.48f, .71f).setRes(R.mipmap.f0006));
        touchPoints.add(new TouchPoint(.48f, .88f).setRes(R.mipmap.f0007));
        touchPoints.add(new TouchPoint(.29f, .42f).setRes(R.mipmap.f0008));
        touchPoints.add(new TouchPoint(.48f, .42f).setRes(R.mipmap.f0009));
        touchPoints.add(new TouchPoint(.62f, .42f).setRes(R.mipmap.f0010));
        mDrawView.post(new Runnable() {
            @Override
            public void run() {
                //转化成相对坐标--宽高一样（正方形）
                float drawWidth = mDrawView.getWidth();

                for (TouchPoint touchPoint : touchPoints) {
                    touchPoint.x = drawWidth * touchPoint.x;
                    touchPoint.y = drawWidth * touchPoint.y;
                }

                mDrawView.setTouchList(touchPoints);

                //误差 -- 当前8等分
                mDrawView.setCenterDistance((int) (drawWidth / 10));

                mDrawView.setOnTouchSuccessListener(new DrawView.IOnTouchSuccess() {
                    @Override
                    public void onTouchSuccess() {
                        Utils.MyToast("全部被触发，可以进行其他事件");
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        mDrawView.clear();
        super.onBackPressed();
    }
}
