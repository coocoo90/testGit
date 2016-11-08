package com.magic.multithread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

public class MultiThreadDownload {
	/**
	 * 线程数量
	 */
	private static int threadCount = 3;

	private static long blockSize; // 每个区块大小

	private static int runningThreadCount; // 正在运行的线程数量

	public static void main(String[] args) throws Exception {
//		String path = "http://192.168.1.9:8080/PVZ.avi";
		String path = "https://content.sakai.rutgers.edu/access/content/group/9a721e60-ef8e-412a-835b-14c0ab9020f0/HW-Dataset/mediumEWG.txt";
		URL url = new URL(path);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setConnectTimeout(10000);
		connection.setReadTimeout(5000);
		int code = connection.getResponseCode();

		if (code == 200) {
			long size = connection.getContentLength();
			System.out.println("服务器文件大小：" + size);

			blockSize = size / threadCount;

			// 1在本地创建一个大小跟服务器一样的空白文件
			File file = new File("hw3.txt");
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
				System.out.println("开启线程" + i + "下载的位置:" + startIndex + "~"
						+ endIndex);

				new DownLoadThread(i, startIndex, endIndex, path).start();

			}

		} else {
			System.out.println("连接失败" + code);
		}
		connection.disconnect();
	}

	private static class DownLoadThread extends Thread {
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
				File positionFile = new File(threadId + ".txt");
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
					
					br.close();  //如果这里不关流，之后删除文件的时候会失败
					fis.close();
				}

				connection.setRequestProperty("Range", "bytes=" + startIndex
						+ "-" + endIndex);
				connection.setConnectTimeout(10000);
				connection.setReadTimeout(5000);
				int code = connection.getResponseCode();
				System.out.println("code=" + code);
				if (code / 200 == 1) { // 下载服务器资源时返回码是206而不是200
					InputStream is = connection.getInputStream();
					File file = new File("positive.txt");
					RandomAccessFile raf = new RandomAccessFile(file, "rw");
					// 指定文件开始写的位置
					raf.seek(startIndex);
					System.out.println("第" + threadId + "个子线程开始写文件的位置："
							+ String.valueOf(startIndex));
					int len = 0;
					byte[] buffer = new byte[1024*1024];

					while ((len = is.read(buffer)) != -1) {
						RandomAccessFile rf = new RandomAccessFile(
								positionFile, "rwd");
						raf.write(buffer, 0, len);
						total += len;
						rf.write(String.valueOf(total).getBytes());
						rf.close();
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
			} finally {
				//同步资源runningThreadCount
				synchronized (MultiThreadDownload.class) {
					System.out.println("线程" + threadId + "工作完毕");
					runningThreadCount--;
					if (runningThreadCount < 1) {
						System.out.println("所有线程工作完毕");
						//删除文件
						for(int i=1;i<=threadCount;i++) {
							File file=new File(i+".txt");
							System.out.println(file.delete());;
						}
						connection.disconnect();
					}
				}

			}

		}

	}
}
