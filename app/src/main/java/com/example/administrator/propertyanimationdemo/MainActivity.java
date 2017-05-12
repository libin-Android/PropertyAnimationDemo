package com.example.administrator.propertyanimationdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.TextView;

/**
 * 属性动画
 * ObjectAnimator是继承自ValueAnimator的，而ValueAnimator又是继承自Animator的
 * 因此不管是ValueAnimator还是ObjectAnimator都是可以使用addListener()这个方法的。
 * 另外AnimatorSet也是继承自Animator的，因此addListener()这个方法算是个通用的方法。
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG=MainActivity.class.getSimpleName();
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        Button initValueAnimator = (Button) findViewById(R.id.bt_initValueAnimator);
        Button initObjectAnimator = (Button) findViewById(R.id.bt_initObjectAnimator);
        Button initAnimatorSet = (Button) findViewById(R.id.bt_initAnimatorSet);
        Button valueAnimatorAdvanced = (Button) findViewById(R.id.valueAnimatorAdvanced);
        textView = (TextView) findViewById(R.id.textView);
        initValueAnimator.setOnClickListener(this);
        initObjectAnimator.setOnClickListener(this);
        initAnimatorSet.setOnClickListener(this);
        valueAnimatorAdvanced.setOnClickListener(this);
    }


    /**
     * ValueAnimator就会自动帮我们完成从初始值平滑地过渡到结束值这样的效果。
     * ValueAnimator还负责管理动画的播放次数、播放模式、以及对动画设置监听器等，确实是一个非常重要的类。
     *
     *
     * 另外ofFloat()方法当中是可以传入任意多个参数的，因此我们还可以构建出更加复杂的动画逻辑，
     * 比如说将一个值在5秒内从0过渡到5，再过渡到3，再过渡到10，就可以这样写：
     *  ValueAnimator anim = ValueAnimator.ofFloat(0f, 5f, 3f, 10f);
     *  anim.setDuration(5000);
     *  anim.start();
     *  我们还可以调用setStartDelay()方法来设置动画延迟播放的时间，
     *  调用setRepeatCount()方法来设置动画循环播放的次数
     *  调用setRepeatMode()方法来循环播放的模式，循环模式包括RESTART和REVERSE两种，分别表示重新播放和倒序播放的意思
     */
    private void initValueAnimator() {
    //比如说想要将一个值从0平滑过渡到1，时长300毫秒，就可以这样写：
        ValueAnimator anim = ValueAnimator.ofFloat(0f, 1f);
        anim.setDuration(300);
        //设置动画改变监听
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                Log.e(TAG, "onAnimationUpdate: "+animatedValue );

            }
        });
        anim.start();
    }

    /**
     * 相比于ValueAnimator，ObjectAnimator可能才是我们最常接触到的类，
     * 因为ValueAnimator只不过是对值进行了一个平滑的动画过渡，但我们实际使用到这种功能的场景好像并不多。
     * 而ObjectAnimator则就不同了，它是可以直接对任意对象的任意属性进行动画操作的，比如说View的alpha属性。
     *
     * 不过虽说ObjectAnimator会更加常用一些，但是它其实是继承自ValueAnimator的，底层的动画实现机制也是基于ValueAnimator来完成的
     */
    private void initObjectAnimator() {
     //这里如果我们想要将一个TextView在5秒中内从常规变换成全透明，再从全透明变换成常规，就可以这样写：
        //"rotation"旋转 中心点旋转
        //alpha 透明
        //translationX 为x方向上平移
        //scaleY 在垂直方向上缩放
        ObjectAnimator animator = ObjectAnimator.ofFloat(textView, "alpha", 1f, 0f, 1f);
        animator.setDuration(5000);
        animator.start();
        /**
         * 这里我们先是调用了TextView的getTranslationX()方法来获取到当前TextView的translationX的位置，
         * 然后ofFloat()方法的第二个参数传入"translationX"，紧接着后面三个参数用于告诉系统TextView应该怎么移动
         */
//        float curTranslationX = textView.getTranslationX();
//        ObjectAnimator animator2 = ObjectAnimator.ofFloat(textView, "translationX", curTranslationX, -500f, curTranslationX);
//        animator.setDuration(5000);
//        animator.start();
        /**
         * 这里将ofFloat()方法的第二个参数改成了"scaleY"，表示在垂直方向上进行缩放
         */
//        ObjectAnimator animator3 = ObjectAnimator.ofFloat(textView, "scaleY", 1f, 3f, 1f);
//        animator.setDuration(5000);
//        animator.start();
    }
    /**
     * 实现组合动画功能主要需要借助AnimatorSet这个类，这个类提供了一个play()方法，
     * 如果我们向这个方法中传入一个Animator对象(ValueAnimator或ObjectAnimator)将会返回一个AnimatorSet.Builder的实例，
     * AnimatorSet.Builder中包括以下四个方法：
     *  after(Animator anim)   将现有动画插入到传入的动画之后执行,括号内为传入的动画
     *  after(long delay)   将现有动画延迟指定毫秒后执行
     *  before(Animator anim)   将现有动画插入到传入的动画之前执行
     *  with(Animator anim)   将现有动画和传入的动画同时执行
     */
    private void initAnimatorSet() {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(textView, "translationX", -500f, 0f);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(textView, "rotation", 0f, 360f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(textView, "alpha", 1f, 0f, 1f);
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.play(rotation).with(alpha).after(translationX);
        animatorSet.setDuration(5000);
        /**
         * 动画的监听
         */
//        animatorSet.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animator) {
//                //onAnimationStart()方法会在动画开始的时候调用
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animator) {
//                //onAnimationEnd()方法会在动画结束的时候调用
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animator) {
//                //onAnimationCancel()方法会在动画被取消的时候调用。
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animator) {
//            //onAnimationRepeat()方法会在动画重复执行的时候调用，
//            }
//        });
        /**
         * *但是也许很多时候我们并不想要监听那么多个事件，
         * 可能我只想要监听动画结束这一个事件，那么每次都要将四个接口全部实现一遍就显得非常繁琐。
         * 没关系，为此Android提供了一个适配器类，叫作AnimatorListenerAdapter
         */
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationPause(Animator animation) {
                super.onAnimationPause(animation);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }

            @Override
            public void onAnimationResume(Animator animation) {
                super.onAnimationResume(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        animatorSet.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_initValueAnimator:
                initValueAnimator();
                break;
            case R.id.bt_initObjectAnimator:
                initObjectAnimator();
                break;
            case R.id.bt_initAnimatorSet:
                initAnimatorSet();
                break;
            case R.id.valueAnimatorAdvanced:
                Intent intent=new Intent(this,Main2Activity.class);
                startActivity(intent);
                break;
        }
    }
}
