package vn.edu.usth.flickr.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.edu.usth.flickr.R;

/**
 * Created by nguye_000 on 3/23/2016.
 */
public class TabFragment extends Fragment {
    public static TabLayout mTabLayout;
    public static ViewPager mTabViewPager;
    public static int mNumTab = 2;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_layout, container, false);

        mTabLayout = (TabLayout) v.findViewById(R.id.tabs);
        mTabViewPager = (ViewPager) v.findViewById(R.id.viewpager);

        mTabViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0 : return new RecentGalleryFragment();
                    case 1 : return new TrendingGalleryFragment();
                }
                return null;
            }

            @Override
            public int getCount() {
                return mNumTab;
            }

            @Override
            public CharSequence getPageTitle(int position) {

                switch (position){
                    case 0 :
                        return "Recent";
                    case 1 :
                        return "Trending";
                }
                return null;
            }
        });

        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                mTabLayout.setupWithViewPager(mTabViewPager);
            }
        });

        return v;
    }
}
