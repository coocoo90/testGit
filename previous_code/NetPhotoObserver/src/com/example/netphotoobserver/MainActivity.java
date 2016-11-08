package com.example.netphotoobserver;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

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

public class MainActivity extends Activity implements OnClickListener {

	private static final String TAG = "MainActivity";
	protected static final int ERROR = 1;
	private EditText edt_Url;
	private ImageView imgView;

	private final int SUCCESS = 0;

	private Handler handler = new Handler() {

		/**
		 * 接收消息
		 */
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			Log.i(TAG, "what=" + msg.what);
			if (msg.what == SUCCESS) {

				imgView.setImageBitmap((Bitmap) msg.obj);
			}else if (msg.what==ERROR) {
				Toast.makeText(MainActivity.this, "抓取失败", 0).show();
			}

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		imgView = (ImageView) findViewById(R.id.imgview);
		edt_Url = (EditText) findViewById(R.id.edt_url);

		findViewById(R.id.btn_submit).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		final String url = edt_Url.getText().toString();

		new Thread(new Runnable() {

			@Override
			public void run() {

				Bitmap bitmap = getImageForNet(url);

				// imgView.setImageBitmap(bitmap); //只有主线程才能修改view

				if (bitmap != null) {
					Message msg = new Message();
					msg.what = SUCCESS;
					msg.obj = bitmap;
					handler.sendMessage(msg);
				}else {
					Message msg = new Message();
					msg.what = ERROR;
					handler.sendMessage(msg);
				}

			}
		}).start();

	}

	/**
	 * 
	 * @param url
	 * @return url对应图片
	 */
	private Bitmap getImageForNet(String url) {

		HttpURLConnection connection = null;

		try {
			URL myUrl = new URL(url); // 创建一个url对象

			connection = (HttpURLConnection) myUrl.openConnection();

			connection.setRequestMethod("GET"); // 设置请求方法为GET
			connection.setConnectTimeout(10000); // 设置连接服务器的超时时间，如果超过10秒，没有连接成功，会抛出异常
			connection.setReadTimeout(5000); // 设置读取数据时超时时间，如果超过5秒，抛异常
			Log.i(TAG, "设置完毕");
			connection.connect(); // 开始链接
			Log.i(TAG, "已连接");
			int responseCode = connection.getResponseCode(); // 得到服务器响应码
			Log.i(TAG, "得到服务器响应码");
			if (responseCode == 200) {
				Log.i(TAG, "访问成功");
				// 访问成功
				InputStream ins = connection.getInputStream(); // 获得服务器返回的流数据
				Log.i(TAG, "以读取stream");
				Bitmap bitmap = BitmapFactory.decodeStream(ins); // 根据流数据创建一个bitmap对象

				return bitmap;
			} else {
				Log.i(TAG, "访问失败：respondcode＝" + responseCode);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.i(TAG, "访问失败");
		} finally {
			if (connection != null) {
				connection.disconnect(); // 断开连接
			}
		}

		return null;

	}

}
