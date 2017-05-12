package animview;

import android.animation.TypeEvaluator;

/**
 * Created by Administrator on 2016/10/25.
 * 设置颜色改变的 TypeEvaluator
 */
public class ColorEvaluator implements TypeEvaluator {
    private int mCurrentRed=-1;
    private int mCurrentGreen=-1;
    private int mCurrentBlue=-1;
    @Override
    public Object evaluate(float v, Object o, Object t1) {
        String startColor = (String) o;
        String endColor = (String) t1;
        int startRed = Integer.parseInt(startColor.substring(1, 3), 16);
        int startGreen = Integer.parseInt(startColor.substring(3, 5), 16);
        int startBlue = Integer.parseInt(startColor.substring(5, 7), 16);
        int endRed = Integer.parseInt(endColor.substring(1, 3), 16);
        int endGreen = Integer.parseInt(endColor.substring(3, 5), 16);
        int endBlue = Integer.parseInt(endColor.substring(5, 7), 16);
        //初始化颜色的值
        if (mCurrentRed == -1) {
            mCurrentRed = startRed;
        }
        if (mCurrentGreen == -1) {
            mCurrentGreen = startGreen;
        }
        if (mCurrentBlue == -1) {
            mCurrentBlue = startBlue;
        }
        //计算初始颜色和最后颜色的差值
        int redDiff = Math.abs(startRed - endRed);
        int greenDiff = Math.abs(startGreen - endGreen);
        int blueDiff = Math.abs(startBlue - endBlue);
        int colorDiff = redDiff + greenDiff + blueDiff;
            if (mCurrentRed != endRed) {
                mCurrentRed = getCuttentColor(startRed, endRed, colorDiff, 0, v);
            } else if (mCurrentGreen != endGreen) {
                mCurrentGreen = getCuttentColor(startGreen, endGreen, colorDiff, redDiff, v);
            } else if (mCurrentBlue != endBlue) {
                mCurrentBlue = getCuttentColor(startBlue, endBlue, colorDiff, redDiff + greenDiff, v);
            }
            //计算出当前颜色的值
            String currentColor = "#" + getHexString(mCurrentRed) + getHexString(mCurrentBlue) + getHexString(mCurrentGreen);
            return currentColor;
        }


        /**
         * 根据v的值来计算当前颜色
         *
         * @param v
         * @return
         */

    private int getCuttentColor(int startColor, int endColor, int colorDiff, int offset, float v) {
        int currentColor;
        if (startColor > endColor) {
            currentColor = (int) (startColor - (v * colorDiff - offset));
            if (currentColor < endColor) {
                currentColor = endColor;
            }
        } else {
            currentColor = (int) (startColor + (v * colorDiff - offset));
            if (currentColor < endColor) {
                currentColor = endColor;
            }
        }
        return currentColor;
        }

    /**
     * 将10进制转化为16进制
     * @param value
     * @return
     */
    private String getHexString(int value) {
        String s = Integer.toHexString(value);
        if (s.length()==1){
            s="0"+s;
        }
        return s;
    }
}
