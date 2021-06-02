package io.realworld.android.hitsapp.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import io.realworld.android.hitsapp.fragments.CallsFragment;
import io.realworld.android.hitsapp.fragments.ChatsFragment;
import io.realworld.android.hitsapp.fragments.StatusFragment;

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1 : return new StatusFragment();
            case 2 : return new CallsFragment();
            default: return new ChatsFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        String title = null;
        if(position==0){
            title = "Chats";
        }
        if(position==1){
            title = "Status";
        }
        if(position==2){
            title = "Calls";
        }

        return title;
    }
}
