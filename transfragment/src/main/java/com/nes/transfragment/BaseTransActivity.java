package com.nes.transfragment;


import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;


public class BaseTransActivity extends AppCompatActivity {
    public static final String TAG = BaseTransActivity.class.getSimpleName();


    public enum FlowDirection {
        FORWARD,
        BACKWARD
    }

    public static FlowDirection direction = FlowDirection.FORWARD;
    //todo: need to be found better solution for backward animation, not static variable!
    public static BaseTransFragment.AnimationType backwardAnimationType;


    protected Fragment attachedFragment;

    public BaseTransFragment.AnimationType getAnimationType() {
        return backwardAnimationType;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        if (direction == FlowDirection.FORWARD) {
            if (getAnimationType() == BaseTransFragment.AnimationType.SLIDE_VERTICAL) {
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            } else if (getAnimationType() == BaseTransFragment.AnimationType.SLIDE_HORISONTAL) {
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            } else {
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        } else {
            if (getAnimationType() == BaseTransFragment.AnimationType.SLIDE_VERTICAL) {
                overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
            } else if (getAnimationType() == BaseTransFragment.AnimationType.SLIDE_HORISONTAL) {
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            } else {
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        }


        backwardAnimationType = null;
        direction = FlowDirection.FORWARD;
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

        attachedFragment = fragment;

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_MENU) {

        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (attachedFragment != null) {
                if ((attachedFragment instanceof BaseTransFragment)) {
                    ((BaseTransFragment) attachedFragment).performBack();
                    return false;
                }

                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
