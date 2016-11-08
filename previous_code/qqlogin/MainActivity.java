package com.example.qqlogin;

import java.util.Map;

import com.caikaijie.zoomimageview.ZoomView;
import com.example.qqlogin.utils.Utils;
import com.example.qqlogin.utils.Utils;
import com.example.qqlogin.utils.UtilsOfSharedPrefenrence;
import com.example.qqlogin1.R;

import android.app.Activity;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener {

    private static final String Tag = "MainActivity";
	private EditText etNumber;
	private EditText etPassword;
	private CheckBox cbRememberPWD;
	private ZoomView super_image;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //  /data/data/包名/files
       // this.getFilesDir();
        super_image=(ZoomView) findViewById(R.id.super_image);
        super_image.setImageResource(R.drawable.ic_launcher); 
        
        etNumber = (EditText) findViewById(R.id.et_number);
        etPassword = (EditText) findViewById(R.id.et_password);
        cbRememberPWD = (CheckBox) findViewById(R.id.cb_remember_pwd);
        Button btnLogin=(Button) findViewById(R.id.btn_login);
        
        btnLogin.setOnClickListener(this);
        
        //回显数据
        Map<String, String> userInfo = UtilsOfSharedPrefenrence.getUserInfo(this);
        if(userInfo!=null) {
        	etNumber.setText(userInfo.get("number"));
        	etPassword.setText(userInfo.get("password"));
        }
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//取出号码和密码
		String number=etNumber.getText().toString();
		String password=etPassword.getText().toString();
		
		if(TextUtils.isEmpty(number) || TextUtils.isEmpty(password))
		{
			Toast.makeText(this, "请正确输入", Toast.LENGTH_SHORT).show();
			return;
		}
		//判断是否记住密码是否被选中，如果被选中,存！
		if(cbRememberPWD.isChecked()) {
			//当前需要记录密码
			Log.i(Tag, "记住密码"+number);
			
			boolean isSuccess = UtilsOfSharedPrefenrence.saveUserInfo(this,number, password);
			
			if(isSuccess)
			{
				Toast.makeText(this, "保存成功", 0).show();
			}else{
				Toast.makeText(this, "保存失败", 0).show();
			}
		}
		//登录成功
		Toast.makeText(this, "登陆成功", Toast.LENGTH_LONG).show();
		
	}

}
