package com.zw.happybuy.http;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

import com.zw.happybuy.R;

/**
 * Created by Administrator on 2017/8/25.
 */

public class ProgressDialogHandler extends Handler {

    public static final int SHOW_PROGRESSDIALOG = 1;
    public static final int DISMISS_PROGRESSDIALOG = 2;

    interface OnProgressDialogCannelListener{
        void onCannelProgress();
    }

    private ProgressDialog mDialog;
    private Context mContext;
    private OnProgressDialogCannelListener mListener;
    private boolean canCannel;

    public ProgressDialogHandler(Context context,OnProgressDialogCannelListener listener,boolean canCannel){
        this.mContext = context;
        this.mListener = listener;
        this.canCannel = canCannel;
    }

    private void initProgressDialog(){
        if(mDialog == null){
            mDialog = new ProgressDialog(mContext);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setMessage("加载中 ...");
            mDialog.setCancelable(canCannel);
            if(canCannel){
                mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        mListener.onCannelProgress();
                    }
                });
            }
        }
        if(!mDialog.isShowing()){
            mDialog.show();
        }
    }

    private void dissmissDialog(){
        if(mDialog != null){
            mDialog.dismiss();
            mDialog = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case SHOW_PROGRESSDIALOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESSDIALOG:
                dissmissDialog();
                break;
        }
    }
}
