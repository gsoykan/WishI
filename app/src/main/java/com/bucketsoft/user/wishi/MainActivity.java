package com.bucketsoft.user.wishi;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.bucketsoft.user.wishi.dataClasses.WishObject;

public class MainActivity extends AppCompatActivity implements WishItemFragment.OnListFragmentInteractionListener {


    // WISH-I Project by Deniz Ademoğlu ,	Batu Başaran ,	Ulaş Nuhoğlu ,	Gürkan Soykan ,	Furkan Özhan


    BottomNavigationView mBottomNavigationView;
    FloatingActionButton askFloatingActionButton;
    private int mColumnCount = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        askFloatingActionButton = findViewById(R.id.ask_floating_action_button);

        mBottomNavigationView = findViewById(R.id.navigation);

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.action_item_home:
                        selectedFragment = WishItemFragment.newInstance(mColumnCount, "ALL");
                        break;
                    case R.id.action_item_feed:
                        selectedFragment = WishItemFragment.newInstance(mColumnCount, "CATEGORICAL");
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
        transaction.replace(R.id.main_frame_layout, WishItemFragment.newInstance(mColumnCount, "ALL"));
        transaction.commit();


        askFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AskWishActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onListFragmentInteraction(WishObject item) {

        Intent intent = new Intent(MainActivity.this, WishObjectDetailActivity.class);
        intent.putExtra("WISH_OBJECT", item);
        intent.putExtra("ANSWERS_TO_WISH", item.getAnswers());
        startActivity(intent);



    }
}
