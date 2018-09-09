package com.campus.android_bind.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.campus.android_bind.bean.NgGo;
import com.campus.android_bind.bean.NgModel;
import com.campus.android_bind.view.NgItemView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jacklee on 17/1/12.
 */

public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.CommonHolder> {
    private List<NgModel> mList;
    private List<NgItemView> mViews;
    private LayoutInflater mInflater;
    private CommonAdapterInterface commonAdapterInterface;
    private CommonAdapterOverrideInterface commonAdapterOverrideInterface;
    private int mRvId = View.NO_ID;//当前RecyclerView的ID
    private HashMap<String, Integer> mViewTypes;

    public CommonAdapter(int rvId, Context context) {
        mViewTypes = new HashMap<>();
        mRvId = rvId;
        mList = new ArrayList<>();
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setViews(List<NgItemView> views) {
        mViews = views;
    }

    public void setCommonAdapterInterface(CommonAdapterInterface commonAdapterInterface) {
        this.commonAdapterInterface = commonAdapterInterface;
        notifyDataSetChanged();
    }

    public void setCommonAdapterOverrideInterface(CommonAdapterOverrideInterface commonAdapterOverrideInterface) {
        this.commonAdapterOverrideInterface = commonAdapterOverrideInterface;
    }

    public List<NgModel> getList() {
        return Collections.unmodifiableList(mList);
    }

    public void setList(List<NgModel> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public CommonAdapter.CommonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int laytoutId = mViews.get(viewType).getLaytoutId();
        View view = mInflater.inflate(laytoutId, parent, false);
        return new CommonHolder(view);
    }

    @Override
    public void onBindViewHolder(CommonAdapter.CommonHolder holder, int position) {
        NgModel ngModel = mList.get(position);
        holder.setData(ngModel);
        if(commonAdapterInterface != null){
            commonAdapterInterface.handleItem(mRvId, holder, position, ngModel);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        //1.获取该位置的NgModel
        NgModel ngModel = mList.get(position);
        //2.获取这个ngModel的tag
        String tag = ngModel.getTag();
        if(mViewTypes.get(tag) != null){
            return mViewTypes.get(tag);
        }else{
            for(int i = 0, n = mViews.size();i < n; i ++){
                if(mViews.get(i).getModel().equals(tag)){
                    mViewTypes.put(tag, i);
                    return i;
                }
            }
        }
        return 0;
    }

    public class CommonHolder extends RecyclerView.ViewHolder{
        private SparseArray<View> mViews;
        private NgGo mNgGo;
        private NgModel mNgModel;

        public CommonHolder(View itemView) {
            super(itemView);
            mViews = new SparseArray<View>();
            mNgGo = new NgGo(itemView);
        }

        public void setData(NgModel ngModel){
            if(mNgModel != null && mNgModel.equals(ngModel)){
                return;
            }
            ngModel.removeAll();
            mNgModel = ngModel;
            mNgGo.addNgModel(mNgModel).bind();
        }

        public View getView(int viewId){
            View v = mViews.get(viewId);
            if(v == null){
                v = itemView.findViewById(viewId);
                mViews.put(viewId, v);
            }
            return v;
        }

        public View getItemView(){
            return itemView;
        }

        public void setText(int viewId, String text){
            View view = getView(viewId);
            if(view instanceof TextView){
                ((TextView) view).setText(text);
            }
        }
    }

    @Override
    public void onViewDetachedFromWindow(CommonHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (commonAdapterOverrideInterface != null) {
            commonAdapterOverrideInterface.onViewDetachedFromWindow(holder);
        }

    }

    @Override
    public void onViewAttachedToWindow(CommonHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (commonAdapterOverrideInterface != null) {
            commonAdapterOverrideInterface.onViewAttachedToWindow(holder);
        }
    }

    @Override
    public void onViewRecycled(CommonHolder holder) {
        super.onViewRecycled(holder);
        if (commonAdapterOverrideInterface != null) {
            commonAdapterOverrideInterface.onViewRecycled(holder);
        }
    }

    public interface CommonAdapterInterface{
        public void handleItem(int rvId, CommonHolder holder, int position, NgModel item);
    }

    public interface CommonAdapterOverrideInterface{
        public void onViewDetachedFromWindow(CommonHolder holder);
        public void onViewRecycled(CommonHolder holder);
        public void onViewAttachedToWindow(CommonHolder holder);
    }
}
