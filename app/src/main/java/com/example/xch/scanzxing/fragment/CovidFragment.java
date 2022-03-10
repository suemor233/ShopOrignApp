package com.example.xch.scanzxing.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.xch.scanzxing.R;
import com.example.xch.scanzxing.utils.zxing.android.CaptureActivity;

public class CovidFragment extends BaseFragment implements View.OnClickListener{
    private static final int REQUEST_CODE_SCAN2 = 0x0002;
    private static final int REQUEST_CODE_SCAN3 = 0x0003;
    private Context context;
    LinearLayout rl_record,rl_information;
    public static CovidFragment newInstance() {
        return new CovidFragment();
    }

    public static CovidFragment newInstance(Context context) {
        return new CovidFragment(context);
    }

    public CovidFragment() {
    }
    public CovidFragment(Context context) {
        this.context = context;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_covid;
    }

    @Override
    protected void initView() {
        rl_record = mRootView.findViewById(R.id.rl_record);
        rl_information = mRootView.findViewById(R.id.rl_information);
    }

    @Override
    protected void initData() {
        rl_record.setOnClickListener(this);
        rl_information.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_record:
                recordQR();
                break;
            case R.id.rl_information:
                information();
                break;
        }
    }


    private void recordQR() {
        Intent intent = new Intent(getActivity(), CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN2);
    }

    private void information() {
        Intent intent = new Intent(context, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN3);
    }


}
