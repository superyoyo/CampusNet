package com.campus.campusnet.ui.page;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.campus.android_bind.adapter.CommonAdapter;
import com.campus.android_bind.bean.NgGo;
import com.campus.android_bind.bean.NgModel;
import com.campus.campusnet.R;

public class LikedFragment extends Fragment implements CommonAdapter.CommonAdapterInterface{
    private NgGo mNgGo;
    private NgModel mNgModel;
    private View mContainer;
    private CommonAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        initNgGo();
        return mContainer;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
        queryData();
    }

    private void initNgGo(){
        mNgGo = new NgGo(getContext(), R.layout.layout_conversations);
        mNgModel = new NgModel("liked");
        mNgGo.addNgModel(mNgModel);
        mContainer = mNgGo.inflateAndBind();
    }

    private void initAdapter(){
        mAdapter = mNgGo.getRecyclerAdapter(R.id.rv_container);
        mAdapter.setCommonAdapterInterface(this);
    }

    @Override
    public void handleItem(int rvId, CommonAdapter.CommonHolder holder, int position, NgModel item) {

    }

    private void queryData(){

    }
}
