package com.example.xch.scanzxing.activity.user;


import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.xch.scanzxing.R;
import com.example.xch.scanzxing.activity.BaseActivity;
import com.example.xch.scanzxing.api.Api;
import com.example.xch.scanzxing.api.ApiConfig;
import com.example.xch.scanzxing.api.TtitCallback;
import com.example.xch.scanzxing.entity.UserMessage;
import com.example.xch.scanzxing.utils.CodeUtils;
import com.google.gson.Gson;

import java.util.HashMap;

public class WarehouseActivity extends BaseActivity {
    ImageView iv_verification;
    EditText et_username,et_password,et_password_repeat,et_verification,et_weight,et_name,et_location;
    Button btn_back,btn_login;
    private CodeUtils codeUtils;
    @Override
    protected int initLayout() {
        return R.layout.activity_warehouse;
    }

    @Override
    protected void initView() {
        iv_verification = findViewById(R.id.iv_verification);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        et_password_repeat = findViewById(R.id.et_password_repeat);
        et_verification = findViewById(R.id.et_verification);
        btn_login = findViewById(R.id.btn_login);
        btn_back = findViewById(R.id.btn_back);
        et_weight = findViewById(R.id.et_weight);
        et_name = findViewById(R.id.et_name);
        et_location = findViewById(R.id.et_location);
    }

    @Override
    protected void initData() {
        codeUtils = CodeUtils.getInstance();
        Bitmap bitmap = codeUtils.createBitmap();
        iv_verification.setImageBitmap(bitmap);

        iv_verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = codeUtils.createBitmap();
                iv_verification.setImageBitmap(bitmap);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                String password_repeat = et_password_repeat.getText().toString();
                String verification = et_verification.getText().toString();
                String location = et_location.getText().toString();
                String name = et_username.getText().toString();
                String weight = et_weight.getText().toString();
                if (username.equals("") || password.equals("") || password_repeat.equals("") || verification.equals("") || location.equals("") || name.equals("") || weight.equals("")){
                    showToast("请输入完整信息");
                }else if (!password.equals(password_repeat)){
                    showToast("两次密码不一致");
                }else {
                    HashMap<String, Object> params = new HashMap<>();
                    //根据OkHttp请求数据进行登录校验
                    params.put("username", username);
                    params.put("password", password);
                    params.put("category", "warehouse");
                    params.put("location", location);
                    params.put("name", name);
                    params.put("weight", Integer.parseInt(weight));
                    Api.config(ApiConfig.REGISTER_USER, params).postRequest(new TtitCallback() {
                        @Override
                        public void onSuccess(final String res) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Gson gson = new Gson();
                                    UserMessage user = gson.fromJson(res, UserMessage.class);
                                    if (user.getSuccess()){
                                        showToast("注册成功，请登录");
                                        finish();
                                    }else {
                                        showToast("用户名已经存在");
                                    }
                                }
                            });

                        }
                        @Override
                        public void onFailure(Exception e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    System.out.println(e);
                                    showToast("网络异常");
                                }
                            });
                        }
                    });
                }
            }
        });

    }
}