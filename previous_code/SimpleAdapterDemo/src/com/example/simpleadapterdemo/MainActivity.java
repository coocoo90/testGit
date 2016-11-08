package com.example.simpleadapterdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.simpleadapterdemo.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

//		findViewById(R.id.item);
		
		ListView myListView = (ListView) findViewById(R.id.listview);
		
		List<Map<String, Object>> data=new ArrayList<Map<String,Object>>();
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("name", "magic1");
		map.put("icon", R.drawable.ic_launcher);
		data.add(map);
		
		map=new HashMap<String, Object>();
		map.put("name", "magic2");
		map.put("icon", R.drawable.ic_launcher);
		data.add(map);
		
		map=new HashMap<String, Object>();
		map.put("name", "magic3");
		map.put("icon", R.drawable.ic_launcher);
		data.add(map);
		
		map=new HashMap<String, Object>();
		map.put("name", "magic4");
		map.put("icon", R.drawable.ic_launcher);
		data.add(map);
		
		map=new HashMap<String, Object>();
		map.put("name", "magic5");
		map.put("icon", R.drawable.ic_launcher);
		data.add(map);
		
		
		SimpleAdapter adapter=new SimpleAdapter(this,
				data, //listview绑定的数据
				R.id.item,//listview的子条目布局的id
				new String[]{"name","icon"}, //data数据中的map集合里的key
				new int[]{R.id.txt_name,R.id.iv_icon});//resource中的id
		
		myListView.setAdapter(adapter); 
 	}
}
