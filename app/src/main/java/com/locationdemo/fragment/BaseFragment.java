package com.locationdemo.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.locationdemo.activity.BaseActivity;
import com.locationdemo.util.Utils;


/**
 * Base class for all the fragments used, manages common feature needed in the most of the fragments
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    protected final String TAG = this.getClass().getSimpleName();
    /**
     * Shows progress indication in screens
     */
    protected ProgressBar pbProgress;
    /**
     * Contains last clicked time
     */
    private long lastClickedTime = 0;
    /**
     * EmptyView Layout
     */
    private LinearLayout llEmptyView;

    /**
     * Returns the resource id of the layout which will be used for setContentView() for the Activity
     *
     * @return resource id of the xml layout
     */
    protected abstract int defineLayoutResource();

    /**
     * Initialize the components for Fragment's view
     *
     * @param view A View inflated into Fragment
     */
    protected abstract void initializeComponent(View view);//to initialize the fragments components

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(defineLayoutResource(), container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    /**
     * Adds the Fragment into layout container.
     *
     * @param container               Resource id of the layout in which Fragment will be added
     * @param currentFragment         Current loaded Fragment to be hide
     * @param nextFragment            New Fragment to be loaded into container
     * @param requiredAnimation       true if screen transition animation is required
     * @param commitAllowingStateLoss true if commitAllowingStateLoss is needed
     * @return true if new Fragment added successfully into container, false otherwise
     */
    protected boolean addFragment(final int container, final Fragment currentFragment, final Fragment nextFragment, final boolean requiredAnimation,
                                  final boolean commitAllowingStateLoss) throws ClassCastException, IllegalStateException {
        if (getActivity() != null) {
            if (getActivity() instanceof BaseActivity) {
                return ((BaseActivity) getActivity()).addFragment(container, currentFragment, nextFragment, requiredAnimation, commitAllowingStateLoss);
            } else {
                throw new ClassCastException(BaseActivity.class.getName() + " can not be cast into " + getActivity().getClass().getName());
            }
        }
        return false;
    }

    /**
     * Replaces the Fragment into layout container.
     *
     * @param container               Resource id of the layout in which Fragment will be added
     * @param fragmentManager         Activity fragment manager
     * @param nextFragment            New Fragment to be loaded into container
     * @param requiredAnimation       true if screen transition animation is required
     * @param commitAllowingStateLoss true if commitAllowingStateLoss is needed
     * @return true if new Fragment added successfully into container, false otherwise
     */
    protected boolean replaceFragment(final int container, final FragmentManager fragmentManager, final Fragment nextFragment, final boolean requiredAnimation,
                                      final boolean commitAllowingStateLoss) throws ClassCastException, IllegalStateException {
        if (getActivity() != null) {
            if (getActivity() instanceof BaseActivity) {
                return ((BaseActivity) getActivity()).replaceFragment(container, fragmentManager, nextFragment, requiredAnimation, commitAllowingStateLoss);
            } else {
                throw new ClassCastException(BaseActivity.class.getName() + " can not be cast into " + getActivity().getClass().getName());
            }
        }
        return false;
    }


    @Override
    public void onClick(View v) {

        Utils.hideSoftKeyBoard(getActivity(), v);
        /*
         * Logic to Prevent the Launch of the Fragment Twice if User makes
         * the Tap(Click) very Fast.
         */

    }


}