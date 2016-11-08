package com.example.multithreadbreakpoint;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	protected static final int ERROR = 1;
	private static final int THREAD_ERROR = 2;
	public static final int FINISH = 3;
	private int threadCount = 3;

	private long blockSize; // 每个区块大小

	private int runningThreadCount; // 正在运行的线程数量
	private EditText edt_path;
	private EditText edt_thread_num;
	private List<ProgressBar> progressBars;
	//存放进度条的布局
	private LinearLayout lin_container;
	
	//消息处理器
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case ERROR:
				Toast.makeText(getApplicationContext(), "下载异常", 0).show();
				break;
			case THREAD_ERROR:
				Toast.makeText(getApplicationContext(), "子线程下载异常", 0).show();
				break;
			case FINISH:
				Toast.makeText(getApplicationContext(), "下载成功", 0).show();
				break;
			default:
				break;
			}

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		edt_thread_num = (EditText) findViewById(R.id.edt_thread_num);
		edt_path = (EditText) findViewById(R.id.edt_path);
		lin_container=(LinearLayout) findViewById(R.id.lin_container);
		findViewById(R.id.btn_download).setOnClickListener(this);
		edt_path.setText("http://a.10155.com/90115000/fulltrack_dl/91892000/MP3_320_44_Stero/201212220402/M0050925001.mp3?channelID=99000100000006000000&user=13027655800");
