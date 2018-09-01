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
import com.campus.campusnet.R;
import com.campus.william.annotationprocessor.annotation.RouterUrl;
import com.campus.william.router.ui.IFragment;

import java.util.HashMap;

@RouterUrl(state = "LOGIN", desc = "登陆页面")
public class LoginFragment extends IFragment {
    private Context mContext;
    private View mContainer;
    private NgModel mNgModel;//声明一个用来绑定UI的数据对象
    private NgGo mNgGo;//绑定执行者，只用来初始化的时候进行绑定

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
        mNgModel.addParams("loginState", "登陆");
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
        /**
         * 绑定三部曲
         * 1.创建数据对象NgModel tag为对象名字
         * 2.创建绑定执行者NgGo 第二个参数为具体的布局文件
         * 3.通过addNgModel方法给绑定执行者设置第一步创建好的数据对象
         * 4.如果需要监听数据对象属性的变化，需要调用addAllPropertyObserver方法，添加一个属性变化的监听者
         * 5.如果通过xml设置了click/longClick方法，则需要把方法的拥有者设置到绑定执行者中
         * 6.调用inflateAndBind方法生成一个已经绑定好UI及数据的view
         */
        mNgModel = new NgModel("user");
        mNgGo = new NgGo(mContext, R.layout.layout_login)
                .addNgModel(mNgModel)
                .addAllPropertyObserver(mPropertyObserver)
                .addActionContainer(this);
        mContainer = mNgGo.inflateAndBind();
    }

    //此方法是通过xml中设置的
    public void deletePassword(View view) {
        mNgModel.addParams("password", "");
    }

    public void deletePhone(View view) {
        mNgModel.addParams("phone", "");
    }

    public void login(View view) {
        view.setClickable(false);
        //TODO 用户点击了登陆
        mNgModel.addParams("loginState", "登陆...");
        String phone = (String)mNgModel.getValue("phone");
        String password = (String)mNgModel.getValue("password");

    }

    public void lookUp(View view){
        //TODO 逛一逛
    }

    public void openUserAgreement(View view){
        //TODO 打开用户协议
    }
}
