package com.example.adam.myapplication.newtaskwindow;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.adam.myapplication.R;

import net.cachapa.expandablelayout.ExpandableLayout;

public class DrugTaskFragment extends Fragment {

    private CheckBox box;
    private ExpandableLayout expandableDateLayout;

    public DrugTaskFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_drug_task, container, false);

        box = view.findViewById(R.id.cycle_check_box);
        expandableDateLayout = view.findViewById(R.id.date_expandable);

        box.setOnCheckedChangeListener(boxListener);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_add_task, menu);
    }

    private CompoundButton.OnCheckedChangeListener boxListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b)
                expandableDateLayout.expand();
            else
                expandableDateLayout.collapse();
        }
    };
}
