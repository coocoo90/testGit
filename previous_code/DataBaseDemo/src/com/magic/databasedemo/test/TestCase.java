package com.magic.databasedemo.test;

import java.util.Iterator;
import java.util.List;

import com.magic.databasedemo.dao.PersonDao;
import com.magic.databasedemo.db.PersonSQLiteOpenHelper;
import com.magic.databasedemo.entities.Person;

import android.R.string;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

public class TestCase extends AndroidTestCase {
	List<List<String>> magic;
	private static final String TAG = "TestCase";

	public void test() {
		//数据库创建时间
		PersonSQLiteOpenHelper openHelper=new PersonSQLiteOpenHelper(getContext());
	
		//第一次连接数据库时创建数据库文件
		openHelper.getReadableDatabase();
	}
	
	public void testInsert() {
		
		PersonDao dao=new PersonDao(getContext());
		
		dao.insert(new Person(0, "Steven", 28));
	}
	
	public void testDelete() {
		PersonDao dao=new PersonDao(getContext());
		dao.delete(1);
	}
	
	public void testUpdate() {
		PersonDao dao=new PersonDao(getContext());
		dao.update(2, "mazi");
	}
	
	public void testQuaryAll() {
		PersonDao dao=new PersonDao(getContext());
		List<Person> persons=dao.quaryAll();
		for(Person person:persons) {
			Log.i(TAG, person.toString());
		}
	}
	
	public void testQuaryItem() {
		PersonDao dao=new PersonDao(getContext());
		Person person=dao.queryItem(2);
		Log.i(TAG, person.toString());
	}
	
	public void testTransaction() {
		PersonSQLiteOpenHelper openHelper=new PersonSQLiteOpenHelper(getContext());
		SQLiteDatabase db = openHelper.getWritableDatabase();
		
		if(db.isOpen()) {
			
			//开启事务
			try {
				db.beginTransaction();
				
				//1 从magic账户中扣1000
				db.execSQL("update person set balance = balance-1000 where name='magic';");

				//转换设备突然down掉
				//int num=10/0;    //要用事务把这两句代码连起来
				
				
				//2 从steven账户中加1000
				db.execSQL("update person set balance = balance+1000 where name='steven';");
			
				//标记事务成功,如果不写则事务永远不会提交
				db.setTransactionSuccessful();
			} finally{
				//停止事务
				db.endTransaction();
			}
		
			db.close();
		}
	}
	
	public void testTransactionInsert() {
		PersonSQLiteOpenHelper openHelper=new PersonSQLiteOpenHelper(getContext());
		SQLiteDatabase db = openHelper.getWritableDatabase();
		
		if(db.isOpen()){
			//记住当前时间
			long start = System.currentTimeMillis();
			
			//开始添加数据
			
			try {
				db.beginTransaction();    //处理数据较多时，用事务可以节省将近10倍的时间
				for(int i=0;i<10000;i++) {
					db.execSQL("insert into person(name,age,balance) values('magic"+i+"',"+(10+i)+","+(10000+i)+")");
				}
				db.setTransactionSuccessful();
			}finally{
				db.endTransaction();
			}
			//3 记住结束时间，计算耗时时间
			long end = System.currentTimeMillis();
			
			long deviation=end-start;
			Log.i(TAG, "消耗"+deviation+"ms");
		}
	}
	
}
