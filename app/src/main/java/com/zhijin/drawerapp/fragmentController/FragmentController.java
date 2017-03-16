package com.zhijin.drawerapp.fragmentController;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.zhijin.drawerapp.collect.CollectFragment;
import com.zhijin.drawerapp.gallery.GalleryFragment;
import com.zhijin.drawerapp.home.HomeFragment;
import com.zhijin.drawerapp.liveVideo.LiveListFragment;
import com.zhijin.drawerapp.send.SendFragment;
import com.zhijin.drawerapp.setting.SettingFragment;
import com.zhijin.drawerapp.share.ShareFragment;

import java.util.ArrayList;


public class FragmentController {

    private int containerId;
    private FragmentManager fm;
    private ArrayList<Fragment> fragments;

    private static FragmentController controller;

    public static FragmentController getInstance(FragmentActivity activity, int containerId) {
        if (controller == null) {
            controller = new FragmentController(activity, containerId);
        }
        return controller;
    }

    public static void onDestroy() {
        controller = null;
    }

    private FragmentController(FragmentActivity activity, int containerId) {
        this.containerId = containerId;
        fm = activity.getSupportFragmentManager();
        initFragment();
    }

    private void initFragment() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new HomeFragment());
        fragments.add(new GalleryFragment());
        fragments.add(new LiveListFragment());
        fragments.add(new CollectFragment());
        fragments.add(new ShareFragment());
        fragments.add(new SendFragment());
        fragments.add(new SettingFragment());

        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment fragment : fragments) {
            ft.add(containerId, fragment);
//            ft.addToBackStack(null);
        }
//		ft.commit();
        ft.commitAllowingStateLoss();
    }

    public void showFragment(int position) {
        hideFragments();
        Fragment fragment = fragments.get(position);
        FragmentTransaction ft = fm.beginTransaction();
        ft.show(fragment);
//        ft.addToBackStack(null);
//		ft.commit();
        ft.commitAllowingStateLoss();

    }

    public void hideFragments() {
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment fragment : fragments) {
            if (fragment != null) {
                ft.hide(fragment);
            }
        }
//		ft.commit();
        ft.commitAllowingStateLoss();
    }

    public Fragment getFragment(int position) {
        return fragments.get(position);
    }
}