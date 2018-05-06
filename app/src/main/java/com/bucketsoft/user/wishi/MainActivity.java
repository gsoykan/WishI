package com.bucketsoft.user.wishi;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.bucketsoft.user.wishi.dummy.DummyContent;

public class MainActivity extends AppCompatActivity implements WishItemFragment.OnListFragmentInteractionListener {


    // WISH-I Project by Deniz Ademoğlu ,	Batu Başaran ,	Ulaş Nuhoğlu ,	Gürkan Soykan ,	Furkan Özhan


    BottomNavigationView mBottomNavigationView;
    private int mColumnCount = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBottomNavigationView = findViewById(R.id.navigation);

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.action_item_home:
                        selectedFragment = WishItemFragment.newInstance(mColumnCount);
                        break;
                    case R.id.action_item_feed:
                        selectedFragment = WishItemFragment.newInstance(mColumnCount);
                        break;
                    case R.id.action_item_answer:
                        selectedFragment = WishAnswerFragment.newInstance();
                        break;
                    case R.id.action_item_search:
                        selectedFragment = WishSearchFragment.newInstance();
                        break;
                    case R.id.action_item_profile:
                        selectedFragment = WisherProfileFragment.newInstance();
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_frame_layout, selectedFragment);
                transaction.commit();
                return true;
            }
        });
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame_layout, WishItemFragment.newInstance(mColumnCount));
        transaction.commit();
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
