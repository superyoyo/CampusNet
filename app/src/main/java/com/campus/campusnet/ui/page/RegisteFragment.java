package com.campus.campusnet.ui.page;

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
import com.campus.william.router.ui.IFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegisteFragment extends IFragment {
    private View mContainer;
    private NgGo mNgGo;
    private NgModel mNgUser;
    private NgModel mNgPage;
    private AllPropertyObserver mAllPropertyObserver = new AllPropertyObserver(){
        @Override
        public void dataChange(String tag, Object object) {
            super.dataChange(tag, object);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initNgGo();
        switchSchoolAuthentic();
        return mContainer;
    }

    @Override
    public void reInit(HashMap bundle) {
    }

    private void initNgGo(){
        mNgUser = new NgModel("user");
        mNgPage = new NgModel("page");
        mNgGo = new NgGo(getContext(), R.layout.layout_registe);
        mContainer = mNgGo.addActionContainer(this)
                .addNgModel(mNgUser)
                .addNgModel(mNgPage)
                .addAllPropertyObserver(mAllPropertyObserver)
                .inflateAndBind();
    }

    public void backToLogin(View view){
        //TODO 提示用户是否后退，后退会丢失信息
        routerProvider.back();
    }

    private void switchSchoolAuthentic(){
        mNgPage
                .addParams("tip", "学校认证是为了让社交信息更加真实，但是我们绝对不会泄漏您的隐私信息")
                .addParams("schoolpart", NgGo.NG_VISIBLE.VISIBLE);

        mNgUser.addParams("schoolsnear", queryNearSchools());
    }

    private List<NgModel> queryNearSchools(){
        List<NgModel> schools = new ArrayList<>();
        for(int i = 0; i < 6; i++){
            NgModel school = new NgModel("school");
            school.addParams("logo", "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=802597730,4249290214&fm=58&bpow=1024&bpoh=1024")
                    .addParams("name", "北京大学");
            schools.add(school);
        }
        return schools;
    }
}
