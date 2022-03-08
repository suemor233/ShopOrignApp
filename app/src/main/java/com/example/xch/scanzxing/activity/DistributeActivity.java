package com.example.xch.scanzxing.activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import com.example.xch.scanzxing.R;
import com.example.xch.scanzxing.api.Api;
import com.example.xch.scanzxing.api.ApiConfig;
import com.example.xch.scanzxing.api.TtitCallback;
import com.example.xch.scanzxing.entity.GoodName;
import com.example.xch.scanzxing.entity.OriginData;
import com.example.xch.scanzxing.utils.StatusBarUtil;
import com.example.xch.scanzxing.utils.StringUtils;
import com.example.xch.scanzxing.utils.zxing.android.CaptureActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.HashMap;

public class DistributeActivity extends BaseActivity {
    private EditText et_save,et_information;
    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private static final int REQUEST_CODE_SCAN1 = 0x0001;
    private static final int REQUEST_CODE_SCAN2 = 0x0002;
    Button btn_code1,btn_code2;
    ImageView iv_code;
    private Toolbar my_toolbar;
    @Override
    protected int initLayout() {
        return R.layout.activity_distribute;
    }

    @Override
    protected void initView() {
        et_save = findViewById(R.id.et_save);
        et_information = findViewById(R.id.et_information);
        btn_code1 = findViewById(R.id.btn_code1);
        btn_code2 = findViewById(R.id.btn_code2);
        iv_code = findViewById(R.id.iv_code);
        my_toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        try {
            StatusBarUtil.setStatusBarMode(this, true, R.color.white);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void initData() {
        my_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
            btn_code1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DistributeActivity.this, CaptureActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_SCAN1);
                }
            });

            btn_code2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (StringUtils.isEmpty(et_save.getText().toString())){
                        Snackbar.make(findViewById(R.id.contentView), "请先扫描溯源二维码", Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent(DistributeActivity.this, CaptureActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_SCAN2);
                }
            });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            //返回的文本内容
            String content = data.getStringExtra(DECODED_CONTENT_KEY);
            //返回的BitMap图像
            Bitmap bitmap = data.getParcelableExtra(DECODED_BITMAP_KEY);
            // 扫描二维码/条码回传
            if (requestCode == REQUEST_CODE_SCAN1 && resultCode == RESULT_OK) {
                Gson gson = new Gson();
                OriginData originData = gson.fromJson(content,OriginData.class);
                et_save.setText(originData.getOriginName());
                et_information.setText(originData.getOriginDesc());

                if (StringUtils.isEmpty(originData.getOriginName())){
                    Snackbar.make(findViewById(R.id.contentView), "溯源信息不存在", Snackbar.LENGTH_SHORT).show();
                }
            }else if (requestCode == REQUEST_CODE_SCAN2 && resultCode == RESULT_OK) {
                HashMap<String, Object> params = new HashMap<>();
                params.put("good_id", Integer.parseInt(content));
                params.put("origin_name", et_save.getText().toString());
                params.put("origin_desc", et_information.getText().toString());

                Api.config(ApiConfig.CREATE_ORIGIN, params).postRequest(new TtitCallback() {
                    @Override
                    public void onSuccess(final String res) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson = new Gson();
                                GoodName goodName = gson.fromJson(res, GoodName.class);
                                if (goodName.getErrorCode().equals("00000")) {
                                    Snackbar.make(findViewById(R.id.contentView), "添加成功", Snackbar.LENGTH_SHORT).show();
                                } else {
                                    Snackbar.make(findViewById(R.id.contentView), goodName.getErrorMessage(), Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure(Exception e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Snackbar.make(findViewById(R.id.contentView), "网络异常", Snackbar.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            Snackbar.make(findViewById(R.id.contentView), "溯源信息不存在", Snackbar.LENGTH_SHORT).show();
        }
    }
}