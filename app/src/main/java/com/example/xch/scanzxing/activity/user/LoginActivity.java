package com.example.xch.scanzxing.activity.user;

import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.xch.scanzxing.R;
import com.example.xch.scanzxing.activity.BaseActivity;

public class LoginActivity extends BaseActivity {

    Button btn_login,btn_register;
    EditText et_username,et_password;
    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
    }

    @Override
    protected void initData() {
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   new AlertDialog.Builder(LoginActivity.this).setPositiveButton("确定",null).setTitle("你要注册哪种类型的账号").show();
            }
        });
    }
}