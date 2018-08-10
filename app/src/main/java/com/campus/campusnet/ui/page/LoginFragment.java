package com.campus.campusnet.ui.page;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.campus.android_bind.bean.NgGo;
import com.campus.android_bind.bean.NgModel;
import com.campus.android_bind.observer.AllPropertyObserver;
import com.campus.william.router.ui.IFragment;
import com.campus.william.user.R;

import java.util.HashMap;

public class LoginFragment extends IFragment {
    private Context mContext;
    private View mContainer;
    private NgModel mNgModel;
    private NgGo mNgGo;

    private AllPropertyObserver mPropertyObserver = new AllPropertyObserver() {
        @Override
        public void dataChange(String tag, Object object) {
            super.dataChange(tag, object);
            if (tag.equals("phone")) {
                mNgModel.addParams("deletePhone",
                        ((String) mNgModel.getValue("phone")).length() > 0 ? NgGo.NG_VISIBLE.VISIBLE : NgGo.NG_VISIBLE.GONE);
            } else if (tag.equals("password")) {
                mNgModel.addParams("deletePassword",
                        ((String) mNgModel.getValue("password")).length() > 0 ? NgGo.NG_VISIBLE.VISIBLE : NgGo.NG_VISIBLE.GONE);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = inflater.getContext();
        initNgGo();
        return mContainer;
    }

    @Override
    public void reInit(HashMap bundle) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNgGo.release();
    }

    private void initNgGo() {
        mNgModel = new NgModel("user");
        mNgGo = new NgGo(mContext, R.layout.layout_login)
                .addNgModel(mNgModel)
                .addAllPropertyObserver(mPropertyObserver)
                .addActionContainer(this);
        mContainer = mNgGo.inflateAndBind();
    }

    public void login(View view) {

    }

    public void forgetPassword(View view) {

    }

    public void deletePassword(View view) {
        mNgModel.addParams("password", "");
    }

    public void deletePhone(View view) {
        mNgModel.addParams("phone", "");
    }

    public void back(View view) {
        routerProvider.back();
    }
}
