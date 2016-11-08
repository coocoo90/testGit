package com.magic.databasedemo.dao;
/**
 * 用API方式查询数据库
 * 
 */
import java.util.ArrayList;
import java.util.List;

import com.magic.databasedemo.db.PersonSQLiteOpenHelper;
import com.magic.databasedemo.entities.Person;

import android.R.integer;
import android.R.string;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PersonDao2 {
	
	private static final String TAG = "PersonDao2";
	private PersonSQLiteOpenHelper myOpenHelper;
	
	public PersonDao2(Context context) {
		myOpenHelper=new PersonSQLiteOpenHelper(context);
	}
	

	public void insert(Person person)  {
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		if(db.isOpen()) {
			
			
			ContentValues values=new ContentValues();
			values.put("name", person.getName());
			values.put("age", person.getAge());
			long id = db.insert("person", null, values);  //第二个参数意义为：如果第三个参数为空则在这一列中插入null字符
			
			Log.i(TAG, "id:"+id);
			
			db.close();  //数据库关闭
		}
	}
	
	public void delete(int id)  {
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		if(db.isOpen()) {
			
			String whereClause="_id=?";
			String[] whereArgs={String.valueOf(id)};
			int count = db.delete("person", whereClause, whereArgs);
			Log.i(TAG, "删除了："+count+"行");
			db.close();  //数据库关闭
		}
	}
	
	public void update(int id,String name)  {
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		if(db.isOpen()) {
			
			ContentValues values=new ContentValues();
			values.put("name", name);
			int count = db.update("person", values, "_id=?", new String[]{id+""});
			
			Log.i(TAG, "修改了："+count+"行");
			
			db.close();  //数据库关闭
		}
	}
	
	public List<Person> quaryAll() {
		
		SQLiteDatabase db=myOpenHelper.getReadableDatabase();
		if(db.isOpen()) {
			
			String[] columns={"_id","name","age"};  //需要的列
			String selection=null;  //选择条件，给null查询所有
			String[] selectionArgs=null; //选择条件的参数，把选择条件中的？替换成数据中的值
			String groupBy=null;  //分组语句 group by name
			String having=null;  //过滤语句
			String orderBy=null;  //排序
			
			Cursor cursor = db.query("person", columns, selection, selectionArgs, groupBy, having, orderBy);
			
			int id;
			String name;
			int age;
			
			if(cursor !=null && cursor.getCount()>0) {
				List<Person> personList=new ArrayList<Person>();
				
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
			String[] columns={"_id","name","age"};  //需要的列
			String selection="_id=?";  //选择条件，给null查询所有
			String[] selectionArgs={id+""}; //选择条件的参数，把选择条件中的？替换成数据中的值
			String groupBy=null;  //分组语句 group by name
			String having=null;  //过滤语句
			String orderBy=null;  //排序
			
			Cursor cursor = db.query("person", columns, selection, selectionArgs, groupBy, having, orderBy);
			
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
