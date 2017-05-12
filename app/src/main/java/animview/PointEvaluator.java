package animview;

import android.animation.TypeEvaluator;

/**
 * Created by Administrator on 2016/10/25.
 * ValueAnimator的高级用法
 */
public class PointEvaluator implements TypeEvaluator {
    /**
     * evaluate()方法当中传入了三个参数，
     * @param v 第一个参数fraction非常重要，这个参数用于表示动画的完成度的，我们应该根据它来计算当前动画的值应该是多少
     * @param o 表示动画的初始值
     * @param t1 表示动画的初始值和结束值
     * @return
     */
    @Override
    public Object evaluate(float v, Object o, Object t1) {
        Point startPoint = (Point) o;
        Point endPoint = (Point) t1;
        float x=startPoint.getX()+v*(endPoint.getX()-startPoint.getX());
        float y=startPoint.getY()+v*(endPoint.getY()-startPoint.getY());
        Point point=new Point(x,y);
        return point;
    }
}
