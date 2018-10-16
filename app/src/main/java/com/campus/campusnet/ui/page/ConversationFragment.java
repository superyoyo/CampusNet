package com.campus.campusnet.ui.page;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.campus.android_bind.adapter.CommonAdapter;
import com.campus.android_bind.bean.NgGo;
import com.campus.android_bind.bean.NgModel;
import com.campus.android_bind.util.ImageLoader;
import com.campus.campusnet.App;
import com.campus.campusnet.R;
import com.campus.campusnet.ui.util.TimeUtil;
import com.campus.event_filter.callback.ICallback;
import com.campus.event_filter.request.IRequest;
import com.campus.event_filter.request.MODE;
import com.campus.event_filter.response.IResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConversationFragment extends Fragment implements CommonAdapter.CommonAdapterInterface{
    private View mContainer;
    private NgGo mNgGo;
    private NgModel mNgModel;
    private CommonAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initNgGo();
        return mContainer;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
        initData();
    }

    private void initNgGo(){
        mNgGo = new NgGo(getContext(), R.layout.layout_conversations);
        mNgModel = new NgModel("conversations");
        mContainer = mNgGo.addNgModel(mNgModel)
                .inflateAndBind();
    }

    private void initAdapter(){
        mAdapter = mNgGo.getRecyclerAdapter(R.id.rv_container);
        mAdapter.setCommonAdapterInterface(this::handleItem);
    }

    private void initData(){
        IRequest.obtain()
                .action("queryConversations")
                .add("userId", App.getInstance().currentUser().getUserId())
                .submit(MODE.IO, MODE.UI, new ICallback(){
            @Override
            public void done(IResponse response) {
                    super.done(response);
                    parseData(response.getString("data"));
                    }
                });
    }

    @Override
    public void handleItem(int rvId, CommonAdapter.CommonHolder holder, int position, final NgModel item) {
        holder.getItemView().setOnClickListener(v -> {
            //TODO 点击进入聊天页面
        });

        final ImageView head = (ImageView) holder.getView(R.id.iv_head);
        head.setTag(item.getString("conversationId"));
        IRequest.obtain()
                .action("queryUserHead")
                .add("userId", item.getString("conversationId"))
                .submit(MODE.IO, MODE.UI, new ICallback(){
                    @Override
                    public void done(IResponse response) {
                        super.done(response);
                        if(head.getTag().equals(item.getString("conversationId"))){
                            ImageLoader.getInstance().loadImage(head, response.getString("data"));
                        }
                    }
                });
    }

    private void parseData(String data){
        try {
            JSONArray arr = new JSONArray(data);
            List<NgModel> list = new ArrayList<>();
            for(int i = 0, n = arr.length(); i < n; i++){
                JSONObject obj = arr.getJSONObject(i);
                NgModel con = new NgModel("conversation");
                con.addParams("conversationId", obj.getString("conversationId"))
                        .addParams("title", obj.getString("title"))
                        .addParams("lastTime", TimeUtil.getTimeStr(getContext(), obj.getLong("lastTime")))
                        .addParams("unReadNum", obj.getInt("unReadNum") > 0
                                ? obj.getInt("unReadNum") : NgGo.NG_VISIBLE.INVISIBLE)
                        .addParams("lastMessage", obj.getString("lastMessage"))
                        .addParams("notification", obj.getBoolean("notification")
                                ? NgGo.NG_VISIBLE.VISIBLE : NgGo.NG_VISIBLE.GONE)
                        .addParams("top", obj.getBoolean("top") ? "置顶" : NgGo.NG_VISIBLE.INVISIBLE)
                        .addParams("data", obj);
                list.add(con);
            }

            mAdapter.setList(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
