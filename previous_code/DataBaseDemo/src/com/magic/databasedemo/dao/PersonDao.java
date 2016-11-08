package com.magic.databasedemo.dao;

import java.util.ArrayList;
import java.util.List;

import com.magic.databasedemo.db.PersonSQLiteOpenHelper;
import com.magic.databasedemo.entities.Person;

import android.R.integer;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PersonDao {
	
	private PersonSQLiteOpenHelper myOpenHelper;
	
	public PersonDao(Context context) {
		myOpenHelper=new PersonSQLiteOpenHelper(context);
	}
	
	/**
	 * 添加到person表一条数据
	 * 
	 */
	
	public void insert(Person person)  {
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		if(db.isOpen()) {
			
			//执行添加到数据库的操作
			db.execSQL("insert into person(name,age)values(?,?);",new Object[]{person.getName(),person.getAge()});
			
			db.close();  //数据库关闭
		}
	}
	
	public void delete(int id)  {
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		if(db.isOpen()) {
			
			db.execSQL("delete from person where _id=?;",new Integer[]{id});
			
			db.close();  //数据库关闭
		}
	}
	
	public void update(int id,String name)  {
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		if(db.isOpen()) {
			
			db.execSQL("update person set name=? where _id=?;",new Object[]{name,id});
			
			db.close();  //数据库关闭
		}
	}
	
	public List<Person> quaryAll() {
		
		SQLiteDatabase db=myOpenHelper.getReadableDatabase();
		if(db.isOpen()) {
			Cursor cursor = db.rawQuery("select _id,name,age from person;", null);
			
			if(cursor!=null && cursor.getCount()>0) {
				List<Person> personList=new ArrayList<Person>();
				int id;
				String name;
				int age;
				while(cursor.moveToNext()) {
					id=cursor.getInt(0);
					name=cursor.getString(1);
					age=cursor.getInt(2);
					personList.add(new Person(id, name, age));
				}
				db.close();
				return personList;
			}
			db.close();
		}
		
		return null;
	}
	
	public Person queryItem(int id) {
		SQLiteDatabase db=myOpenHelper.getReadableDatabase();
		if(db.isOpen()) {
			Cursor cursor = db.rawQuery("select _id,name,age from person where _id=?;", new String[]{String.valueOf(id)});
			if(cursor !=null && cursor.moveToFirst()) {
				String name=cursor.getString(1);
				int age=cursor.getInt(2);
				db.close();
				return new Person(id, name, age);
			}
			
			db.close();
		}
		
		
		return null;
	}
}
