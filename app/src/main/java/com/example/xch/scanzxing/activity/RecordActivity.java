package com.example.xch.scanzxing.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xch.scanzxing.R;
import com.example.xch.scanzxing.adapter.RecyclerViewAdapter;

public class RecordActivity extends BaseActivity{
    RecyclerView recyclerView;
    private Toolbar my_toolbar;
    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private ImageView iv_details;
    public static RecyclerViewAdapter mAdapter;//适配器
    private LinearLayoutManager mLinearLayoutManager;//布局管理器

    static  TextView tv_record;
    @Override
    protected int initLayout() {
        return R.layout.activity_record;
    }

    @Override
    protected void initView() {
        recyclerView = findViewById(R.id.recyclerview);
        tv_record = findViewById(R.id.tv_record);
        my_toolbar = findViewById(R.id.my_toolbar);
        iv_details = findViewById(R.id.iv_details);
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void initData() {
        if (MainActivity.originData.size() > 0){
            tv_record.setVisibility(View.GONE);
        }
        //创建布局管理器，垂直设置LinearLayoutManager.VERTICAL，水平设置LinearLayoutManager.HORIZONTAL
        mLinearLayoutManager = new LinearLayoutManager(RecordActivity.this, LinearLayoutManager.VERTICAL, false);
        //创建适配器，将数据传递给适配器
        mAdapter = new RecyclerViewAdapter(MainActivity.originData,RecordActivity.this);
        //设置布局管理器垂直排列，显示分割线
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(RecordActivity.this,
                DividerItemDecoration.VERTICAL));
        //滑动动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置适配器adapter
        recyclerView.setAdapter(mAdapter);
        my_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        iv_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = View.inflate(RecordActivity.this,R.layout.dialog_show,null);
                TextView tv_goodsName = v.findViewById(R.id.tv_goodsName);
                TextView tv_brand = v.findViewById(R.id.tv_brand);
                TextView tv_category = v.findViewById(R.id.tv_category);
                TextView tv_weight = v.findViewById(R.id.tv_weight);
                TextView tv_storage = v.findViewById(R.id.tv_storage);
                TextView tv_life = v.findViewById(R.id.tv_life);
                TextView tv_ProductionDate = v.findViewById(R.id.tv_ProductionDate);
                TextView tv_ShelfLife = v.findViewById(R.id.tv_ShelfLife);
                TextView tv_covid = v.findViewById(R.id.tv_covid);

                tv_goodsName.setText(MainActivity.originData.get(0).getGoods().getGoodName());
                tv_brand.setText(MainActivity.originData.get(0).getGoods().getBrand());
                tv_category.setText(MainActivity.originData.get(0).getGoods().getCategory());
                tv_weight.setText(MainActivity.originData.get(0).getGoods().getWeight());
                tv_storage.setText(MainActivity.originData.get(0).getGoods().getStorage());
                tv_life.setText(MainActivity.originData.get(0).getGoods().getLife());
                tv_ProductionDate.setText(MainActivity.originData.get(0).getGoods().getProductionDate());
                tv_ShelfLife.setText(MainActivity.originData.get(0).getGoods().getShelfLife());
                tv_covid.setText(MainActivity.originData.get(0).getGoods().isCovid() ? "阳性" : "阴性");
                new AlertDialog.Builder(RecordActivity.this).setTitle("商品详情").setView(v).setPositiveButton("确认", null).show();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}