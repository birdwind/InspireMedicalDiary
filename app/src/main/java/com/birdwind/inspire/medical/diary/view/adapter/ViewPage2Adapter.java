package com.birdwind.inspire.medical.diary.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.Map;
import java.util.Objects;

public class ViewPage2Adapter extends FragmentStateAdapter {

    private Map<String, Fragment> fragmentMap;

    public ViewPage2Adapter(@NonNull Fragment fragment, Map<String, Fragment> fragmentMap) {
        super(fragment);
        this.fragmentMap = fragmentMap;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        String title = fragmentMap.keySet().toArray()[position].toString();
        return Objects.requireNonNull(fragmentMap.get(title));
    }

    @Override
    public int getItemCount() {
        return fragmentMap.size();
    }

}
