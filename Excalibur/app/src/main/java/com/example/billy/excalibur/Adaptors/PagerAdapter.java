package com.example.billy.excalibur.Adaptors;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import com.example.billy.excalibur.MainActivity;
import com.example.billy.excalibur.R;
import com.example.billy.excalibur.fragment.ArticleListRecycleView;

/**
 * Created by drewmahrt on 2/18/16.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;


    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {


        switch (position) {
            case 0:
                ArticleListRecycleView tab1 = new ArticleListRecycleView();
                tab1.setSections(MainActivity.BREAKING_NEWS);
                
                return tab1;
            case 1:
                //TODO: Add your second tab
                ArticleListRecycleView tab2 = new ArticleListRecycleView();
                tab2.setSections(MainActivity.BREAKING_NEWS);

                return tab2;
            case 2:
                //TODO: Add your third tab
                return null;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
