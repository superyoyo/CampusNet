package com.campus.william.user.internal.ui.page;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.campus.android_bind.bean.NgGo;
import com.campus.android_bind.bean.NgModel;
import com.campus.android_bind.observer.AllPropertyObserver;
import com.campus.william.annotationprocessor.annotation.PageUrl;
import com.campus.william.router.ui.IFragment;
import com.campus.william.user.R;

import java.util.HashMap;

/**
 * Created by wenge on 2018/9/2.
 */
@PageUrl(state = "EditUserInfo", desc = "用户信息编辑页")
public class InfoEditFragment extends IFragment {

    private View mContainer;
    private NgModel mNgModel;
    private NgGo mNaGo;

    private AllPropertyObserver mPropertyObserver = new AllPropertyObserver() {
        @Override
        public void dataChange(String tag, Object object) {
            super.dataChange(tag, object);

        }
    };

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
        mNgModel = new NgModel("infoEdit");
        mNaGo = new NgGo(getActivity(), R.layout.layout_info_edit)
                .addNgModel(mNgModel)
                .addAllPropertyObserver(mPropertyObserver)
                .addActionContainer(this);
        mContainer = mNaGo.inflateAndBind();
    }

    /**
     * 点击返回，并保存或者是上传相关的数据
     **/
    public void back(View view) {

    }

    public void addPhoto(View view) {
        final ImageView imageView = (ImageView) view;
        switch (imageView.getId()) {
//            case R.id.user_infoEdit_background_wall_image_1:
//                break;
//            case R.id.user_infoEdit_background_wall_image_2:
//                break;
//            case R.id.user_infoEdit_background_wall_image_3:
//                break;
//            case R.id.user_infoEdit_background_wall_image_4:
//                break;
//            case R.id.user_infoEdit_background_wall_image_5:
//                break;
//            case R.id.user_infoEdit_background_wall_image_6:
//                break;
        }

    }
}
