package com.magic.databasedemo.test;

import java.util.Iterator;
import java.util.List;

import com.magic.databasedemo.dao.PersonDao2;
import com.magic.databasedemo.db.PersonSQLiteOpenHelper;
import com.magic.databasedemo.entities.Person;

import android.test.AndroidTestCase;
import android.util.Log;

public class TestCase2 extends AndroidTestCase {
	
	private static final String TAG = "TestCase";

	public void test() {
		//数据库创建时间
		PersonSQLiteOpenHelper openHelper=new PersonSQLiteOpenHelper(getContext());
	
		//第一次连接数据库时创建数据库文件
		openHelper.getReadableDatabase();
	}
	
	public void testInsert() {
		
		PersonDao2 dao=new PersonDao2(getContext());
		
		dao.insert(new Person(0, "dhfjksg", 28));
	}
	
	public void testDelete() {
		PersonDao2 dao=new PersonDao2(getContext());
		dao.delete(2);
	}
	
	public void testUpdate() {
		PersonDao2 dao=new PersonDao2(getContext());
		dao.update(5, "史蒂文");
	}
	
	public void testQuaryAll() {
		PersonDao2 dao=new PersonDao2(getContext());
		List<Person> persons=dao.quaryAll();
		
		for(Person person:persons) {
			Log.i(TAG, person.toString());
		}
	}
	
	public void testQuaryItem() {
		PersonDao2 dao=new PersonDao2(getContext());
		Person person=dao.queryItem(10);
		Log.i(TAG, person.toString());
	}
	
	
}
