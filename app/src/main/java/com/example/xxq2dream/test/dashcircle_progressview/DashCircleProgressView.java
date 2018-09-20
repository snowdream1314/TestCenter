package com.example.xxq2dream.test.dashcircle_progressview;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Message;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.xxq2dream.test.R;


/**
 * desc ：初始化时是虚线圆的圆形加载进度条
 *
 * @author ：xxq2dream
 * @Date ： 2018/9/20
 */
public class DashCircleProgressView extends View {

    AnimationState mAnimationState;

    private Paint mRimPaint;
    private Paint mBarPaint;

    protected int mLayoutHeight;
    protected int mLayoutWidth;
    protected RectF mCircleBounds;
    private int mStartAngle;
    float mValueTo = 0.0F;
    float mValueFrom = 0.0F;
    float mCurrentValue = 0.0F;
    float mMaxValue = 100.0F;
    int dashCount = 36;
    float dashLineWidth = 10F;
    private int mBarWidth;
    private int mRimWidth;
    private int mRimColor;
    private int mBarColor;
    double mAnimationDuration = 900.0D;
    AnimationHandler mAnimationHandler = new AnimationHandler(this);
    int mFrameDelayMillis = 10;


    public void setBarWidth(@FloatRange(from = 0.0D) int barWidth) {
        this.mBarWidth = barWidth;
        this.mBarPaint.setStrokeWidth((float) barWidth);
    }

    public void setBarColor(@ColorInt int barColor) {
        this.mBarColor = barColor;
        this.mBarPaint.setColor(barColor);
    }

    public void setRimWidth(@IntRange(from = 0L) int rimWidth) {
        this.mRimWidth = rimWidth;
        this.mRimPaint.setStrokeWidth((float) rimWidth);
    }

    public void setRimColor(@ColorInt int rimColor) {
        this.mRimColor = rimColor;
        this.mRimPaint.setColor(rimColor);
    }


    public DashCircleProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mLayoutHeight = 0;
        this.mLayoutWidth = 0;
        this.mCircleBounds = new RectF();
        this.mStartAngle = 270;
        this.mBarWidth = 8;
        this.mRimWidth = 6;
        this.mRimColor = Color.parseColor("#ffffff");
        this.mBarColor = Color.parseColor("#ffffff");
        this.mAnimationState = AnimationState.IDLE;
        init();
        parseAttributes(context.obtainStyledAttributes(attrs, R.styleable.DashCircleProgressView));

    }

    private void parseAttributes(TypedArray a) {
        this.setBarWidth((int) a.getDimension(R.styleable.DashCircleProgressView_dcpv_barWidth, (float) this.mBarWidth));
        this.setRimWidth((int) a.getDimension(R.styleable.DashCircleProgressView_dcpv_rimWidth, (float) this.mRimWidth));
        this.setRimColor(a.getColor(R.styleable.DashCircleProgressView_dcpv_rimColor, this.mRimColor));
        this.setBarColor(a.getColor(R.styleable.DashCircleProgressView_dcpv_barColor, this.mBarColor));
        a.recycle();
    }

    private void init() {
        mBarPaint = new Paint();
        mBarPaint.setAntiAlias(true);
        mBarPaint.setColor(this.mBarColor);
        mBarPaint.setStyle(Paint.Style.STROKE);
        mBarPaint.setStrokeCap(Paint.Cap.ROUND);
        mBarPaint.setStrokeWidth(this.mBarWidth);

        //虚线画笔
        mRimPaint = new Paint();
        mRimPaint.setAntiAlias(true);
        mRimPaint.setStyle(Paint.Style.STROKE);
        mRimPaint.setColor(this.mRimColor);
        mRimPaint.setStrokeWidth(this.mRimWidth);
        mRimPaint.setStrokeCap(Paint.Cap.ROUND);
        mRimPaint.setPathEffect(new DashPathEffect(new float[]{3f, 30f}, 0));
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = this.getMeasuredWidth();
        int height = this.getMeasuredHeight();
        int widthWithoutPadding = width - this.getPaddingLeft() - this.getPaddingRight();
        int heightWithoutPadding = height - this.getPaddingTop() - this.getPaddingBottom();
        int size;
        if (widthWithoutPadding > heightWithoutPadding) {
            size = heightWithoutPadding;
        } else {
            size = widthWithoutPadding;
        }

        this.setMeasuredDimension(size + this.getPaddingLeft() + this.getPaddingRight(), size + this.getPaddingTop() + this.getPaddingBottom());
    }

    private void setupBounds() {
        int minValue = Math.min(this.mLayoutWidth, this.mLayoutHeight);
        int xOffset = this.mLayoutWidth - minValue;
        int yOffset = this.mLayoutHeight - minValue;
        float paddingTop = (float) (this.getPaddingTop() + yOffset / 2);
        float paddingBottom = (float) (this.getPaddingBottom() + yOffset / 2);
        float paddingLeft = (float) (this.getPaddingLeft() + xOffset / 2);
        float paddingRight = (float) (this.getPaddingRight() + xOffset / 2);
        int width = this.getWidth();
        int height = this.getHeight();
        float circleWidthHalf = (float) this.mBarWidth / 2.0F > (float) this.mRimWidth / 2.0F ? (float) this.mBarWidth / 2.0F : (float) this.mRimWidth / 2.0F;
        this.mCircleBounds = new RectF(paddingLeft + circleWidthHalf, paddingTop + circleWidthHalf, (float) width - paddingRight - circleWidthHalf, (float) height - paddingBottom - circleWidthHalf);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mLayoutWidth = w;
        this.mLayoutHeight = h;
        this.setupBounds();

        this.invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(mCircleBounds, 360.0f, 360.0f, false, mRimPaint);
        if (this.mAnimationState != AnimationState.SPINNING && this.mAnimationState != AnimationState.END_SPINNING) {
            float degrees = 360.0F / this.mMaxValue * this.mCurrentValue;
            drawBar(canvas, degrees);
        }
    }


    private void drawBar(Canvas _canvas, float _degrees) {
        _canvas.drawArc(this.mCircleBounds, (float) this.mStartAngle, _degrees, false, this.mBarPaint);
    }

    public void setValueInterpolator(TimeInterpolator interpolator) {
        this.mAnimationHandler.setValueInterpolator(interpolator);
    }

    public void setValueAnimated(float _valueTo, long _animationDuration) {
        this.setValueAnimated(this.mCurrentValue, _valueTo, _animationDuration);
    }

    public void setValueAnimated(float _valueFrom, float _valueTo, long _animationDuration) {
        this.mAnimationDuration = (double) _animationDuration;
        Message msg = new Message();
        msg.what = AnimationMsg.SET_VALUE_ANIMATED.ordinal();
        msg.obj = new float[]{_valueFrom, _valueTo};
        this.mAnimationHandler.sendMessage(msg);
    }

    public void setValue(float _value) {
        Message msg = new Message();
        msg.what = AnimationMsg.SET_VALUE.ordinal();
        msg.obj = new float[]{_value, _value};
        this.mAnimationHandler.sendMessage(msg);
    }

    public float getCurrentValue() {
        return this.mCurrentValue;
    }

}
