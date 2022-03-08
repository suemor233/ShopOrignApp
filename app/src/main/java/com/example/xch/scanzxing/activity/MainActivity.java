package com.example.xch.scanzxing.activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;

import android.widget.LinearLayout;
import androidx.appcompat.app.AlertDialog;

import com.example.xch.scanzxing.R;
import com.example.xch.scanzxing.entity.GoodMessage;
import com.example.xch.scanzxing.entity.GoodName;
import com.example.xch.scanzxing.api.Api;
import com.example.xch.scanzxing.api.ApiConfig;
import com.example.xch.scanzxing.entity.OriginData;
import com.example.xch.scanzxing.entity.OriginMessage;
import com.example.xch.scanzxing.utils.DialogUtil;
import com.example.xch.scanzxing.utils.EncodingUtils;
import com.example.xch.scanzxing.api.TtitCallback;
import com.example.xch.scanzxing.utils.ImageSaveUtil;

import com.example.xch.scanzxing.utils.StatusBarUtil;
import com.example.xch.scanzxing.utils.StringUtils;
import com.example.xch.scanzxing.utils.zxing.android.CaptureActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private static final int REQUEST_CODE_SCAN2 = 0x0002;
    private static final int REQUEST_CODE_SCAN3 = 0x0003;
    LinearLayout rl_createCode,rl_distributeInformation,rl_record,rl_information,rl_distribute,rl_exit;
    public static List<OriginData> originData;
    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        rl_createCode = findViewById(R.id.rl_createCode);
        rl_distributeInformation = findViewById(R.id.rl_distributeInformation);
        rl_record = findViewById(R.id.rl_record);
        rl_information = findViewById(R.id.rl_information);
        rl_distribute = findViewById(R.id.rl_distribute);
        rl_exit = findViewById(R.id.rl_exit);
        originData = new ArrayList<>();

        try {
            StatusBarUtil.setStatusBarMode(this, true, R.color.white2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initData() {
        rl_createCode.setOnClickListener(this);
        rl_distributeInformation.setOnClickListener(this);
        rl_record.setOnClickListener(this);
        rl_information.setOnClickListener(this);
        rl_distribute.setOnClickListener(this);
        rl_exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_createCode:
                createQR();
                break;
            case R.id.rl_distributeInformation:
                DistributeInformation();
                break;
            case R.id.rl_record:
                recordQR();
                break;
            case R.id.rl_information:
                information();
                break;
            case R.id.rl_distribute:
                Distribute();
                break;
            case R.id.rl_exit:
                finish();
                break;
        }
    }

    private void Distribute() {
        startActivity(new Intent(MainActivity.this,DistributeActivity.class));
    }

    private void DistributeInformation() {
        startActivity(new Intent(MainActivity.this,informationActivity.class));
    }

    private void createQR() {
        DialogUtil.showAlertDialog(MainActivity.this, "创建溯源二维码", "确认", "取消", true, false, new DialogUtil.AlertDialogBtnClickListener() {
            @Override
            public void clickPositive(EditText editText, EditText editText2) {
                HashMap<String, Object> params = new HashMap<>();
                params.put("good_name", editText.getText().toString());
                //根据OkHttp请求数据进行登录校验
                Api.config(ApiConfig.GOOD_ADD, params).postRequest(new TtitCallback() {
                    @Override
                    public void onSuccess(final String res) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson = new Gson();
                                GoodName goodName = gson.fromJson(res, GoodName.class);
                                if (goodName.getErrorCode().equals("00000")) {
                                    Bitmap codeBitmap = EncodingUtils.createQRCode(goodName.getData(), 500, 500, null);
                                    Uri uri = ImageSaveUtil.saveAlbum(MainActivity.this, codeBitmap, Bitmap.CompressFormat.JPEG, 1, true);
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

            @Override
            public void clickNegative() {

            }
        });
    }
    private void recordQR() {
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN2);
    }


    private void information() {
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN3);
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
            if (requestCode == REQUEST_CODE_SCAN2 && resultCode == RESULT_OK) {
                HashMap<String, Object> params = new HashMap<>();
                //根据OkHttp请求数据进行登录校验
                params.put("id", Integer.parseInt(content));
                Api.config(ApiConfig.ORIGIN_GET, params).getRequest(new TtitCallback() {
                    @Override
                    public void onSuccess(final String res) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson = new Gson();
                                OriginMessage originMessage = gson.fromJson(res, OriginMessage.class);

                                originData.clear();

                                if (originMessage.getErrorCode().equals("00000")) {
                                    if (originMessage.getData().size() > 0) {
                                        for (int i = 0; i < originMessage.getData().size(); i++) {
                                            StringBuffer stringBuffer = StringUtils.subTime(originMessage.getData().get(i).getCreatedAt());
                                            originData.add(new OriginData(originMessage.getData().get(i).getOriginName(), originMessage.getData().get(i).getOriginDesc(), stringBuffer.toString()));
                                        }
                                    }else {
                                        Snackbar.make(findViewById(R.id.contentView), "请扫描商品二维码", Snackbar.LENGTH_SHORT).show();
                                    }
                                    startActivity(new Intent(MainActivity.this,RecordActivity.class));
                                } else {
                                    Snackbar.make(findViewById(R.id.contentView), originMessage.getErrorMessage(), Snackbar.LENGTH_SHORT).show();
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
            } else if (requestCode == REQUEST_CODE_SCAN3 && resultCode == RESULT_OK) {
                HashMap<String, Object> params = new HashMap<>();
                //根据OkHttp请求数据进行登录校验
                params.put("id", Integer.parseInt(content));
                Api.config(ApiConfig.ORIGIN_GET, params).getRequest(new TtitCallback() {
                    @Override
                    public void onSuccess(final String res) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson = new Gson();
                                OriginMessage originMessage = gson.fromJson(res, OriginMessage.class);
                                System.out.println(originMessage + "==========");
                                if (originMessage.getErrorCode().equals("00000")) {
                                    if (originMessage.getData().size() > 0) {
                                        for (int i = 0; i < originMessage.getData().size(); i++) {
                                            if (originMessage.getData().get(i).getOriginName().equals("蒙古")){
                                                new AlertDialog.Builder(MainActivity.this).setTitle("商品名称").setMessage("处于高风险区！！！").setPositiveButton("好的", null).show();
                                            }else {
                                                new AlertDialog.Builder(MainActivity.this).setTitle("商品名称").setMessage("处于低风险区").setPositiveButton("好的", null).show();
                                            }
                                        }
                                    }else {
                                        Snackbar.make(findViewById(R.id.contentView), "请扫描商品二维码", Snackbar.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Snackbar.make(findViewById(R.id.contentView), originMessage.getErrorMessage(), Snackbar.LENGTH_SHORT).show();
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
            Snackbar.make(findViewById(R.id.contentView), "请扫描商品二维码", Snackbar.LENGTH_SHORT).show();
        }
    }

}
