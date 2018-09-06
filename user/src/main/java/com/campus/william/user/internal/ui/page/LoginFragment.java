package com.campus.william.user.internal.ui.page;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.campus.android_bind.bean.NgGo;
import com.campus.android_bind.bean.NgModel;
import com.campus.android_bind.observer.AllPropertyObserver;
import com.campus.event_filter.callback.ICallback;
import com.campus.event_filter.request.IRequest;
import com.campus.event_filter.request.MODE;
import com.campus.event_filter.response.IResponse;
import com.campus.william.annotationprocessor.annotation.PageUrl;
import com.campus.william.router.logic.ActivityProvider;
import com.campus.william.router.logic.RouterFactory;
import com.campus.william.router.ui.IFragment;
import com.campus.william.user.R;

import java.util.HashMap;

/**
 * Created by wenge on 2018/9/1.
 */
@PageUrl(state = "LOGIN", desc = "登陆页面")
public class LoginFragment extends IFragment {

    private View mContainer;
    private NgModel mNgModel;
    private NgGo mNaGo;

    private Toast mToasts;

    private AllPropertyObserver mPropertyObserver = new AllPropertyObserver() {
        @Override
        public void dataChange(String tag, Object object) {
            super.dataChange(tag, object);

        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initNgGo();
        return mContainer;
    }

    @Override
    public void reInit(HashMap bundle) {

    }

    private void initNgGo() {
        mNgModel = new NgModel("user");
        mNaGo = new NgGo(getActivity(), R.layout.layout_login)
                .addNgModel(mNgModel)
                .addAllPropertyObserver(mPropertyObserver)
                .addActionContainer(this);
        mContainer = mNaGo.inflateAndBind();
    }

    /**
     * 发送验证码点击回调方法
     *
     * @param view 是为了区分可能有多个View绑定了这个方法，通过View来区分
     **/
    public void sendVerificationCode(View view) {
        final String phoneNumber = mNgModel.getString("phoneNumber");
        if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() != 11) {
            showToasts("请输入正确的手机号");
            return;
        }

        IRequest.obtain()
                .action("sendPhoneCode")
                .add("phoneNumber", mNgModel.getValue("phoneNumber"))
                .submit(MODE.IO, MODE.UI, new ICallback() {
                    @Override
                    public void done(IResponse response) {
                        super.done(response);
                        //验证码返回
                    }
                });
    }

    /**
     * 登录点击回调
     **/
    public void login(View view) {
        final String phoneNumber = mNgModel.getString("phoneNumber");
        if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() != 11) {
            showToasts("请输入正确的手机号");
            return;
        }
        final String code = mNgModel.getString("code");
        if (TextUtils.isEmpty(code)) {
            showToasts("请输入验证码");
            return;
        }

        IRequest.obtain().
                action("userLogin")
                .add("phoneNumber", phoneNumber)
                .add("token", code)
                .submit(MODE.IO, MODE.UI, new ICallback() {
                    @Override
                    public void done(IResponse response) {
                        super.done(response);
                        if(response.getException() == null){
                            routerProvider.release();
                            getActivity().finish();
                            ActivityProvider activityProvider = RouterFactory.getInstance()
                                    .obtainAcitivtyProvider();
                            activityProvider.setState("MAIN")
                                    .navigate(getContext());
                        }else{
                            showToasts(response.getException().getMessage());
                        }
                    }
                });
    }

    /**
     * 逛一逛点击回调
     **/
    public void lookUp(View view) {
      //todo 这里后续对接
        showToasts("暂无该功能");
    }

    /**
     * 用户协议点击回调
     **/
    public void openUserAgreement(View view) {
        //todo 用户协议跳转
        showToasts("暂无该功能");
    }

    private Toast getToasts() {
        if (mToasts == null) {
            mToasts = Toast.makeText(getActivity().getApplicationContext(), "", Toast.LENGTH_SHORT);
        }
        return mToasts;
    }

    private void showToats(@StringRes int resId) {
        final String strRes = getActivity().getString(resId);
        showToasts(strRes);
    }

    private void showToasts(String msg) {
        final Toast toast = getToasts();
        toast.setText((msg == null ? "" : msg));
        toast.show();
    }

}
