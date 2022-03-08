package com.example.xch.scanzxing.utils;

import android.app.Activity;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.xch.scanzxing.R;
import com.example.xch.scanzxing.activity.MainActivity;


//自定义的提示框
public class DialogUtil {
    private static AlertDialog dialog;

    /**
     * @param activity                    Context
     * @param title                       提示标题
     * @param positiveText                确认
     * @param negativeText                取消
     * @param cancelableTouchOut          点击外部是否隐藏提示框
     * @param alertDialogBtnClickListener 点击监听
     */
    public static void showAlertDialog(Activity activity,String title,
                                       String positiveText, String negativeText, boolean
                                               cancelableTouchOut, boolean useEtTwo,final AlertDialogBtnClickListener
                                               alertDialogBtnClickListener) {
        View view = LayoutInflater.from(activity).inflate(R.layout.custom_dialog_layout, null);
        TextView mTitle = view.findViewById(R.id.title);
        EditText mMessage1 = view.findViewById(R.id.message1);
        EditText mMessage2 = view.findViewById(R.id.message2);
        Button positiveButton = view.findViewById(R.id.positiveButton);
        Button negativeButton = view.findViewById(R.id.negativeButton);
        mTitle.setText(title);
        positiveButton.setText(positiveText);
        negativeButton.setText(negativeText);
        if (!useEtTwo) {
            mMessage2.setVisibility(View.GONE);
            SpannableString s = new SpannableString("请输入商品");
            mMessage1.setHint(s);
        }else {
            SpannableString s = new SpannableString("请输入产地");
            mMessage1.setHint(s);
            SpannableString s2 = new SpannableString("请输入车牌号");
            mMessage2.setHint(s2);
        }
        positiveButton.setOnClickListener(v -> {
            alertDialogBtnClickListener.clickPositive(mMessage1,mMessage2);

            dialog.dismiss();
        });
        negativeButton.setOnClickListener(v -> {
            alertDialogBtnClickListener.clickNegative();
            dialog.dismiss();
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view);

        builder.setCancelable(true);   //返回键dismiss
        //创建对话框
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//去掉圆角背景背后的棱角
        dialog.setCanceledOnTouchOutside(cancelableTouchOut);   //失去焦点dismiss
        dialog.show();

    }


    public interface AlertDialogBtnClickListener {
        void clickPositive(EditText editText,EditText editText2);

        void clickNegative();
    }
}
