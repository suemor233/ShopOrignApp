package com.example.xch.scanzxing.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public abstract class BaseActivity extends AppCompatActivity {

    public Context context;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout());
        context = this;
        //绑定控件
        initView();
        //应用逻辑
        initData();
        //动态权限申请
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, 1);
        }

    }

    public void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    protected void saveStringToSp(String key,String val){
        SharedPreferences sp = getSharedPreferences("te", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key,val);
        edit.apply();
    }
    protected String getStringFromSp(String key){
        SharedPreferences sp = getSharedPreferences("te", MODE_PRIVATE);
        return sp.getString(key,"");
    }

    protected abstract int initLayout();

    protected abstract void initView();

    protected abstract void initData();
}
