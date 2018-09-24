package com.nes.transfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Constructor;


/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseTransFragment extends Fragment {
    protected final String TAG = BaseTransFragment.this.getClass().getSimpleName();

    public enum AnimationType {
        SLIDE_HORISONTAL,
        SLIDE_VERTICAL,
        FADE

    }

    abstract public int getFragmentContainer();


    public AnimationType getAnimationType() {
        return AnimationType.SLIDE_HORISONTAL;
    }

    public Class<?> getBackFragmentClass() {
        return null;
    }

    public Class<?> getBackActivityClass() {
        return null;
    }

    public String getActivityTitle() {
        return null;
    }

    private CustomProgressDialog _progress = null;
    protected boolean isAnimated = true;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    public boolean backEnabled() {
        return true;

    }

    public boolean changed() {
        return false;
    }

    public void save() {

    }

    public void performBack() {
        if (!backEnabled()) {
            return;
        }
        View view = this.getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        if (changed()) {
//            MessageDialog.showMessage(getActivity(), getString(R.string.msg_would_you_like_to_save_your_changes))
//                    .setButtons(getString(R.string.btn_yes), getString(R.string.btn_no))
//                    .setListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            String tag = (String) view.getTag();
//                            if (tag.equalsIgnoreCase(MessageDialog.YES_TAG)) {
//                                backInProcess = true;
//                                save();
//                            } else {
//                                processBack();
//                            }
//                            MessageDialog.hideMessage();
//                        }
//                    }).show();
        } else {
            processBack();
        }
    }

    protected void processBack() {
        if (getBackActivityClass() != null) {
            try {
                isAnimated = true;
                BaseTransActivity.direction = BaseTransActivity.FlowDirection.BACKWARD;
                Constructor<?> constructor = getBackActivityClass().getConstructor();
                Intent mainIntent = new Intent(getActivity(), constructor.getDeclaringClass());

                getActivity().startActivity(mainIntent);
                getActivity().finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (getBackFragmentClass() != null) {
            try {
                //Class<?> remoteClass = Class.forName(backClassName);

                isAnimated = true;

                Constructor<?> constructor = getBackFragmentClass().getConstructor();

                BaseTransFragment innerInstance = (BaseTransFragment) constructor.newInstance();

                backToFragment(innerInstance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (BaseTransFragment.this.getActivity() != null)
                BaseTransFragment.this.getActivity().finish();
        }
    }

    public void performBackWithoutFadeAnimation() {
        if (getBackFragmentClass() != null) {
            try {

                Constructor<?> constructor = getBackFragmentClass().getConstructor();
                BaseTransFragment innerInstance = (BaseTransFragment) constructor.newInstance();

                innerInstance.isAnimated = false;

                backToFragment(innerInstance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (BaseTransFragment.this.getActivity() != null)
                BaseTransFragment.this.getActivity().finish();
        }
    }

    protected void forwardToFragment(BaseTransFragment fragment) {
        if (getFragmentManager() != null) {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

            if (fragment.getAnimationType() == AnimationType.SLIDE_HORISONTAL) {
                transaction.setCustomAnimations(R.animator.forward_in, R.animator.forward_out);
            } else if (fragment.getAnimationType() == AnimationType.SLIDE_VERTICAL) {
                transaction.setCustomAnimations(R.animator.push_in, R.animator.push_out);
//            } else{
//                transaction.setCustomAnimations(R.animator.fr_fade_in, R.animator.fr_fade_out);
            }

            transaction.replace(getFragmentContainer(), fragment);
            transaction.commitAllowingStateLoss();
        }
    }

    public void backToFragment(BaseTransFragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        if (this.getAnimationType() == AnimationType.SLIDE_HORISONTAL) {
            transaction.setCustomAnimations(R.animator.back_in, R.animator.back_out);
        } else {
            transaction.setCustomAnimations(R.animator.pop_in, R.animator.pop_out);
        }

        transaction.replace(getFragmentContainer(), fragment);
        transaction.commitAllowingStateLoss();
    }

    public void updateProgressMessage(final String message) {
        if (BaseTransFragment.this.getActivity() != null) {
            BaseTransFragment.this.getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    if (_progress != null) {
                        _progress.showMessage(message);
                    } else {
                        _progress.showMessage("Please Wait");
                    }
                }
            });
        }
    }

    public void showProgress() {
        final int gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;

        if (BaseTransFragment.this.getActivity() != null) {
            BaseTransFragment.this.getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    hideProgress();
                    if (_progress == null)
                        _progress = new CustomProgressDialog(BaseTransFragment.this.getActivity(), gravity);
                    else
                        _progress.getWindow().setGravity(gravity);
                    _progress.show();
                    _progress.showMessage("Please Wait");
                    _progress.animateStart(gravity);
                }
            });
        }
    }

    public void showProgress(final String msg) {
        final int gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;

        if (BaseTransFragment.this.getActivity() != null) {
            BaseTransFragment.this.getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    hideProgress();
                    if (_progress == null)
                        _progress = new CustomProgressDialog(BaseTransFragment.this.getActivity(), gravity);
                    else
                        _progress.getWindow().setGravity(gravity);

                    _progress.show();
                    _progress.showMessage(msg);
                    _progress.animateStart(gravity);
                }
            });
        }
    }

    public void hideProgress() {
        try {
            if (BaseTransFragment.this.getActivity() != null) {
                BaseTransFragment.this.getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        if (null != _progress) {
                            _progress.animateStop();
                            _progress.hide();
                            _progress.dismiss();
                            _progress = null;
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
