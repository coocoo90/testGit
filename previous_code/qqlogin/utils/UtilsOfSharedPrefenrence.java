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
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.nfc.Tag;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

public class UtilsOfSharedPrefenrence {

	public static boolean saveUserInfo(Context context, String number,
			String password) {
		try {
			// /data/data/包名/shared_prefs/magic3
			SharedPreferences sPreferences = context.getSharedPreferences("magic3",
					context.MODE_PRIVATE);
			
			//获得一个编辑对象
			Editor edit = sPreferences.edit();
			//存数据
			edit.putString("number", number);
			edit.putString("password", password);
			
			//提交,数据真正存储起来了
			edit.commit();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	// 返回用户信息

	public static Map<String, String> getUserInfo(Context context) {

		SharedPreferences sPreferences = context.getSharedPreferences("magic3",
				context.MODE_PRIVATE);
		
		String number = sPreferences.getString("number", null);
		String password = sPreferences.getString("password", null);
		
		if(!TextUtils.isEmpty(number)&& !TextUtils.isEmpty(password)) {
			Map<String, String> userInfoMap=new HashMap<String, String>();
			userInfoMap.put("number", number);
			userInfoMap.put("password", password);
			
			return userInfoMap;
		}
		
		return null;
	}
}
