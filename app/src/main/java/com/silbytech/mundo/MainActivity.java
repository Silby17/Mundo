package com.silbytech.mundo;

import com.silbytech.mundo.fragments.AllCategoriesFragment;
import com.silbytech.mundo.fragments.InboxFragment;
import com.silbytech.mundo.fragments.SettingsFragment;
import android.content.SharedPreferences;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private String userID;
    public static final String PREFS = "prefs";
    public String userToken;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        final Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        fragmentManager = getSupportFragmentManager();

        //FragmentTransaction transaction = fragmentManager.beginTransaction();
        preferences = getSharedPreferences(PREFS, 0);

        userID = preferences.getString("userId", "");
        userToken = preferences.getString("token", "");

        final Bundle userArgs = new Bundle();
        userArgs.putString("userId", userID);
        userArgs.putString("token", userToken);


        /*//Starts the All Categories Fragment at start
        Fragment frag = new AllCategoriesFragment();
        loadFragment(frag);*/

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);


        //Switch Case for bottom Navigation onClicks
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_all:
                        Fragment frag = new AllCategoriesFragment();
                        frag.setArguments(userArgs);
                        loadFragment(frag);
                        return true;
                    case R.id.action_account:
                        toolbar.setTitle("Account");
                        Fragment accountFrag = new SingleListingFragment();
                        accountFrag.setArguments(userArgs);
                        loadFragment(accountFrag);
                        return true;
                    case R.id.action_inbox:
                        toolbar.setTitle("Inbox");
                        Fragment inboxFragment = new InboxFragment();
                        inboxFragment.setArguments(userArgs);
                        loadFragment(inboxFragment);
                        return true;
                    case R.id.action_settings:
                        Fragment settingsFrag = new SettingsFragment();
                        settingsFrag.setArguments(userArgs);
                        loadFragment(settingsFrag);
                        toolbar.setTitle("Settings");
                        return true;
                }
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**************************************************
     * Closes the app on Back Button Pressed
     *************************************************/
    @Override
    public void onBackPressed() {
        this.finish();
    }
}
