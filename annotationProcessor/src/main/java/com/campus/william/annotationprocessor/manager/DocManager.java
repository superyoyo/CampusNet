package com.campus.william.annotationprocessor.manager;

import com.campus.william.annotationprocessor.annotation.ActivityUrl;
import com.campus.william.annotationprocessor.annotation.Logic;
import com.campus.william.annotationprocessor.annotation.ModuleDoc;
import com.campus.william.annotationprocessor.annotation.PageUrl;
import com.campus.william.annotationprocessor.annotation.ParamType;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DocManager {
    private ModuleDoc mModuleDoc;
    private List<Logic> mLogics;
    private List<PageUrl> mPageUrls;
    private List<ActivityUrl> mActivityUrls;

    private boolean mModelDocDone = false;
    private boolean mLogicsDone = false;
    private boolean mPageUrlsDone = false;
    private boolean mAcitviitiesUrlsDone = false;

    private String mDocPath;
    private static class InstanceHolder{
        public static DocManager sInstance = new DocManager();
    }

    private DocManager() {
        mLogics = new ArrayList<>();
        mPageUrls = new ArrayList<>();
        mActivityUrls = new ArrayList<>();
    }

    public static DocManager getInstance(){
        return InstanceHolder.sInstance;
    }

    public void setDocPath(String docPath){
        mDocPath = docPath;
        build();
    }

    public void setModuleDoc(ModuleDoc moduleDoc){
        if(moduleDoc == null){
            System.out.println("模块描述文件不能为空");
            return;
        }

        if(moduleDoc.moduleName() == null || moduleDoc.moduleName().equals("")){
            System.out.println("模块的名字不能为空");
            return;
        }

        if(moduleDoc.moduleDesc() == null || moduleDoc.moduleDesc().equals("")){
            System.out.println("模块的描述不能为空");
            return;
        }

        if(moduleDoc.version() <= 0){
            System.out.println("模块的版本号不能为空");
            return;
        }

        if(moduleDoc.versionName() == null || moduleDoc.versionName().equals("")){
            System.out.println("模块的版本名字不能为空");
            return;
        }

        mModuleDoc = moduleDoc;
    }

    public void moduleDocDone(){
        mModelDocDone = true;
    }

    public void addLogicDoc(Logic logic){
        if(logic == null){
            System.out.println("接口文件不能为空");
            return;
        }
        if(logic.action() == null || logic.action().equals("")){
            System.out.println("接口的Action不能为空");
            return;
        }

        if(logic.desc() == null || logic.desc().equals("")){
            System.out.println("接口的描述不能为空");
            return;
        }

        if(logic.paramsType() == null
                || logic.params() == null || logic.canNull() == null
                || logic.paramsDesc() == null){
            System.out.println("数据格式错误");
            return;
        }
        int paramsCount = logic.params().length;
        if(logic.paramsDesc().length != paramsCount){
            System.out.println("参数描述与参数个数不匹配");
            return;
        }

        if(logic.paramsType().length != paramsCount){
            System.out.println("参数类型与参数个数不匹配");
            return;
        }

        if(logic.canNull().length != paramsCount){
            System.out.println("参数是否为空与参数个数不匹配");
            return;
        }

        mLogics.add(logic);
    }

    public void logicsDone(){
        mLogicsDone = true;
    }

    public void addPageUrl(PageUrl pageUrl){
        if(mPageUrls == null){
            mPageUrls = new ArrayList<>();
        }
        if(pageUrl == null){
            System.out.println("接口文件不能为空");
            return;
        }
        if(pageUrl.state() == null || pageUrl.state().equals("")){
            System.out.println("Page的state不能为空");
            return;
        }
        if(pageUrl.desc() == null || pageUrl.desc().equals("")){
            System.out.println("Page的desc不能为空");
            return;
        }
        mPageUrls.add(pageUrl);
        System.out.println("添加了一个PageUrl:" + pageUrl.state() + " " + pageUrl.desc());
    }

    public void pageUrlsDone(){
        mPageUrlsDone = true;
    }

    public void addActivityUrl(ActivityUrl activityUrl){
        if(mActivityUrls == null){
            mActivityUrls = new ArrayList<>();
        }
        if(activityUrl == null){
            System.out.println("接口文件不能为空");
            return;
        }
        if(activityUrl.url() == null || activityUrl.url().equals("")){
            System.out.println("Activity的url不能为空");
            return;
        }
        mActivityUrls.add(activityUrl);
    }

    public void activityUrlsDone(){
        mAcitviitiesUrlsDone = true;
    }

    public void build(){
        if(!mModelDocDone || !mLogicsDone || !mPageUrlsDone
                || !mAcitviitiesUrlsDone || mDocPath == null || mDocPath.equals("")){
            System.out.println("解析未完成");
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("模块名字===>" + mModuleDoc.moduleName() + "\n");
        stringBuilder.append("模块描述===>" + mModuleDoc.moduleDesc() + "\n");
        stringBuilder.append("模块版本===>" + mModuleDoc.version() + "\n");
        stringBuilder.append("模块版本号===>" + mModuleDoc.versionName() + "\n");
        stringBuilder.append("===================接口列表=============================" + "\n");

        for(int i = 0, n = mLogics.size(); i < n; i++){
            Logic logic = mLogics.get(i);
            stringBuilder.append((i + 1) + ":" + logic.desc() + "\n");
            stringBuilder.append("  " + "action===>" + logic.action() + "\n");
            String[] params = logic.params();
            boolean[] canNull = logic.canNull();
            String[] paramsDesc = logic.paramsDesc();
            ParamType[] paramTypes = logic.paramsType();
            for (int j = 0, m = params.length; j < m; j++) {
                stringBuilder.append("  " + "参数名：" + params[j] + "  " + "可为空：" + canNull[j]
                        +"  " + "参数与类型：" + paramTypes[j] + "  " + "参数描述：" + paramsDesc[j] + "\n");
                stringBuilder.append("\n");
                stringBuilder.append("\n");
            }
        }

        if(mPageUrls.size() == 0){
            stringBuilder.append("================================================" + "\n");
        }else{
            stringBuilder.append("===================路由列表=============================" + "\n");
        }


        for(int i = 0, n = mPageUrls.size(); i < n; i++){
            PageUrl pageUrl = mPageUrls.get(i);
            stringBuilder.append((i + 1) + ":" + pageUrl.desc() + "\n");
            stringBuilder.append("  " + "state===>" + pageUrl.state() + "\n");
        }

        stringBuilder.append("================================================" + "\n");

        try {
            File file = new File(mDocPath + "/" + mModuleDoc.moduleName()+ "_" + mModuleDoc.versionName()+".txt");
            if(!file.exists()){
                file.createNewFile();
            }
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
            outputStream.write(stringBuilder.toString().getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
