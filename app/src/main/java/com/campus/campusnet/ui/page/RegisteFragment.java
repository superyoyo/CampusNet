package com.campus.campusnet.ui.page;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.campus.android_bind.adapter.CommonAdapter;
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
    private CommonAdapter mCommonAdapter;
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
        initAdapter();
    }

    private void initAdapter(){
        mCommonAdapter = mNgGo.getRecyclerAdapter(R.id.rv_nearschools);
        mCommonAdapter.setCommonAdapterInterface(new CommonAdapter.CommonAdapterInterface() {
            @Override
            public void handleItem(int rvId, CommonAdapter.CommonHolder holder, int position, final NgModel item) {
                holder.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO 用户点击了学校
                        Toast.makeText(getActivity(), item.getValue("name").toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });
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
        //TODO 通过Student模块获取学校列表

        List<NgModel> schools = new ArrayList<>();
        for(int i = 0; i < 6; i++){
            NgModel school = new NgModel("school");
            school.addParams("logo", "http://t2.hddhhn.com/uploads/tu/201610/198/hkgip2b102z.jpg")
                    .addParams("name", "北京大学");
            schools.add(school);
        }
        return schools;
    }
}
