package com.example.xch.scanzxing.activity;

import android.content.Intent;
import android.graphics.Bitmap;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.xch.scanzxing.R;
import com.example.xch.scanzxing.adapter.MyPagerAdapter;
import com.example.xch.scanzxing.api.Api;
import com.example.xch.scanzxing.api.ApiConfig;
import com.example.xch.scanzxing.api.TtitCallback;
import com.example.xch.scanzxing.entity.OriginData;
import com.example.xch.scanzxing.entity.OriginMessage;
import com.example.xch.scanzxing.entity.TabEntity;
import com.example.xch.scanzxing.fragment.CovidFragment;
import com.example.xch.scanzxing.fragment.LogisticsFragment;
import com.example.xch.scanzxing.fragment.OriginFragment;
import com.example.xch.scanzxing.fragment.WareHouseFragment;
import com.example.xch.scanzxing.utils.StringUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//主界面的ViewPager
public class MainActivity extends BaseActivity {
    //下方的工具栏
    private String[] mTitles = new String[4];
    private int[] mIconUnselectIds = new int[4];
    private int[] mIconSelectIds = new int[4];
    public static List<OriginData> originData = new ArrayList<>();

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    public static ViewPager viewPager;
    private CommonTabLayout commonTabLayout;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";


    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        viewPager = findViewById(R.id.viewpager);
        commonTabLayout = findViewById(R.id.commonTabLayout);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        //集合添加fragment
        switch (category) {
            case "send":
                mTitles[0] = "物流";
                mTitles[1] = "疫情";
                mIconUnselectIds[0] = R.mipmap.logistics_unselect;
                mIconUnselectIds[1] = R.mipmap.covid_unselect;
                mIconSelectIds[0] = R.mipmap.logistics_select;
                mIconSelectIds[1] = R.mipmap.covid_select;
                mFragments.add(LogisticsFragment.newInstance());
                mFragments.add(CovidFragment.newInstance(this));
                break;
            case "supplier":
                mTitles[0] = "商品";
                mTitles[1] = "疫情";
                mIconUnselectIds[0] = R.mipmap.shop_unselect;
                mIconUnselectIds[1] = R.mipmap.covid_unselect;
                mIconSelectIds[0] = R.mipmap.shop_select;
                mIconSelectIds[1] = R.mipmap.covid_select;
                mFragments.add(OriginFragment.newInstance());
                mFragments.add(WareHouseFragment.newInstance());
                break;
            case "user":
                mTitles[0] = "物流";
                mTitles[1] = "疫情";
                mIconUnselectIds[0] = R.mipmap.logistics_unselect;
                mIconUnselectIds[1] = R.mipmap.covid_unselect;
                mIconSelectIds[0] = R.mipmap.logistics_select;
                mIconSelectIds[1] = R.mipmap.covid_select;
                mFragments.add(LogisticsFragment.newInstance());
                mFragments.add(CovidFragment.newInstance(this));
                break;
            case "warehouse":
                mTitles[0] = "仓储";
                mTitles[1] = "疫情";
                mIconUnselectIds[0] = R.mipmap.save_unselect;
                mIconUnselectIds[1] = R.mipmap.covid_unselect;
                mIconSelectIds[0] = R.mipmap.save_select;
                mIconSelectIds[1] = R.mipmap.covid_select;
                mFragments.add(WareHouseFragment.newInstance());
                mFragments.add(CovidFragment.newInstance(this));
                break;

        }
//        mFragments.add(WareHouseFragment.newInstance());
//        mFragments.add(LogisticsFragment.newInstance());
//        mFragments.add(CovidFragment.newInstance(this));
        new CovidFragment(this);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        //点击下方实现fragment界面切换
        commonTabLayout.setTabData(mTabEntities);
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        //滑动切换
        viewPager.setOffscreenPageLimit(mFragments.size());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                commonTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),mTitles,mFragments));


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
            System.out.println(requestCode + "  " + resultCode + " " + data);
            if ( resultCode == RESULT_OK) {

                HashMap<String, Object> params = new HashMap<>();
                //根据OkHttp请求数据进行登录校验
                params.put("id", content);
                Api.config(ApiConfig.ORIGIN_GET, params).getRequest(new TtitCallback() {
                    @Override
                    public void onSuccess(final String res) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson = new Gson();
                                OriginMessage originMessage = gson.fromJson(res, OriginMessage.class);
                                originData.clear();                                if (originMessage.getErrorCode().equals("00000")) {
                                    if (originMessage.getData().size() > 0) {
                                        for (int i = 0; i < originMessage.getData().size(); i++) {
                                            String stringBuffer = StringUtils.subTime(originMessage.getData().get(i).getCreatedAt());
                                            originData.add(new OriginData(originMessage.getData().get(i).getOriginName(),originMessage.getData().get(i).getOriginDesc(),stringBuffer.toString(),originMessage.getData().get(i).getGoods()));

                                            if (viewPager.getCurrentItem() == 3){
                                                if (originMessage.getData().get(i).getOriginName().equals("蒙古")){
                                                    new AlertDialog.Builder(MainActivity.this).setTitle("当前商品: " + originMessage.getData().get(i).getGoods().getGoodName()).setMessage("处于高风险区！！！").setPositiveButton("好的", null).show();
                                                    return;
                                                }else {
                                                    if (i == originMessage.getData().size() - 1){
                                                        new AlertDialog.Builder(MainActivity.this).setTitle("当前商品: " +originMessage.getData().get(i).getGoods().getGoodName()).setMessage("处于低风险区").setPositiveButton("好的", null).show();
                                                    }
                                                }
                                            }
                                        }
                                    }else {
                                        Snackbar.make(findViewById(R.id.contentView), "请扫描商品二维码", Snackbar.LENGTH_SHORT).show();
                                    }
                                    //曲线救国
                                    if (viewPager.getCurrentItem() != 3){
                                        startActivity(new Intent(MainActivity.this,RecordActivity.class));
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