package com.campus.william.student.internal.ui.page;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.campus.android_bind.bean.NgGo;
import com.campus.android_bind.bean.NgModel;
import com.campus.android_bind.observer.ViewObserver;
import com.campus.android_bind.observer.ViewObserverFactory;
import com.campus.event_filter.callback.ICallback;
import com.campus.event_filter.request.IRequest;
import com.campus.event_filter.request.MODE;
import com.campus.event_filter.response.IResponse;
import com.campus.william.annotationprocessor.annotation.PageUrl;
import com.campus.william.router.ui.IFragment;
import com.campus.william.student.R;
import com.campus.william.student.internal.ui.widget.album.AlbumView;
import com.campus.william.student.internal.ui.widget.album.AlbumViewbserver;
import com.campus.william.student.internal.ui.widget.tag.TagView;
import com.campus.william.student.internal.ui.widget.tag.TagViewObserver;
import com.campus.william.student.internal.util.StatusBarUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        StatusBarUtils.setImage(getActivity());
        View topBar = mContainer.findViewById(R.id.ll_top);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)topBar.getLayoutParams();
        lp.topMargin = StatusBarUtils.getStatusBarHeight(getActivity());
        topBar.setLayoutParams(lp);
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
        ViewObserverFactory.getInstance().setViewObserverFactory2(new ViewObserverFactory.ViewObserverFactory2() {
            @Override
            public ViewObserver createViewObserver(View view, NgModel ngModel, String perproty) {
                if(view instanceof AlbumView){
                    return new AlbumViewbserver(view, ngModel, perproty);
                }else if(view instanceof TagView){
                    return new TagViewObserver(view, ngModel, perproty);
                }
                return null;
            }
        });
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

                            List<String> ablums = new ArrayList<>();
                            ablums.add("https://wxt.sinaimg.cn/thumb300/a08fb9dfly1fuypfp6lmqj20yi1pcu0x.jpg");
                            ablums.add("https://wxt.sinaimg.cn/thumb300/a08fb9dfly1fuypfy5rf5j20yi1pctvx.jpg");
                            ablums.add("https://wxt.sinaimg.cn/thumb300/a08fb9dfly1fut2dub0l7j20yi1pcap0.jpg");
                            ablums.add("https://wxt.sinaimg.cn/thumb300/a08fb9dfly1fukznqkrwcj20u011iahb.jpg");
                            ablums.add("https://wxt.sinaimg.cn/thumb300/a08fb9dfly1fukyhvjov5j20u00u00u0.jpg");
                            ablums.add("https://wxt.sinaimg.cn/thumb300/a08fb9dfly1fukyhvsnh0j20u011itcv.jpg");
                            ablums.add("https://wx2.sinaimg.cn/mw690/006JuXM5ly1fuqppa9nqwj31hc0u0e82.jpg");

                            List<String> tags = new ArrayList<>();
                            tags.add("文艺");
                            tags.add("旅行");
                            tags.add("读书");
                            tags.add("烘焙");
                            tags.add("花艺");

                            mNgModel.addParams("name",response.getString("name"))
                                    .addParams("sexAndAge", sex + age)
                                    .addParams("sign", response.getString("sign"))
                                    .addParams("cover", "https://wxt.sinaimg.cn/thumb300/a08fb9dfly1fuypfp6lmqj20yi1pcu0x.jpg")
                                    .addParams("pictures", response.getString("pictures"))
                                    .addParams("schoolName", "学校：" + response.getString("schoolName"))
                                    .addParams("majorName", "专业：" + response.getString("majorName"))
                                    .addParams("tags", tags)
                                    .addParams("albums", ablums);
                        }else{
                            Toast.makeText(getActivity(), "查询该用户信息失败", Toast.LENGTH_SHORT).show();
                            routerProvider.back();
                        }
                    }
                });
    }

}
