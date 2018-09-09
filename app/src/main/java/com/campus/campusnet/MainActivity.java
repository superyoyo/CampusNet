package com.campus.campusnet;

import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.campus.android_bind.bean.NgGo;
import com.campus.android_bind.bean.NgModel;
import com.campus.campusnet.model.User;
import com.campus.campusnet.ui.adapter.ViewPagerAdapter;
import com.campus.campusnet.ui.page.IndexFragment;
import com.campus.event_filter.request.IRequest;
import com.campus.william.annotationprocessor.annotation.ActivityUrl;
import com.campus.william.router.logic.ActivityProvider;
import com.campus.william.router.logic.RouterFactory;
import com.campus.william.router.logic.RouterProvider;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;
@ActivityUrl(url = "MAIN")
public class MainActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> mFragments;
    private ViewPagerAdapter mAdapter;
    private RouterProvider mRouterProvider;

    private NgGo mNgGo;
    private NgModel mNgModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initNgGo());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            // 激活状态栏
            tintManager.setStatusBarTintEnabled(true);
            // enable navigation bar tint 激活导航栏
            tintManager.setNavigationBarTintEnabled(false);
            //设置系统栏设置颜色
            //tintManager.setTintColor(R.color.red);
            //给状态栏设置颜色
            tintManager.setStatusBarTintResource(R.color.topbar_bkg);
            //Apply the specified drawable or color resource to the system navigation bar.
            //给导航栏设置资源
            tintManager.setNavigationBarTintResource(R.color.topbar_bkg);
        }

        initView();
        initData();
    }

    public void clickHead(View view){
        ActivityProvider provider = RouterFactory.getInstance().obtainAcitivtyProvider();

        Bundle bundle = new Bundle();
        bundle.putString("action", "userInfoPage");

        provider.setState("CONTAINER")
                .setData(bundle)
                .navigate(this);
    }

    private View initNgGo(){
        mNgGo = new NgGo(this, R.layout.activity_main);
        mNgModel = new NgModel("user");
        return mNgGo.addNgModel(mNgModel).addActionContainer(this).inflateAndBind();
    }

    private void initView(){
        mRouterProvider = RouterFactory.getInstance().obtain(android.R.id.content, getSupportFragmentManager());
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.vp_container);
        initAdapter();
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(android.support.design.widget.TabLayout.MODE_SCROLLABLE);
        String[] tags = {"首页", "心动"};
        for(int i = 0, n = tags.length; i< n; i++){
            mTabLayout.getTabAt(i).setText(tags[i]);
        }
    }

    private void initData(){
        User user = App.getInstance().currentUser();
        mNgModel.addParams("head", user.getHead())
                .addParams("name", user.getName())
                .addParams("beloved", "被心动：100次")
                .addParams("visitors", "最近来访：1024次")
                .addParams("msgCount", 100);
    }

    private void initAdapter(){
        mFragments = new ArrayList<>();
        mFragments.add(new IndexFragment());
        mFragments.add(new IndexFragment());
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        if(mRouterProvider.back()){
            super.onBackPressed();
        }
    }

    private void queryUserInfo(){
        IRequest.obtain();
    }
}
