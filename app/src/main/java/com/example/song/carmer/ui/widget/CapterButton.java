package com.example.song.carmer.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.song.carmer.R;

/**
 * Create by songdaren on 18-7-19.
 */
public class CapterButton extends View {
    private String TAG="CapterButton";
    private Paint mPaint;
    private float outside_circle_radius=100;
    private float inner_circle_radius=50;
    private float centerX;
    private float centerY;
    private float start_outside_circle_radius=100;
    private float end_outside_circle_radius=150;
    private float start_inner_circle_radius=50;
    private float end_inner_circle_radious=30;
    private float progress=0;
    private int outside_circle_color=Color.WHITE;
    private int inner_circle_color=Color.WHITE;
    private int arc_color=Color.BLUE;
    private ValueAnimator mAnimator=ValueAnimator.ofFloat(0,360);
    private int maxDuration=10;
    private Context mContext;
    public CapterButton(Context context) {
        this(context,null);
    }

    public CapterButton(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CapterButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initCapterButton(context, attrs, defStyleAttr);
    }
    private void initCapterButton(Context context,AttributeSet attributeSet,int defStyleAttr){
        TypedArray mTypeArray= context.obtainStyledAttributes(attributeSet, R.styleable.CapterButton,defStyleAttr,0);
        if(mTypeArray!=null){
            Log.i(TAG,"initCapterButton");
            outside_circle_radius=mTypeArray.getDimension(R.styleable.CapterButton_outside_circle_radius,100);
            outside_circle_color=mTypeArray.getColor(R.styleable.CapterButton_outside_circle_color,Color.WHITE);
            inner_circle_radius=mTypeArray.getDimension(R.styleable.CapterButton_inner_circle_radius,50);
            inner_circle_color=mTypeArray.getColor(R.styleable.CapterButton_inner_circle_color,Color.WHITE);
            start_outside_circle_radius=mTypeArray.getDimension(R.styleable.CapterButton_start_outside_circle_radius,100);
            end_outside_circle_radius=mTypeArray.getDimension(R.styleable.CapterButton_end_outside_circle_radius,150);
            start_inner_circle_radius=mTypeArray.getDimension(R.styleable.CapterButton_start_inner_circle_radius,50);
            end_inner_circle_radious=mTypeArray.getDimension(R.styleable.CapterButton_end_inner_circle_radious,30);
            arc_color=mTypeArray.getColor(R.styleable.CapterButton_arc_color,Color.BLUE);
        }
            mPaint=new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthmode=MeasureSpec.getMode(widthMeasureSpec);
        int widthsize=MeasureSpec.getSize(widthMeasureSpec);
        int heightmode=MeasureSpec.getMode(heightMeasureSpec);
        int heightsize=MeasureSpec.getSize(heightMeasureSpec);
        Log.i(TAG,"onMeasure------width:"+widthsize+"height:"+heightsize);
        switch (widthmode){
            case MeasureSpec.AT_MOST://根据父类大小匹配 wrap_content
                Log.i(TAG,"onMeasure----AT_MOST");
                widthsize=(int)(end_outside_circle_radius*2)+20;
                break;
            case MeasureSpec.EXACTLY://精确模式 指定精确的大小
                Log.i(TAG,"onMeasure-----EXACTLY");

                break;
            case MeasureSpec.UNSPECIFIED://未定义模式
                Log.i(TAG,"onMeasure----UNSPECIFIED");

                break;
        }
        switch (heightmode){
            case MeasureSpec.AT_MOST:
                heightsize=(int)(end_outside_circle_radius*2)+20;
                break;
            case MeasureSpec.EXACTLY:
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }

        centerX=widthsize/2;
        centerY=heightsize/2;
        Log.i(TAG,"outside_circle_radius2:"+outside_circle_radius);
        setMeasuredDimension(widthsize,heightsize);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG,"outside_circle_radius1:"+outside_circle_radius);
        centerX=getWidth()/2;
        centerY=getHeight()/2;

    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        Log.i(TAG,"outside_circle_radius3:"+outside_circle_radius);
        mPaint.setColor(outside_circle_color);
        mPaint.setAntiAlias(true);
        canvas.drawCircle(centerX,centerY,outside_circle_radius,mPaint);

        mPaint.setColor(inner_circle_color);
        mPaint.setAntiAlias(true);
        canvas.drawCircle(centerX,centerY,inner_circle_radius,mPaint);

        Paint arcPaint=new Paint();
        arcPaint.reset();
        arcPaint.setColor(arc_color);
        arcPaint.setAntiAlias(true);
        arcPaint.setStrokeWidth(10);
        arcPaint.setStyle(Paint.Style.STROKE);
        RectF mRectF=new RectF(centerX-outside_circle_radius,centerY-outside_circle_radius,centerX+outside_circle_radius,centerY+outside_circle_radius);
        canvas.drawArc(mRectF,-90,progress,false,arcPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Float dowmX=event.getX();
                float downY=event.getY();
                if(dowmX>(centerX-outside_circle_radius)&&dowmX<(centerX+outside_circle_radius)&&downY<(centerY+outside_circle_radius)&&downY>(centerY-outside_circle_radius))
                startAnimtor(start_outside_circle_radius,end_outside_circle_radius,start_inner_circle_radius,end_inner_circle_radious);
                break;
                case MotionEvent.ACTION_UP:
                    cancalAnimtor();
                    break;
        }
        return super.onTouchEvent(event);
    }
    private void cancalAnimtor(){
        mAnimator.cancel();
        progress=0;
        startAnimtor(end_outside_circle_radius,start_outside_circle_radius,end_inner_circle_radious,start_inner_circle_radius);

    }
    private void startAnimtor(float start_outside_circle_radius,float end_outside_circle_radius,float start_inner_circle_radius,float end_inner_circle_radious){
        Log.i(TAG,"outside_circle_radius5:"+end_outside_circle_radius);
        Log.i(TAG,"outside_circle_radius4:"+end_inner_circle_radious);
        ValueAnimator outside=ValueAnimator.ofFloat(start_outside_circle_radius,end_outside_circle_radius);
        ValueAnimator inner=ValueAnimator.ofFloat(start_inner_circle_radius,end_inner_circle_radious);
        outside.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                outside_circle_radius=(Float) valueAnimator.getAnimatedValue();

                invalidate();
            }
        });
        inner.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                inner_circle_radius=(Float) valueAnimator.getAnimatedValue();
                invalidate();
            }

        });
        inner.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                postDelayed(new Recoder(),100);
            }
        });

        outside.setDuration(200);
        outside.start();
        inner.setDuration(200);
        inner.start();
    }
    private void startProgressAnmitor(){
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                progress=(Float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mAnimator.setDuration(maxDuration*1000);
        mAnimator.start();
    }
    private class Recoder implements Runnable{
        @Override
        public void run() {
            startProgressAnmitor();
        }
    }

}
