package com.nes.transfragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;



/**
 * Created by sergenes on 10/10/17.
 */

public class CustomProgressDialog extends Dialog {
    Activity activity;
    TextView progressTextView;
    LinearLayout containerLayout;
    View separatorView;

    public CustomProgressDialog(Context context, int gravity) {
        super(context, android.R.style.Theme_Translucent);

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dialog_progress);

        setCancelable(false);

        LinearLayout layoutLoading = findViewById(R.id.layoutLoading);
        progressTextView = findViewById(R.id.progressTextView);
        progressTextView.setVisibility(View.INVISIBLE);
        containerLayout = findViewById(R.id.containerLayout);
        containerLayout.setBackgroundColor(Color.TRANSPARENT);


        layoutLoading.setGravity(gravity);

        findViewById(R.id.ProgressBarThumbnailLoading).post(new Runnable() {
            @Override
            public void run() {
                View imageView = findViewById(R.id.ProgressBarThumbnailLoading);
                AnimationDrawable anim = (AnimationDrawable) imageView.getBackground();
                anim.start();
            }
        });


    }

    public CustomProgressDialog(Activity context, int gravity) {
        super(context, android.R.style.Theme_Translucent);

        activity = context;

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dialog_progress);

        setCancelable(false);

        LinearLayout layoutLoading = findViewById(R.id.layoutLoading);
//        separatorView = findViewById(R.id.separatorView);
//        separatorView.setVisibility(View.GONE);
        progressTextView = findViewById(R.id.progressTextView);
        progressTextView.setVisibility(View.VISIBLE);

        containerLayout = findViewById(R.id.containerLayout);
        containerLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.dialog_rounded_bg));
        containerLayout.setBackgroundColor(Color.TRANSPARENT);

        layoutLoading.setGravity(gravity);

        findViewById(R.id.ProgressBarThumbnailLoading).post(new Runnable() {
            @Override
            public void run() {
                View imageView = findViewById(R.id.ProgressBarThumbnailLoading);
                AnimationDrawable anim = (AnimationDrawable) imageView.getBackground();
                anim.start();
            }
        });


    }

    public Activity getActivity() {
        return activity;
    }

    public void animateStart(int gravity){
        LinearLayout layoutLoading = findViewById(R.id.layoutLoading);
//        progressTextView.setVisibility(View.INVISIBLE);
        layoutLoading.setGravity(gravity);

        findViewById(R.id.ProgressBarThumbnailLoading).post(new Runnable() {
            @Override
            public void run() {
                View imageView = findViewById(R.id.ProgressBarThumbnailLoading);
                AnimationDrawable anim = (AnimationDrawable) imageView.getBackground();
                anim.start();
            }
        });
    }

    public void showMessage(String message){
        progressTextView.setText(message);
        //separatorView.setVisibility(View.VISIBLE);
        progressTextView.setVisibility(View.VISIBLE);
        progressTextView.setAlpha(1);
        containerLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.dialog_rounded_bg));


    }

    public void animateStop(){
        View imageView = findViewById(R.id.ProgressBarThumbnailLoading);
        AnimationDrawable anim = (AnimationDrawable) imageView.getBackground();
//        progressTextView.setVisibility(View.INVISIBLE);
        anim.stop();
    }


    @Override
    public void dismiss() {
        View imageView = findViewById(R.id.ProgressBarThumbnailLoading);
        AnimationDrawable anim = (AnimationDrawable) imageView.getBackground();
        anim.stop();
        super.dismiss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.isLongPress() && keyCode == KeyEvent.KEYCODE_BACK) {


            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
