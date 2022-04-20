package com.example.xch.scanzxing.activity.user;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.xch.scanzxing.R;
import com.example.xch.scanzxing.activity.BaseActivity;
import com.example.xch.scanzxing.activity.MainActivity;
import com.example.xch.scanzxing.api.Api;
import com.example.xch.scanzxing.api.ApiConfig;
import com.example.xch.scanzxing.api.TtitCallback;
import com.example.xch.scanzxing.entity.UserMessage;
import com.google.gson.Gson;

import java.util.HashMap;

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
                    View v = View.inflate(LoginActivity.this,R.layout.registerdialog,null);
                AlertDialog dialog = new AlertDialog.Builder(LoginActivity.this).setView(v).setTitle("你要注册哪种类型的账号").show();

                Button btn_1 = v.findViewById(R.id.btn_1);
                Button btn_2 = v.findViewById(R.id.btn_2);
                Button btn_3 = v.findViewById(R.id.btn_3);
                Button btn_4 = v.findViewById(R.id.btn_4);

                btn_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(LoginActivity.this,SupplierActivity.class));
                        dialog.dismiss();

                    }
                });

                btn_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(LoginActivity.this,WarehouseActivity.class));
                        dialog.dismiss();

                    }
                });

                btn_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(LoginActivity.this,SendActivity.class));
                        dialog.dismiss();

                    }
                });


                btn_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(LoginActivity.this,UserActivity.class));
                        dialog.dismiss();

                    }
                });
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                if (username.equals("") || password.equals("")){
                    showToast("请输入完整信息");
                }else {
                    HashMap<String, Object> params = new HashMap<>();
                    //根据OkHttp请求数据进行登录校验
                    params.put("username", username);
                    params.put("password", password);
                    Api.config(ApiConfig.LOGIN_USER, params).postRequest(new TtitCallback() {
                        @Override
                        public void onSuccess(final String res) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Gson gson = new Gson();
                                    UserMessage user = gson.fromJson(res, UserMessage.class);
                                    if (user.getSuccess()){
                                        showToast("登录成功");
                                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                        intent.putExtra("category", user.getData().getCategory());
                                        saveStringToSp("userId",user.getData().getId());
                                        System.out.println(user.getData().getId());
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        showToast("账号或密码错误");
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