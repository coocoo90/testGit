package com.example.simplemusic;

import java.io.IOException;



import android.app.Activity;
import android.app.Dialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.app.*;


public class MainActivity extends Activity implements OnClickListener{
	ImageButton start,pause,stop;
	MediaPlayer mp=new MediaPlayer();
	private Dialog over;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        start=(ImageButton)this.findViewById(R.id.start);
        pause=(ImageButton)this.findViewById(R.id.pause);
        stop=(ImageButton)this.findViewById(R.id.stop);
        
        start.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);
        
        over=new Dialog(MainActivity.this);
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


//	@Override
//	public void onClick(DialogInterface dialog, int which) {
//		// TODO Auto-generated method stub
//		
//	}


	@Override
	public void onClick(View v) {
		switch(v.getId())
	 {
		case R.id.start:
		{
			if(mp.isPlaying()){
				mp.reset();
			}
			mp=MediaPlayer.create(MainActivity.this,R.raw.ormosia);
			
			try {
				mp.prepare();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mp.start();
			break;
		}
	 
		case R.id.stop:
		{
			if(mp.isPlaying()){
				
				mp.stop();
			}
			
			
		}
		
		case R.id.pause:
		{
			if(mp.isPlaying())
			{
				
				mp.pause();
			}
			
		}
		
	}
  }
	
}
