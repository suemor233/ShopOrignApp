package com.example.xch.scanzxing.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xch.scanzxing.R;
import com.example.xch.scanzxing.activity.DistributeActivity;
import com.example.xch.scanzxing.activity.informationActivity;
import com.example.xch.scanzxing.api.Api;
import com.example.xch.scanzxing.api.ApiConfig;
import com.example.xch.scanzxing.api.TtitCallback;
import com.example.xch.scanzxing.entity.UserMessage;
import com.example.xch.scanzxing.utils.zxing.android.CaptureActivity;
import com.google.gson.Gson;

import java.util.HashMap;

public class WareHouseFragment extends BaseFragment implements View.OnClickListener{
    private static final int REQUEST_CODE_SCAN2 = 0x0002;
    LinearLayout rl_distributeInformation,rl_record,rl_distribute,rl_data;
    public static WareHouseFragment newInstance() {
        return new WareHouseFragment();
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_warehouse;
    }

    @Override
    protected void initView() {

        rl_distributeInformation = mRootView.findViewById(R.id.rl_distributeInformation);
        rl_record = mRootView.findViewById(R.id.rl_record);
        rl_distribute = mRootView.findViewById(R.id.rl_distribute);
        rl_data = mRootView.findViewById(R.id.rl_data);

    }

    @Override
    protected void initData() {
        rl_distributeInformation.setOnClickListener(this);
        rl_record.setOnClickListener(this);
        rl_distribute.setOnClickListener(this);
        rl_data.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_distributeInformation:
                DistributeInformation();
                break;
            case R.id.rl_record:
                recordQR();
                break;
            case R.id.rl_distribute:
                Distribute();
                break;
            case R.id.rl_data:
                showDialog();
                break;
        }
    }



    private void DistributeInformation() {
        startActivity(new Intent(getActivity(), informationActivity.class));
    }

    private void recordQR() {
        Intent intent = new Intent(getActivity(), CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN2);
    }

    private void Distribute() {
        startActivity(new Intent(getActivity(), DistributeActivity.class));
    }

    private void showDialog() {
        View view = View.inflate(getActivity(), R.layout.warehouse_dialog, null);
        new AlertDialog.Builder(getActivity()).setView(view).setPositiveButton("关闭",null).show();
        TextView et_username = view.findViewById(R.id.tv_username);
        TextView tv_name = view.findViewById(R.id.tv_name);
        TextView tv_location = view.findViewById(R.id.tv_location);
        TextView tv_weight_max = view.findViewById(R.id.tv_weight_max);
        TextView tv_weight = view.findViewById(R.id.tv_weight);
        TextView tv_weight_left = view.findViewById(R.id.tv_weight_left);
        TextView tv_data = view.findViewById(R.id.tv_data);

      UserMessage user =   getUser();
        System.out.println(user);

    }

    private UserMessage getUser() {
        HashMap<String, Object> params = new HashMap<>();
        //根据OkHttp请求数据进行登录校验
        final UserMessage[] user = new UserMessage[1];
        Api.config(ApiConfig.USERINFO + "/" + getStringFromSp("userId"), params).getRequest(new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        System.out.println(res);
                         user[0] = gson.fromJson(res, UserMessage.class);

                    }
                });

            }
            @Override
            public void onFailure(Exception e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });

        return user[0];
    }

}
