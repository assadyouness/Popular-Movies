package com.ditto.popularmovies.ui.fragments;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.TransitionInflater;
import com.ditto.popularmovies.R;
import com.ditto.popularmovies.ui.activities.BaseActivity;
import com.ditto.popularmovies.ui.activities.MainActivity;

import java.util.Objects;
import dagger.android.support.DaggerFragment;

class BaseFragment extends DaggerFragment {

    public BaseActivity mActivity;

    private static final String TAG = "BaseFragment";
    private Handler mBaseHandler = new Handler();

    void doIn(Runnable r) {
        mBaseHandler.postDelayed(r, (long) 100);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        mActivity = (BaseActivity) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    public void pushFragment(BaseFragment fragment, boolean addToBackStack, String string, View transitionView,View transitionView2) {
        try {
            FragmentTransaction transaction = mActivity.getSupportFragmentManager().beginTransaction();
            String name = mActivity.getCurrentFragment() != null ? mActivity.getCurrentFragment().getClass().getName() : null;
            if (addToBackStack) {
                transaction.addToBackStack(name);
            }

            if(transitionView != null) {
                transaction.addSharedElement(transitionView, Objects.requireNonNull(ViewCompat.getTransitionName(transitionView)));

                if(transitionView2 != null){
                    transaction.addSharedElement(transitionView2, Objects.requireNonNull(ViewCompat.getTransitionName(transitionView2)));
                }

                setSharedElementReturnTransition(TransitionInflater.from(getContext()).inflateTransition(R.transition.default_transition));
                setExitTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.no_transition));

                fragment.setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(R.transition.default_transition));
                fragment.setEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.no_transition));
            }

            transaction.replace(R.id.frameContainer, fragment, string);
            transaction.commitAllowingStateLoss();
        }
        catch (Exception e){
        }
    }

    public BaseActivity getCurrentActivity(){
        return (BaseActivity) getActivity();
    }
}




















