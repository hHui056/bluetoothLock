package com.hh.bluetoothlock.widget;

import android.content.Context;
import android.text.TextUtils;

import com.hh.bluetoothlock.ui.BaseActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class MyAlertDialog {
    private Context mContext;
    private SweetAlertDialog alertDialog;

    public MyAlertDialog(Context activity) {
        mContext = activity;
    }

    public interface AlertClickBack {
        void onConfirm();

        void onCanle();
    }

    public void showDialogLoading(String message) {
        alertDialog = null;
        alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE).setTitleText(TextUtils.isEmpty(message) ? "处理中..." : message)
                .setContentText("");
        alertDialog.show();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
    }

    public void closeDialogLoading() {
        if (mContext instanceof BaseActivity) {
            if (!((BaseActivity) mContext).isFinishing() && !((BaseActivity) mContext).isDestroyed()) {
                if (alertDialog != null) {
                    alertDialog.dismissWithAnimation();
                    alertDialog.dismiss();
                    alertDialog = null;
                }
            }
        } else {
            if (alertDialog != null) {
                alertDialog.dismissWithAnimation();
                alertDialog.dismiss();
                alertDialog = null;
            }
        }
    }

    public void dissmisDialog() {
        closeDialogLoading();
    }

    /**
     * 普通弹出框
     *
     * @param msg
     */
    public void alertMsg(String msg) {
        if (alertDialog != null && alertDialog.isShowing()) {
            closeDialogLoading();
        }
        alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.NORMAL_TYPE).setTitleText(msg).setContentText("");
        alertDialog.show();
    }

    /**
     * 成功弹出框
     *
     * @param msg
     */
    public void alertSuccessMsg(String msg) {
        if (alertDialog != null && alertDialog.isShowing()) {
            closeDialogLoading();
        }
        alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE).setTitleText(msg).setContentText("");
        alertDialog.show();
    }

    /**
     * 警告弹出框
     *
     * @param title
     * @param msg
     */
    public void alertWaringMsg(String title, String msg) {
        if (alertDialog != null && alertDialog.isShowing()) {
            closeDialogLoading();
        }
        alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE).setTitleText(title).setContentText(msg);
        alertDialog.show();
    }

    /**
     * 错误信息弹出框
     *
     * @param title
     * @param msg
     */
    public void alertErrorMsg(String title, String msg) {
        if (alertDialog != null && alertDialog.isShowing()) {
            closeDialogLoading();
        }
        alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE).setTitleText(title).setContentText(msg);
        alertDialog.show();
    }

    /**
     * 带确定取消按钮额弹出框
     *
     * @param title
     * @param msg
     * @param callBack
     */
    public void alertOption(String title, String msg, final AlertClickBack callBack) {
        if (alertDialog != null && alertDialog.isShowing()) {
            closeDialogLoading();
        }
        alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText(title).setContentText(msg)
                .showCancelButton(true)
                .setCancelText("取消")
                .setConfirmText("确定")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        callBack.onConfirm();
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        alertDialog.dismissWithAnimation();
                        callBack.onCanle();
                    }
                });
        alertDialog.show();
    }

}
