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
import android.nfc.Tag;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

public class UtilsOfSDCard {

	private static final String Tag = "UtilsOfSDCard";

	public static boolean saveUserInfo(Context context, String number,
			String password) {



		try {
			//判断当前的手机是否有sd卡
			String state = Environment.getExternalStorageState();
			
			if(!Environment.MEDIA_MOUNTED.equals(state)) {
				return false;
			}
			
			File sdCardFile = Environment.getExternalStorageDirectory();

			File file = new File(sdCardFile, "magic.txt");
			
			FileOutputStream foStream = new FileOutputStream(file);

			String data = number + "##" + password;

			foStream.write(data.getBytes());

			foStream.flush();

			foStream.close();

			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	// 返回用户信息

	public static Map<String, String> getUserInfo(Context context) {

		Log.i(Tag, "113");
		try {
			Log.i(Tag, "114");
			//判断当前的手机是否有sd卡
			String state = Environment.getExternalStorageState();
			
			if(!Environment.MEDIA_MOUNTED.equals(state)) {
				return null;
			}
			
			File sdCardFile = Environment.getExternalStorageDirectory();

			File file = new File(sdCardFile, "magic.txt");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			
			String text=reader.readLine();
			
			reader.close();
			if(!TextUtils.isEmpty(text))
			{
				Map<String, String> userInfoMap=new HashMap<String, String>();
				String[] split = text.split("##");
				userInfoMap.put("number", split[0]);
				userInfoMap.put("password", split[1]);
				Log.i(Tag, "111");
				return userInfoMap;
			}
			Log.i(Tag, "112");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i(Tag, "119");
		return null;
	}
}
