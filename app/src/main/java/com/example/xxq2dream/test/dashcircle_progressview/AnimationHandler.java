package com.example.xxq2dream.test.dashcircle_progressview;

import android.animation.TimeInterpolator;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.animation.LinearInterpolator;

import java.lang.ref.WeakReference;

/**
 * desc ：
 *
 * @author ：xxq2dream
 * @Date ： 2018/9/20
 */
public class AnimationHandler extends Handler{
    private long mFrameStartTime = 0L;
    private long mAnimationStartTime;
    private TimeInterpolator mInterpolator = new LinearInterpolator();

    private final WeakReference<DashCircleProgressView> mCircleViewWeakReference;

    public AnimationHandler(DashCircleProgressView dashCircleProgressView) {
        super(dashCircleProgressView.getContext().getMainLooper());
        this.mCircleViewWeakReference = new WeakReference(dashCircleProgressView);
    }

    public void setValueInterpolator(TimeInterpolator interpolator) {
        this.mInterpolator = interpolator;
    }


    @Override
    public void handleMessage(Message msg) {
        DashCircleProgressView circleProgressView = (DashCircleProgressView) this.mCircleViewWeakReference.get();
        if (circleProgressView != null) {
            AnimationMsg msgType = AnimationMsg.values()[msg.what];
            if (msgType == AnimationMsg.TICK) {
                this.removeMessages(AnimationMsg.TICK.ordinal());
            }

            this.mFrameStartTime = SystemClock.uptimeMillis();
            switch (circleProgressView.mAnimationState) {
                case IDLE:
                    switch (msgType) {
                        case SET_VALUE:
                            this.setValue(msg, circleProgressView);
                            return;
                        case SET_VALUE_ANIMATED:
                            this.enterSetValueAnimated(msg, circleProgressView);
                            break;
                        case TICK:
                            this.removeMessages(AnimationMsg.TICK.ordinal());
                            break;
                        default:
                            break;
                    }

                    break;
                case ANIMATING:
                    switch (msgType) {
                        case SET_VALUE:
                            this.setValue(msg, circleProgressView);
                            break;
                        case SET_VALUE_ANIMATED:
                            break;
                        case TICK:
                            if (this.calcNextAnimationValue(circleProgressView)) {
                                circleProgressView.mAnimationState = AnimationState.IDLE;
                                circleProgressView.mCurrentValue = circleProgressView.mValueTo;
                            }

                            this.sendEmptyMessageDelayed(AnimationMsg.TICK.ordinal(), (long)circleProgressView.mFrameDelayMillis - (SystemClock.uptimeMillis() - this.mFrameStartTime));
                            circleProgressView.invalidate();
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }

    }

    private boolean calcNextAnimationValue(DashCircleProgressView circleView) {
        float t = (float)((double)(System.currentTimeMillis() - this.mAnimationStartTime) / circleView.mAnimationDuration);
        t = t > 1.0F ? 1.0F : t;
        float interpolatedRatio = this.mInterpolator.getInterpolation(t);
        circleView.mCurrentValue = circleView.mValueFrom + (circleView.mValueTo - circleView.mValueFrom) * interpolatedRatio;
        return t >= 1.0F;
    }

    private void enterSetValueAnimated(Message msg, DashCircleProgressView circleView) {
        circleView.mValueFrom = ((float[])((float[])msg.obj))[0];
        circleView.mValueTo = ((float[])((float[])msg.obj))[1];
        this.mAnimationStartTime = System.currentTimeMillis();
        circleView.mAnimationState = AnimationState.ANIMATING;
        this.sendEmptyMessageDelayed(AnimationMsg.TICK.ordinal(), (long)circleView.mFrameDelayMillis - (SystemClock.uptimeMillis() - this.mFrameStartTime));
    }

    private void setValue(Message msg, DashCircleProgressView circleView) {
        circleView.mValueFrom = circleView.mValueTo;
        circleView.mCurrentValue = circleView.mValueTo = ((float[])((float[])msg.obj))[0];
        circleView.mAnimationState = AnimationState.IDLE;

        circleView.invalidate();
    }
}
