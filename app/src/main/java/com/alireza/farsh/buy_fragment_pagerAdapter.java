package com.alireza.farsh;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class buy_fragment_pagerAdapter extends FragmentStatePagerAdapter {

    int num;

    public buy_fragment_pagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        num = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                buy_fragment1 tab1 = new buy_fragment1();
                return tab1;
            case 1:
                buy_fragment2 tab2 = new buy_fragment2();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return num;
    }
}
