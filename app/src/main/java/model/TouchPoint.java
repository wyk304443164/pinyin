package model;

/**
 * Created by WYK on 2017/8/29.
 */

public class TouchPoint {
    public float x;
    public float y;
    public boolean isTouched;//是不是被滑动过了
    public int res;

    public TouchPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public TouchPoint setRes(int res) {
        this.res = res;
        return this;
    }
}
