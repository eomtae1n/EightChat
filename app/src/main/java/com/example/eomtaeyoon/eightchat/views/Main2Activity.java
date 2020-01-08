package com.example.eomtaeyoon.eightchat.views;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;


import com.example.eomtaeyoon.eightchat.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class Main2Activity extends FragmentActivity {

    private ChatFragment chatFragment;
    private FriendFragment friendFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        chatFragment = new ChatFragment();
        friendFragment = new FriendFragment();

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                if(tabId == R.id.tab_chat) {
                    transaction.replace(R.id.contentContainer, chatFragment).commit();
                }
                else if(tabId == R.id.tab_friend) {
                    transaction.replace(R.id.contentContainer, friendFragment).commit();
                }
            }
        });
    }
}
