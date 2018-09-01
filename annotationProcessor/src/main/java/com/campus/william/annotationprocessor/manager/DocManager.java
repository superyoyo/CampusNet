package com.campus.william.annotationprocessor.manager;

import com.campus.william.annotationprocessor.annotation.Logic;
import com.campus.william.annotationprocessor.annotation.ModuleDoc;
import com.campus.william.annotationprocessor.annotation.ParamType;

import java.util.ArrayList;
import java.util.List;

public class DocManager {
    private ModuleDoc mModuleDoc;
    private List<Logic> mLogics;
    private static class InstanceHolder{
        public static DocManager sInstance = new DocManager();
    }

    private DocManager() {
        mLogics = new ArrayList<>();
    }

    public static DocManager getInstance(){
        return InstanceHolder.sInstance;
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

    public void build(){
        if(mModuleDoc == null || mLogics.size() == 0){
            System.out.println("数据解析还没完成");
            return;
        }

        System.out.println("上传文档数据");
        System.out.println("模块名字===>" + mModuleDoc.moduleName());
        System.out.println("模块描述===>" + mModuleDoc.moduleDesc());
        System.out.println("模块版本===>" + mModuleDoc.version());
        System.out.println("模块版本号===>" + mModuleDoc.versionName());
        System.out.println("");

        for(Logic logic : mLogics){
            System.out.println("action===>" + logic.action());
            System.out.println("desc===>" + logic.desc());
            String[] params = logic.params();
            boolean[] canNull = logic.canNull();
            String[] paramsDesc = logic.paramsDesc();
            ParamType[] paramTypes = logic.paramsType();
            for (int i = 0, n = params.length; i < n; i++) {
                System.out.print("参数名：" + params[i] + "     ");
                System.out.print("可为空：" + canNull[i] + "     ");
                System.out.print("参数与类型：" + paramTypes[i] + "     ");
                System.out.print("参数描述：" + paramsDesc[i] + "     ");
                System.out.println("");
            }
        }
    }
}
