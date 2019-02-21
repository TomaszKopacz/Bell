package com.example.adam.myapplication.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.adam.myapplication.R;

public class MainActivity extends AppCompatActivity implements MainContract.MainView {

    private MainContract.MainPresenter presenter;

    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getComponents();
        setPresenter();
    }

    private void getComponents() {
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(0);
    }

    private void setPresenter() {
        this.presenter = new MainPresenter(this);
        setListeners();

        presenter.onViewAttached();
    }

    private void setListeners() {
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);

                switch(menuItem.getItemId()) {
                    case R.id.action_board:
                        presenter.onBoardSelected();
                        break;

                    case R.id.action_doctor:
                        presenter.onDoctorSelected();
                        break;

                    case R.id.action_pill:
                        presenter.onPillsSelected();
                        break;

                    case R.id.action_measurement:
                        presenter.onScoresSelected();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void changeContentView(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    public void goToBoard() {
        bottomNavigation.setSelectedItemId(R.id.action_board);
    }
}
