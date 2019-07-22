package com.watiao.yishuproject.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.watiao.yishuproject.AppContext;
import com.watiao.yishuproject.R;

/**
 * @author WH
 * @Title: ExtAlertDialog.java
 * @Package duoduo.app.widgets
 * @Description: 自定义alertDialog
 * @date 2015年3月7日 下午12:58:14
 */
public class ExtAlertDialog {

    /**
     * @param @param context 结构上下文
     * @param @param title 对话框标题
     * @param @param msg 显示类容
     * @return void
     * @Description: 显示自定义AlertDialog
     */
    public static void showDialog(Context context, int title, int msg) {
        Resources res = context.getResources();
        final AlertDialog dlg = new AlertDialog.Builder(context).create();
        dlg.show();
        dlg.setContentView(R.layout.dialog_layout1);
        dlg.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent);
        TextView titleTxt = (TextView) dlg.findViewById(R.id.ext_dialog_title);
        EditText phone=dlg.findViewById(R.id.txt_phone);
        EditText yzm=dlg.findViewById(R.id.txt_yzm);
        Button btn = (Button) dlg.findViewById(R.id.next);
        View hasTitle = dlg.findViewById(R.id.ext_dialog_hasTitle);
        if (TextUtils.isEmpty(res.getString(title)))
            hasTitle.setVisibility(View.GONE);
        else
            titleTxt.setText(res.getString(title));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });
        dlg.setCancelable(false);
    }


    public interface IExtDlgClick {
        public void Oclick(int result);
    }


    public static void showSureDlg(Context context, String title, String msg, String btnTxt, boolean isCancelable, final IExtDlgClick onclickListener) {
        final AlertDialog dlg = new AlertDialog.Builder(context).create();
        dlg.show();
        dlg.setContentView(R.layout.ext_cancel_sure_dialog);
        dlg.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent);
        View titleLayout = dlg.findViewById(R.id.title_layout);
        TextView titleTxt = (TextView) dlg.findViewById(R.id.ext_dialog_title);
        if (TextUtils.isEmpty(title))
            titleLayout.setVisibility(View.GONE);
        else
            titleTxt.setText(title);
        TextView contentTxt = (TextView) dlg
                .findViewById(R.id.content);
        if(msg!=null&&!msg.equals("")){
            contentTxt.setText(msg);
        }
        else {
            contentTxt.setText("无备注");
        }
        Button btn = (Button) dlg.findViewById(R.id.ext_dialog_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onclickListener != null)
                    onclickListener.Oclick(0);
                dlg.dismiss();
            }
        });

        Button sureBnt = (Button) dlg.findViewById(R.id.sure);
        if (!TextUtils.isEmpty(btnTxt))
            sureBnt.setText(btnTxt);

        sureBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onclickListener != null)
                    onclickListener.Oclick(1);
                dlg.dismiss();
            }
        });
        dlg.setCancelable(isCancelable);
        dlg.setCancelable(false);
    }



    public static void showSureDlg2(Context context, String msg, String btnTxt, boolean isCancelable, final IExtDlgClick onclickListener) {
        final AlertDialog dlg = new AlertDialog.Builder(context).create();
        dlg.show();
        dlg.setContentView(R.layout.ext_cancel_sure_dialog2);
        dlg.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent);
        TextView contentTxt = (TextView) dlg
                .findViewById(R.id.content);
        if(msg!=null&&!msg.equals("")){
            contentTxt.setText(msg);
        }
        else {
            contentTxt.setText("无备注");
        }
        Button btn = (Button) dlg.findViewById(R.id.ext_dialog_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onclickListener != null)
                    onclickListener.Oclick(0);
                dlg.dismiss();
            }
        });

        Button sureBnt = (Button) dlg.findViewById(R.id.sure);
        if (!TextUtils.isEmpty(btnTxt))
            sureBnt.setText(btnTxt);

        sureBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onclickListener != null)
                    onclickListener.Oclick(1);
                dlg.dismiss();
            }
        });
        dlg.setCancelable(isCancelable);
        dlg.setCancelable(false);
    }



    public static Dialog creatRequestDialog(final Context context, String tip) {

        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.dialog_layout4);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha=0.5f;
        window.clearFlags( WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setAttributes(lp);
        int width = AppContext.getInstance().getDisplayWidth();
        lp.width = (int) (0.3 * width);
        lp.height = (int) (0.3 * width);

        TextView tvTitle = (TextView) dialog.findViewById(R.id.tvLoad);
        tvTitle.setText(tip);

        return dialog;
    }


    public static Dialog creatRequestDialog2(final Context context, String tip) {

        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.alert_dialog_layout);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        int width = AppContext.getInstance().getDisplayWidth();
        lp.width = (int) (0.3 * width);
        lp.height = (int) (0.3 * width);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tvLoad);
        tvTitle.setText(tip);


        return dialog;
    }

}
