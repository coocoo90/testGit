package com.example.broadcastsender;

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
        
        findViewById(R.id.btn_send).setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {		
		//发送广播消息
		Intent intent=new Intent();
		//自定义一个广播动作
		intent.setAction("com.example.broadcastsender.attack");
		//发送消息
		sendBroadcast(intent);
	}


}
