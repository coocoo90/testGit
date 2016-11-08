package com.example.netphotoobserver;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

import com.loopj.android.image.SmartImageView;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity2 extends Activity implements OnClickListener {

	private static final String TAG = "MainActivity";
	private EditText edt_Url;
	private SmartImageView myImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);

		myImageView = (SmartImageView) findViewById(R.id.imgview);
		edt_Url = (EditText) findViewById(R.id.edt_url);

		findViewById(R.id.btn_submit).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		// 取出url，抓取图片
		String url = edt_Url.getText().toString();
		myImageView.setImageUrl(url);

	}

}
