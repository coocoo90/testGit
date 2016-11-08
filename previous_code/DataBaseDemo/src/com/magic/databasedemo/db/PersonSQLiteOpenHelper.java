package com.magic.databasedemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.AlteredCharSequence;
import android.util.Log;

public class PersonSQLiteOpenHelper extends SQLiteOpenHelper{

	private static final String TAG = "PersonSQLiteOpenHelper";

	/**
	 * 
	 * name数据库名称
	 * factory 游标工程（数据结果集合）
	 * version 数据库版本号 >=1
	 * @param context
	 */
	
	
	public PersonSQLiteOpenHelper(Context context) {
		super(context, "magic.db", null, 2);
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * 数据库第一次创建时回调次方法
	 * 初始化表
	 * 
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		String sql="create table person(_id integer primary key,name varchar(20),age integer);";
		db.execSQL(sql);
	}

	/**
	 * 数据库版本号更新时回调
	 * 更新数据库内容（删除表，添加表，修改表）
	 * 
	 */
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		if(oldVersion==1 && newVersion==2) {	
			Log.i(TAG, "数据库已更新");
			//在person表中添加一个余额列balance
			db.execSQL("alter table person add balance integer;");
		}else if(oldVersion==2 && newVersion==3){
			
		}
		
	}

}
