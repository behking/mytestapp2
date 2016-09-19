package com.example.newuser.vangmaterialdesign;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;



import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Fragmentstatepageradapter
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    private final SparseArray<WeakReference<Fragment>> instantiatedFragments = new SparseArray<>();
    private ArrayList<String> mTabHeader;
    public PagerAdapter(FragmentManager fm, ArrayList<String> tabHeader) {
        super(fm);
        this.mTabHeader = tabHeader;
    }

    @Override
    public Fragment getItem(int position) {
//        FragA tab1;
        FragB tab2;
        FragC tab3;
        switch (position) {
//            case 0:
//                 tab1 = new FragA();
//                return tab1;
            case 0:
                 tab2 = new FragB();
                return tab2;
            case 1:
                 tab3 = new FragC();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mTabHeader.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

//        return mTabHeader.get(position);
        return null;
    }
    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final Fragment fragment = (Fragment) super.instantiateItem(container, position);
        instantiatedFragments.put(position, new WeakReference<>(fragment));
        return fragment;
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        instantiatedFragments.remove(position);
        super.destroyItem(container, position, object);
    }
    @Nullable
    public Fragment getFragment(final int position) {
        final WeakReference<Fragment> wr = instantiatedFragments.get(position);
        if (wr != null) {
            return wr.get();
        } else {
            return null;
        }
    }
}
