package com.aliyun.apsara.alivclittlevideo.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.aliyun.apsara.alivclittlevideo.AppContext;
import com.aliyun.apsara.alivclittlevideo.R;

public class ExtAlertDialog {

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
