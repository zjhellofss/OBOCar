package com.wanli.fss.obocar;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wanli.fss.obocar.Service.LoginService;

public class LoginActivity extends AppCompatActivity {

    //定义对象
    private TextView tv_username1 = null;
    private TextView tv_passwd = null;

    //private EditText et_username = null;
    private EditText et_passwd = null;

    private CheckBox cb_isDriver = null;
    private Button bt_login = null;
    private Button bt_reg = null;

    //定义一个attv控件
    private AutoCompleteTextView attv_username = null;

    private boolean isDriver = false;//当前用户的角色(true是司机，false是乘客)

    String Tag = "Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        Log.e(Tag, "onCreate()...");


        //关联控件
        initUI();


        //给登陆按钮绑定一个事件
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //获取用户名和密码
                String username = attv_username.getText().toString();
                String passwd = et_passwd.getText().toString();


                if (username.isEmpty()) {
                    //日志
                    Log.e(Tag, "用户名为空");

                    //吐司
                    Toast.makeText(getApplicationContext(), "用户名为空", Toast.LENGTH_SHORT).show();
                    return;
                } else if (passwd.isEmpty()) {
                    Log.e(Tag, "密码为空");
                    Toast.makeText(getApplicationContext(), "密码为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                String isDriverString = isDriver ? "true" : "false";
                boolean loginRes = LoginService.UserIsValid(username, passwd, isDriverString);
                if (loginRes) {
                    //登陆成功
                    Log.e(Tag, "登录成功 username = " + username + ", passwd = " + passwd);
                    Log.e(Tag, "当前用户的角色是 " + ((isDriver) ? "司机" : "乘客"));
                    Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();

                    //1 定义一个跳转的对象
                    Intent intent = new Intent();

                    if (isDriver) {
                        //需要跳转到司机界面
                        //2 给这个动作设置起始界面 和 目的界面
                        intent.setClass(LoginActivity.this, DriverActivity.class);
                    } else {
                        //需要跳转 乘客界面
                        //2 给这个动作设置起始界面 和 目的界面
                        intent.setClass(LoginActivity.this, PassengerActivity.class);
                    }
                    //3 启动跳转
                    startActivity(intent);
                } else {
                    //登陆失败
                    Toast.makeText(getApplicationContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }


            }
        });


        //checkbox绑定一个事件
        cb_isDriver.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isDriver = true;
                } else {
                    isDriver = false;
                }
            }
        });

        //给注册按钮绑定一个事件
        bt_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //跳转到注册界面
                //1 定义一个跳转的对象
                Intent intent = new Intent();
                //2 给这个动作设置起始界面 和 目的界面
                intent.setClass(LoginActivity.this, RegActivity.class);
                //3 启动跳转
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.e(Tag, "onStart()...");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.e(Tag, "onResume()...");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(Tag, "onRestart()...");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(Tag, "onStop()...");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(Tag, "onPause()...");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(Tag, "onDestroy()...");
    }

    public void initUI() {
        tv_username1 = (TextView) findViewById(R.id.tv_username1);
        tv_passwd = (TextView) findViewById(R.id.tv_passwd);
        //et_username = (EditText)findViewById(R.id.et_username);
        et_passwd = (EditText) findViewById(R.id.et_passwd);
        cb_isDriver = (CheckBox) findViewById(R.id.cb_isDriver);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_reg = (Button) findViewById(R.id.bt_reg);

        attv_username = (AutoCompleteTextView) findViewById(R.id.attv_username);
    }

}
