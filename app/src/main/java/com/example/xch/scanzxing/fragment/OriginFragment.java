package com.example.xch.scanzxing.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.xch.scanzxing.R;
import com.example.xch.scanzxing.api.Api;
import com.example.xch.scanzxing.api.ApiConfig;
import com.example.xch.scanzxing.api.TtitCallback;
import com.example.xch.scanzxing.entity.GoodMessage;
import com.example.xch.scanzxing.utils.Date.DatePickerFragment;
import com.example.xch.scanzxing.utils.EncodingUtils;
import com.example.xch.scanzxing.utils.ImageSaveUtil;
import com.example.xch.scanzxing.utils.zxing.android.CaptureActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.HashMap;

public class OriginFragment extends BaseFragment implements View.OnClickListener{
    LinearLayout rl_createCode,rl_record,rl_distribute,rl_exit;
    public static TextView tv_date;
    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private static final int REQUEST_CODE_SCAN1 = 0x0001;
    private static final int REQUEST_CODE_SCAN2 = 0x0002;
    public static OriginFragment newInstance() {
        return new OriginFragment();
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_origin;
    }

    @Override
    protected void initView() {
        rl_createCode = mRootView.findViewById(R.id.rl_createCode);
        rl_record = mRootView.findViewById(R.id.rl_record);
    }

    @Override
    protected void initData() {
        rl_createCode.setOnClickListener(this);
        rl_record.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_createCode:
                createQR();
                break;
            case R.id.rl_record:
                recordQR();
                break;
        }
    }

    private void createQR() {
        View view = View.inflate(getActivity(),R.layout.dialog,null);
        Spinner sp_covid = view.findViewById(R.id.sp_covid);

        ImageView iv_date = view.findViewById(R.id.iv_date);
        tv_date = view.findViewById(R.id.tv_date);
        iv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getChildFragmentManager(), "datePicker");
            }
        });
        sp_covid.setAdapter(new ArrayAdapter<String >(getActivity(),R.layout.support_simple_spinner_dropdown_item,new String[]{"阴性","阳性"}));
        new AlertDialog.Builder(getActivity()).setTitle("创建产品二维码").setView(view).setNegativeButton("取消",null).setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText et_goodsName = view.findViewById(R.id.et_goodsName);
                EditText et_brand = view.findViewById(R.id.et_brand);
                EditText et_category = view.findViewById(R.id.et_category);
                EditText et_weight = view.findViewById(R.id.et_weight);
                EditText et_storage = view.findViewById(R.id.et_storage);
                EditText et_life = view.findViewById(R.id.et_life);
                EditText et_ShelfLife = view.findViewById(R.id.et_ShelfLife);

                HashMap<String, Object> params = new HashMap<>();
                params.put("GoodName", et_goodsName.getText().toString());
                params.put("brand", et_brand.getText().toString());
                params.put("category", et_category.getText().toString());
                params.put("weight", et_weight.getText().toString());
                params.put("storage", et_storage.getText().toString());
                params.put("life", et_life.getText().toString());
                params.put("ProductionDate", tv_date.getText().toString());
                params.put("ShelfLife", et_ShelfLife.getText().toString());
                params.put("covid", sp_covid.getSelectedItem().toString().equals("阳性"));
                //根据OkHttp请求数据进行登录校验
                Api.config(ApiConfig.GOOD_ADD, params).postRequest(new TtitCallback() {
                    @Override
                    public void onSuccess(final String res) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson = new Gson();
                                GoodMessage goodName = gson.fromJson(res, GoodMessage.class);
                                if (goodName.getErrorCode().equals("00000")) {
                                    Bitmap codeBitmap = EncodingUtils.createQRCode(goodName.getData().getId(), 500, 500, null);
                                    Uri uri = ImageSaveUtil.saveAlbum(getActivity(), codeBitmap, Bitmap.CompressFormat.JPEG, 1, true);
                                    Snackbar.make(mRootView.findViewById(R.id.contentView), "二维码成功保存到相册", Snackbar.LENGTH_SHORT).setAction("分享", new View.OnClickListener() {
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
                                    System.out.println(goodName.getErrorMessage());
                                    Snackbar.make(mRootView.findViewById(R.id.contentView), goodName.getErrorMessage(), Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    @Override
                    public void onFailure(Exception e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Snackbar.make(mRootView.findViewById(R.id.contentView), "网络异常", Snackbar.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        }).show();
    }



    private void recordQR() {
        Intent intent = new Intent(getActivity(), CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN2);
    }
}
