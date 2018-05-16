package com.hyphenate.chatuidemo.my.adatper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hyphenate.chatuidemo.my.CupItem_1Fragment;
import com.hyphenate.chatuidemo.my.CupItem_2Fragment;

/**
 * Created by Administrator on 2018/4/25.
 */

public class CupViewPageAdatper extends FragmentStatePagerAdapter {
  String[] title={"赛程","积分"};
  String hunderd_id;
    public CupViewPageAdatper(FragmentManager fm,String hunderd_id) {
        super(fm);
        this.hunderd_id=hunderd_id;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle=new Bundle();
        bundle.putString("hunderd_id",hunderd_id);
        if (position==0){
            CupItem_1Fragment fragment1=new CupItem_1Fragment();
            fragment1.setArguments(bundle);
            return fragment1;
        }else if (position==1){
            CupItem_2Fragment fragment2=new CupItem_2Fragment();
            fragment2.setArguments(bundle);
            return fragment2;
        }else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
