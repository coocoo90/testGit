package com.example.whyuseservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;


public class MainActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        findViewById(R.id.btn_service).setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		
		Intent intent=new Intent(this, MyService.class);
		startService(intent);
		
	}


}
