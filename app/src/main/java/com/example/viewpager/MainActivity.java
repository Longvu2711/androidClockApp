package com.example.viewpager;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_world_time) {
                    viewPager.setCurrentItem(0);
                    return true;
                } else if (itemId == R.id.nav_alarm) {
                    viewPager.setCurrentItem(1);
                    return true;
                } else if (itemId == R.id.nav_timer) {
                    viewPager.setCurrentItem(2);
                    return true;
                } else if (itemId == R.id.nav_count_down) {
                    viewPager.setCurrentItem(3);
                    return true;
                }
                return false;
//            public void onPageSelected( int position){
//                switch (position){
//                    case 0:
//                        bottomNavigationView.getMenu().findItem(R.id.nav_world_time).setChecked(true);
//                        break;
//                    case 1:
//                        bottomNavigationView.getMenu().findItem(R.id.nav_alarm).setChecked(true);
//                        break;
//                    case 2:
//                        bottomNavigationView.getMenu().findItem(R.id.nav_timer).setChecked(true);
//                        break;
//                    case 3:
//                        bottomNavigationView.getMenu().findItem(R.id.nav_count_down).setChecked(true);
//                        break;
//                }
//            }
            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.nav_world_time);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.nav_alarm);
                        break;
                    case 2:
                        bottomNavigationView.setSelectedItemId(R.id.nav_timer);
                        break;
                    case 3:
                        bottomNavigationView.setSelectedItemId(R.id.nav_count_down);
                        break;
                }
            }
        });
    }
}