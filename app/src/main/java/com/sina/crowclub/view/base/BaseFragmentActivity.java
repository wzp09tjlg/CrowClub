package com.sina.crowclub.view.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.sina.crowclub.R;
import com.sina.crowclub.utils.LogUtil;
import com.sina.crowclub.view.widget.CommonDialog;

/**
 * Created by wu on 2016/7/15.
 */
public abstract class BaseFragmentActivity extends FragmentActivity {
    private final String TAG = BaseFragmentActivity.class.getSimpleName();
    /** View */
    private Dialog mProgressDialog;

    /** Data */
    private Context mContext = this;

    /***************************************************************/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /** 提供的一个实例view的方法 */
    protected <T extends View> T $(int id){
        return (T)super.findViewById(id);
    }

    protected <T extends View> T $(View view,int id){
        return (T)view.findViewById(id);
    }

    /** 提供的弹框 */
    public void showProgress(int resId, boolean cancelAble) {
        showProgress(getString(resId), cancelAble, null);
    }

    public void showProgress(String message, boolean cancelAble) {
        showProgress(message, cancelAble, null);
    }

    public void showProgress(int resId, boolean cancelAble, DialogInterface.OnKeyListener listener) {
        showProgress(getString(resId), cancelAble, listener);
    }

    public void showProgress(String message, boolean cancelAble, DialogInterface.OnKeyListener listener) {
        if(mContext == null) return;
        if (mProgressDialog == null) {
            mProgressDialog = new CommonDialog(mContext);
            View view = LayoutInflater.from(mContext).inflate(R.layout.view_dialog_progress, null);
            if (!TextUtils.isEmpty(message)) {
                ((TextView)$(view,R.id.progress_text)).setText(message);
            }
            mProgressDialog.setContentView(view);
            mProgressDialog.setCancelable(cancelAble);
            mProgressDialog.setOnKeyListener(listener);
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.findViewById(R.id.root_view).setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_dialog_custom_bg));
            try{
                mProgressDialog.show();
            }catch (Exception e){//做异常处理,防止出现极端情况,在显示的时候就退出activity导致dailog所依附的window为空
                LogUtil.e("e.msg;" + e.getMessage());
            }
        }
    }

    public void dismissProgress() {
        if (null != mProgressDialog && mProgressDialog.isShowing()) {
            try{
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }catch (Exception e){//做一场处理,防止极端情况,调用显示之后立马退出.
                LogUtil.e("e.msg;" + e.getMessage());
            }
        }
    }
}
