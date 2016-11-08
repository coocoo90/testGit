package com.example.lifecycleapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStop() {
    	System.out.println("界面不可见，暂停播放");
    	super.onStop();
    }
    @Override
    protected void onStart() {
    	System.out.println("开始播放");
    	super.onStart();
    }

}
