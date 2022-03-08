package com.example.xch.scanzxing.activity;

import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.xch.scanzxing.R;
import com.example.xch.scanzxing.entity.OriginData;
import com.example.xch.scanzxing.utils.EncodingUtils;
import com.example.xch.scanzxing.utils.ImageSaveUtil;
import com.example.xch.scanzxing.utils.StatusBarUtil;
import com.example.xch.scanzxing.utils.StringUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

public class informationActivity extends BaseActivity {
    private EditText et_save,et_information;
    Button btn_create;
    ImageView iv_code;
    private Toolbar my_toolbar;
    @Override
    protected int initLayout() {
        return R.layout.activity_information;
    }

    @Override
    protected void initView() {
        et_save = findViewById(R.id.et_save);
        et_information = findViewById(R.id.et_information);
        btn_create = findViewById(R.id.btn_create);
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
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringUtils.isEmpty(et_information.getText().toString()) || StringUtils.isEmpty(et_save.getText().toString())){
                    Snackbar.make(findViewById(R.id.contentView), "输入不能为空", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                OriginData originData = new OriginData(et_save.getText().toString(), et_information.getText().toString(), null);
                Gson gson = new Gson();
                String s = gson.toJson(originData);
                Bitmap codeBitmap = EncodingUtils.createQRCode(s, 500, 500, null);
                iv_code.setImageBitmap(codeBitmap);
                Uri uri = ImageSaveUtil.saveAlbum(informationActivity.this, codeBitmap, Bitmap.CompressFormat.JPEG, 1, true);
                Snackbar.make(findViewById(R.id.contentView), "二维码成功保存到相册", Snackbar.LENGTH_SHORT).setAction("分享", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_STREAM, uri);
                        intent.setType("image/*");
                        startActivity(Intent.createChooser(intent, "分享"));
                    }
                }).show();
            }
        });
    }
}