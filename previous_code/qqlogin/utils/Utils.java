package com.example.qqlogin.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

public class Utils {
	public static boolean saveUserInfo(String number,String password) {
		
		try {
			String path="/data/data/com.example.qqlogin/magic.txt";
			
			FileOutputStream fos=new FileOutputStream(path);
			
			String data=number+"##"+password;
			
			fos.write(data.getBytes());
			
			fos.flush();
			
			fos.close();
			
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean saveUserInfo(Context context,String number,String password) {

		try {
//			String path="/data/data/com.example.qqlogin/magic.txt";
//			File fileDir=context.getFilesDir();
			File fileDir=context.getCacheDir();
			
			File f=new File(fileDir, "magic2");
			
			FileOutputStream fos=new FileOutputStream(f);
			
			String data=number+"##"+password;
			
			fos.write(data.getBytes());
			
			fos.flush();
			
			fos.close();
			
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	//返回用户信息
	public static Map<String, String> getUserInfo() {
		
		try {
			String path="/data/data/com.example.qqlogin/magic.txt";
			
			FileInputStream fis=new FileInputStream(path);
			
			//字符流对象
			BufferedReader reader=new BufferedReader(new InputStreamReader(fis));
			
			String text = reader.readLine();
			
			if(!TextUtils.isEmpty(text))
			{
				String[] split = text.split("##");
				
				Map<String, String> userinfoMap=new HashMap<String, String>();
				userinfoMap.put("number", split[0]);
				userinfoMap.put("password", split[1]);
				return userinfoMap;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Map<String, String> getUserInfo(Context context) {
		
		try {
			//String path="/data/data/com.example.qqlogin/magic.txt";
			
			//File fileDir=context.getFilesDir();
			File fileDir=context.getCacheDir();
			
			File f=new File(fileDir, "magic2");
			
			FileInputStream fis=new FileInputStream(f);
			
			//字符流对象
			BufferedReader reader=new BufferedReader(new InputStreamReader(fis));
			
			String text = reader.readLine();
			
			if(!TextUtils.isEmpty(text))
			{
				String[] split = text.split("##");
				
				Map<String, String> userinfoMap=new HashMap<String, String>();
				userinfoMap.put("number", split[0]);
				userinfoMap.put("password", split[1]);
				return userinfoMap;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
