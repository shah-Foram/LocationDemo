package com.locationdemo.activity;



import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.locationdemo.R;
import com.locationdemo.util.FragmentAnimation;



/**
 * Base Activity class performing common functions and providing common features to the child activities.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Returns the resource id of the layout which will be used for setContentView() for the Activity
     *
     * @return resource id of the xml layout
     */
    protected abstract int defineLayoutResource();

    /**
     * Initialize the components on activity create
     */
    protected abstract void initializeComponents();

    protected FirebaseAuth firebaseAuth;
    protected DatabaseReference databaseReference;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.databaseReference = FirebaseDatabase.getInstance().getReference("users");
        firebaseAuth = FirebaseAuth.getInstance();
        setContentView(defineLayoutResource());
        initializeComponents();
    }


    /**
     * Adds the Fragment into layout container
     *
     * @param fragmentContainerResourceId Resource id of the layout in which Fragment will be added
     * @param currentFragment             Current loaded Fragment to be hide
     * @param nextFragment                New Fragment to be loaded into fragmentContainerResourceId
     * @param requiredAnimation           true if screen transition animation is required
     * @param commitAllowingStateLoss     true if commitAllowingStateLoss is needed
     */
    public boolean addFragment(final int fragmentContainerResourceId, final Fragment currentFragment, final Fragment nextFragment, final boolean requiredAnimation, final boolean commitAllowingStateLoss) throws IllegalStateException {
        if (currentFragment == null || nextFragment == null) {
            return false;
        }
        final FragmentManager fragmentManager =getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (requiredAnimation) {
            FragmentAnimation.setDefaultFragmentAnimation(fragmentTransaction);
        }

        fragmentTransaction.add(fragmentContainerResourceId, nextFragment, nextFragment.getClass().getSimpleName());
        fragmentTransaction.addToBackStack(nextFragment.getClass().getSimpleName());

        final Fragment parentFragment = currentFragment.getParentFragment();
        fragmentTransaction.hide(parentFragment == null ? currentFragment : parentFragment);

        if (!commitAllowingStateLoss) {
            fragmentTransaction.commit();
        } else {
            fragmentTransaction.commitAllowingStateLoss();
        }

        return true;
    }


    /**
     * Replaces the Fragment into layout container
     *
     * @param fragmentContainerResourceId Resource id of the layout in which Fragment will be added
     * @param fragmentManager             FRAGMENT MANGER
     * @param nextFragment                New Fragment to be loaded into fragmentContainerResourceId
     * @param requiredAnimation           true if screen transition animation is required
     * @param commitAllowingStateLoss     true if commitAllowingStateLoss is needed
     */
    public boolean replaceFragment(final int fragmentContainerResourceId, final FragmentManager fragmentManager, final Fragment nextFragment, final boolean requiredAnimation, final boolean commitAllowingStateLoss) throws IllegalStateException {
        if (nextFragment == null || fragmentManager == null) {
            return false;
        }
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (requiredAnimation) {
            FragmentAnimation.setDefaultFragmentAnimation(fragmentTransaction);
        }
        fragmentTransaction.replace(fragmentContainerResourceId, nextFragment, nextFragment.getClass().getSimpleName());

        if (!commitAllowingStateLoss) {
            fragmentTransaction.commit();
        } else {
            fragmentTransaction.commitAllowingStateLoss();
        }

        return true;
    }


    @Override
    public void onClick(View v) {
      firebaseAuth.getInstance().signOut();
      startActivity(new Intent(this,LoginActivity.class));
      finish();
    }

}
