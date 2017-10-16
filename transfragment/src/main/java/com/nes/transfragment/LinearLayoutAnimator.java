package com.nes.transfragment;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

/**
 * Created by sergenes on 9/29/14.
 *
 * This class helps animate the fragments
 * without calculating the dimension in pixels
 * Use % of screen position from 0/1.0
 *
 */
public class LinearLayoutAnimator extends LinearLayout {
    private ViewTreeObserver.OnPreDrawListener preDrawListener = null;

    private float xFraction = 0;
    private float yFraction = 0;

    public void setXFraction(float fraction) {

        this.xFraction = fraction;

        if (getWidth() == 0) {
            if (preDrawListener == null) {
                preDrawListener = new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        getViewTreeObserver().removeOnPreDrawListener(preDrawListener);
                        setXFraction(xFraction);
                        return true;
                    }
                };
                getViewTreeObserver().addOnPreDrawListener(preDrawListener);
            }
            return;
        }

        float translationX = getWidth() * fraction;
        setTranslationX(translationX);
    }

    public float getXFraction() {
        return this.xFraction;
    }

    public float getyFraction() {
        return yFraction;
    }

    public void setYFraction(float fraction) {
        if (getHeight() == 0) {
            if (preDrawListener == null) {
                preDrawListener = new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        getViewTreeObserver().removeOnPreDrawListener(preDrawListener);
                        setYFraction(yFraction);
                        return true;
                    }
                };
                getViewTreeObserver().addOnPreDrawListener(preDrawListener);
            }
            return;
        }

        float translationY = getHeight() * fraction;
        setTranslationY(translationY);
    }

    public LinearLayoutAnimator(Context context) {
        super(context);
    }


    public LinearLayoutAnimator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearLayoutAnimator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}

