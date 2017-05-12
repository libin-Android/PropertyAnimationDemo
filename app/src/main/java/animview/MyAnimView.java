package animview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/10/25.
 * 自定义的可以实现跟随手势滑动的动画的view
 */
public class MyAnimView extends View {
    private Paint mPaint;
    private Point currentPoint;
    public static final float RADIUS=50f;
    private String TAG=MyAnimView.class.getSimpleName();
    private float xMove;
    private float yMove;

    public MyAnimView(Context context) {
        this(context,null);
    }

    public MyAnimView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
       if (currentPoint==null){
           currentPoint=new Point(RADIUS,RADIUS);
           drawCircle(canvas);
           startMyAnimation(RADIUS,RADIUS,RADIUS,RADIUS);
       }else {
           drawCircle(canvas);
       }
    }
    private void drawCircle(Canvas canvas) {
        float x = currentPoint.getX();
        float y = currentPoint.getY();
        canvas.drawCircle(x,y,RADIUS,mPaint);
    }

    private void startMyAnimation(float startX,float startY,float endX,float endY) {
        Log.e(TAG, "startMyAnimation: " );
        Point startPoint =new Point(startX,startY);
        Point endPoint=new Point(endX,endY);
        //使用自定义的 PointEvaluator()，此处相当于给 PointEvaluator()类的 public Object evaluate(float v, Object o, Object t1) {}传值
        final ValueAnimator anim=ValueAnimator.ofObject(new PointEvaluator(),startPoint,endPoint);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                currentPoint= ((Point) valueAnimator.getAnimatedValue());
                invalidate();
            }
        });
        ObjectAnimator animator=ObjectAnimator.ofObject(this,"color",new ColorEvaluator(),"#00FFFF","#FF0000");
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.play(anim).with(animator);
        animatorSet.setDuration(2000);
        animatorSet.start();
    }

    /**
     * 要监听 MotionEvent.ACTION_MOVE:和MotionEvent.ACTION_UP:必须在xml文件中设置
     * android:clickable="true"
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent: " );
        float  eventX=xMove;
        float  eventY=yMove;
        Log.e(TAG, "eventX"+eventX);
        Log.e(TAG, "eventY"+eventY);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
//                xDown = event.getX();
//                yDown = event.getY();
//                Log.e(TAG, "xDown: "+xDown);
//                Log.e(TAG, "yDown: "+yDown);
//                startMyAnimation(eventX,eventY,xDown,yDown);
//                Log.e(TAG, "onTouchEvent: ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                xMove = event.getX();
                yMove = event.getY();
                startMyAnimation(eventX,eventY,xMove,yMove);
                Log.e(TAG, "onTouchEvent: ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "onTouchEvent: ACTION_UP");
                break;
        }
        return super.onTouchEvent(event);
    }
}
