package com.campus.campusnet.ui.page;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.campus.android_bind.adapter.CommonAdapter;
import com.campus.android_bind.bean.NgGo;
import com.campus.android_bind.bean.NgModel;
import com.campus.campusnet.App;
import com.campus.campusnet.R;
import com.campus.campusnet.model.User;
import com.campus.event_filter.callback.ICallback;
import com.campus.event_filter.request.IRequest;
import com.campus.event_filter.request.MODE;
import com.campus.event_filter.response.IResponse;
import com.campus.william.router.logic.ActivityProvider;
import com.campus.william.router.logic.RouterFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class IndexFragment extends Fragment implements CommonAdapter.CommonAdapterInterface{
    private View mContainer;
    private NgGo mNgGo;
    private NgModel mNgModel;
    private CommonAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initNgGo();
        return mContainer;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
        queryUsers();
    }

    private void initNgGo(){
        mNgGo = new NgGo(getContext(), R.layout.layout_index);
        mNgModel = new NgModel("cardpage");
        mContainer = mNgGo.addNgModel(mNgModel)
                .inflateAndBind();
    }

    private void initAdapter(){
        mAdapter = mNgGo.getRecyclerAdapter(R.id.rv_container);
        mAdapter.setCommonAdapterInterface(this);
    }

    @Override
    public void handleItem(int rvId, CommonAdapter.CommonHolder holder, int position, NgModel item) {
        holder.getView(R.id.iv_head).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "点击了用户头像", Toast.LENGTH_SHORT).show();
                ActivityProvider activityProvider = RouterFactory.getInstance()
                        .obtainAcitivtyProvider();
                Bundle bundle = new Bundle();
                bundle.putString("action", "studentInfoPage");
                bundle.putString("studentId", item.getString("studentId"));
                bundle.putString("userId", item.getString("userId"));
                activityProvider.setState("CONTAINER").setData(bundle).navigate(getActivity());
            }
        });

        holder.getView(R.id.tv_like).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityProvider activityProvider = RouterFactory.getInstance()
                        .obtainAcitivtyProvider();
                Bundle bundle = new Bundle();
                bundle.putString("action", "studentAuthPage");
                bundle.putString("studentId", item.getString("studentId"));
                bundle.putString("userId", item.getString("userId"));
                activityProvider.setState("CONTAINER").setData(bundle).navigate(getActivity());
            }
        });

        holder.getView(R.id.iv_cover).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "点击了封面", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void queryUsers(){
        IRequest.obtain()
                .action("queryStudentRecommend")
                .add("userId", "user")
                .submit(MODE.IO, MODE.UI, new ICallback(){
                    @Override
                    public void done(IResponse response) {
                        super.done(response);
                        if(response.getException() == null){
                            String data = response.getString("data");
                            parseData2NgModel(data);
                        }else{
                            Toast.makeText(getActivity(),
                                    response.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void parseData2NgModel(String studentCardString){
        try {
            JSONArray jsonArray = new JSONArray(studentCardString);
            List<NgModel> list = new ArrayList<>();
            for(int i = 0; i < jsonArray.length(); i ++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                NgModel item = new NgModel("card");
                item.addParams("userId", jsonObject.opt("userId"))
                        .addParams("studentId", jsonObject.opt("studentId"))
                        .addParams("head", jsonObject.optString("head"))
                        .addParams("name", jsonObject.opt("name"))
                        .addParams("schoolName", jsonObject.opt("schoolName"))
                        .addParams("distance", TextUtils.isEmpty(jsonObject.optString("distance"))
                                ? NgGo.NG_VISIBLE.GONE : "距离你"+jsonObject.opt("distance") + "m")
                        .addParams("cover", jsonObject.optString("cover"))
                        .addParams("authed",
                                TextUtils.isEmpty(jsonObject.optString("studentId")) ? "" : "已认证");
                list.add(item);
            }
            mAdapter.setList(list);
        }catch (Exception e){
            Toast.makeText(getActivity(), "解析错误", Toast.LENGTH_SHORT).show();
        }
    }
}