//		edt_path.setText("http://yinyueshiting.baidu.com/data2/music/134370203/14945107241200128.mp3?xcode=1d55fc6ebc65d29b712f412e7d9d0e059747f83dd337b2e9");
	}

	@Override
	public void onClick(View v) {

		final String path = edt_path.getText().toString().trim(); // trim用来去除前缀和后缀空格
		if (TextUtils.isEmpty(path)) {
			Toast.makeText(this, "下载路径不能为空", 0).show();
			return;
		}

		String count = edt_thread_num.getText().toString().trim(); // trim用来去除前缀和后缀空格
		if (TextUtils.isEmpty(path)) {
			Toast.makeText(this, "线程数量不能为空", 0).show();
			return;
		}

		threadCount = Integer.parseInt(count);
		//清空久的进度条
		lin_container.removeAllViews();
		//在界面里添加threadCount个进度条
		progressBars=new ArrayList<ProgressBar>();
		for(int i=1;i<=threadCount;i++) {
			ProgressBar progressBar = (ProgressBar) View.inflate(this, R.layout.progressbar, null);
			lin_container.addView(progressBar);
			progressBars.add(progressBar);
		}
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					//Toast.makeText(MainActivity.this, "123", 0).show();
					
					// String path = "http://192.168.1.9:8080/PVZ.avi";
					URL url = new URL(path);
					HttpURLConnection connection = (HttpURLConnection) url
							.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(10000);
					connection.setReadTimeout(5000);
					int code = connection.getResponseCode();

					if (code == 200) {
						long size = connection.getContentLength();
						System.out.println("服务器文件大小：" + size);

						blockSize = size / threadCount;

						// 1在本地创建一个大小跟服务器一样的空白文件
						File file = new File(Environment
								.getExternalStorageDirectory(),
								getFileName(path));
						RandomAccessFile raf = new RandomAccessFile(file, "rw");
						raf.setLength(size);
						// 2开启若干个子线程分别去下载对应的资源
						runningThreadCount = threadCount;
						for (int i = 1; i <= threadCount; i++) {
							long startIndex = (i - 1) * blockSize;
							long endIndex = i * blockSize - 1;
							if (i == threadCount) {
								// 最后一个线程
								endIndex = size - 1;
							}
							System.out.println("开启线程" + i + "下载的位置:"
									+ startIndex + "~" + endIndex);

							
							//计算每个线程下载的长度
							int threadSize=(int) (endIndex-startIndex);
							progressBars.get(i-1).setMax(threadSize);
							
							new DownLoadThread(i, startIndex, endIndex, path)
									.start();
						
						}
//						Toast.makeText(MainActivity.this, "123", 0).show();
						
					} else {
						System.out.println("连接失败" + code);
					}
					connection.disconnect();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Message msg = Message.obtain();
					msg.what = ERROR;
					handler.sendMessage(msg);
				}
			}
		}).start();

	}

	private class DownLoadThread extends Thread {
		private int threadId;
		private long startIndex;
		private long endIndex;
		private String path;

		public DownLoadThread(int threadId, long startIndex, long endIndex,
				String path) {
			super();
			this.threadId = threadId;
			this.startIndex = startIndex;
			this.endIndex = endIndex;
			this.path = path;
		}

		public void run() {
			HttpURLConnection connection = null;
			try {
				
				
				int total = 0;
				File positionFile = new File(
						Environment.getExternalStorageDirectory(),
						getFileName(path) + threadId + ".txt");
				URL url = new URL(path);
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");

				// 接着从上一次的位置继续下载数据
				if (positionFile.exists() && positionFile.length() > 0) {// 判断是否有记录
					FileInputStream fis = new FileInputStream(positionFile);
					BufferedReader br = new BufferedReader(
							new InputStreamReader(fis));
					// 获取当前线程上次下载的总大小
					String lastTotalstr = br.readLine();
					int lastTotal = Integer.parseInt(lastTotalstr);
					System.out.println("上次线程" + threadId + "下载的总大小："
							+ lastTotal);
					startIndex += lastTotal;
					total += lastTotal;

					br.close(); // 如果这里不关流，之后删除文件的时候会失败
					fis.close();
				}
				
				//http协议
				connection.setRequestProperty("Range", "bytes=" + startIndex
						+ "-" + endIndex);
				

				
				connection.setConnectTimeout(10000);
				connection.setReadTimeout(5000);
				int code = connection.getResponseCode();
				System.out.println("code=" + code);
				if (code / 200 == 1) { // 下载服务器资源时返回码是206而不是200
					InputStream is = connection.getInputStream();
					File file = new File(
							Environment.getExternalStorageDirectory(),
							getFileName(path));
					RandomAccessFile raf = new RandomAccessFile(file, "rw");
					// 指定文件开始写的位置
					raf.seek(startIndex);
					System.out.println("第" + threadId + "个子线程开始写文件的位置："
							+ String.valueOf(startIndex));
					int len = 0;
					byte[] buffer = new byte[1024 * 1024];

					while ((len = is.read(buffer)) != -1) {
						RandomAccessFile rf = new RandomAccessFile(
								positionFile, "rwd");
						raf.write(buffer, 0, len);
						total += len;
						rf.write(String.valueOf(total).getBytes());
						rf.close();
						progressBars.get(threadId-1).setProgress(total);  //虽然是子线程，对ui的操作要放在主线程中，但是这个方法会判断是否是主线程，如果不是则会自动调用message和handler
					}
					is.close();
					raf.close();
					System.out.println("线程" + threadId + "下载完毕");
				} else {
					System.out.println("访问失败" + (code / 200));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Message msg = Message.obtain();
				msg.what = THREAD_ERROR;
				handler.sendMessage(msg);
			} finally {
				// 同步资源runningThreadCount

				synchronized (MainActivity.this) {
					System.out.println("线程" + threadId + "工作完毕");
					runningThreadCount--;
					if (runningThreadCount < 1) {
						System.out.println("所有线程工作完毕");
//						Toast.makeText(MainActivity.this, "下载成功", 0).show();
						// 删除文件
						for (int i = 1; i <= threadCount; i++) {
							File file = new File(
									Environment.getExternalStorageDirectory(),
									getFileName(path) + i + ".txt");
							System.out.println(file.delete());
							;
						}
						connection.disconnect();
						Message msg = Message.obtain();
						msg.what = FINISH;
						handler.sendMessage(msg);
					}
				}

			}

		}

	}

	private String getFileName(String path) {

		int start = path.lastIndexOf("/") + 1; // 从最后一个/处后一个开始读取
//		return path.substring(start);
		return "magic3.mp3";
	}

}
