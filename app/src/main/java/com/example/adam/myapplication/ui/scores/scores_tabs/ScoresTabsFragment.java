package com.example.adam.myapplication.ui.scores.scores_tabs;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adam.myapplication.R;

public class ScoresTabsFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public ScoresTabsFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scores_tabs, container, false);

        customizeComponents(view);

        return view;
    }

    private void customizeComponents(View view) {
        tabLayout = view.findViewById(R.id.tabs);
        viewPager = view.findViewById(R.id.view_pager);

        makeTabPages();
    }

    private void makeTabPages() {
        ScoresPagerAdapter adapter = new ScoresPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
