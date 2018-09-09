package com.campus.william.student.internal.ui.page;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.campus.android_bind.bean.NgGo;
import com.campus.android_bind.bean.NgModel;
import com.campus.event_filter.callback.ICallback;
import com.campus.event_filter.request.IRequest;
import com.campus.event_filter.request.MODE;
import com.campus.event_filter.response.IResponse;
import com.campus.william.annotationprocessor.annotation.PageUrl;
import com.campus.william.router.ui.IFragment;
import com.campus.william.student.R;

import org.json.JSONObject;

import java.util.HashMap;

@PageUrl(state = "studentInfoPage", desc = "学生的信息页面")
public class StudentInfoFragment extends IFragment{
    private View mContainer;
    private NgGo mNgGo;
    private NgModel mNgModel;
    private String mStudentId;
    private String mUserId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater
            , @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initNgGo();
        return mContainer;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        queryStudentInfo();
    }

    @Override
    public void reInit(HashMap bundle) {
        mStudentId = (String)bundle.get("studentId");
        mUserId = (String) bundle.get("userId");

    }

    public void back(View view){
        if(routerProvider.back()){
            getActivity().finish();
        }
    }

    private void initNgGo(){
        mNgGo = new NgGo(getContext(), R.layout.student_info_page);
        mNgModel = new NgModel("user");
        mContainer = mNgGo.addNgModel(mNgModel)
                .addActionContainer(this)
                .inflateAndBind();

        mContainer.setPadding(0, routerProvider.getStatusBarHeight(), 0, 0);
    }

    private void queryStudentInfo(){
        IRequest.obtain()
                .action("queryStudentInfo")
                .add("studentId", mStudentId)
                .add("userId", mUserId)
                .submit(MODE.IO, MODE.UI, new ICallback(){
                    @Override
                    public void done(IResponse response) {
                        super.done(response);
                        if(response.getException() == null){
                            String sex = response.getString("sex").equals("male") ? "性别：男" : "性别：女";
                            String age = response.getString("age") + "   岁";

                            mNgModel.addParams("name",response.getString("name"))
                                    .addParams("sexAndAge", sex + age)
                                    .addParams("sign", response.getString("sign"))
                                    .addParams("cover", response.getString("cover"))
                                    .addParams("pictures", response.getString("pictures"))
                                    .addParams("schoolName", "学校：" + response.getString("schoolName"))
                                    .addParams("majorName", "专业：" + response.getString("majorName"));
                        }else{
                            Toast.makeText(getActivity(), "查询该用户信息失败", Toast.LENGTH_SHORT).show();
                            routerProvider.back();
                        }
                    }
                });
    }

}
