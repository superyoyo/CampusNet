package com.campus.william.student.internal.logic;

import com.campus.event_filter.logic.ILogic;
import com.campus.event_filter.request.IRequest;
import com.campus.event_filter.response.IResponse;
import com.campus.william.annotationprocessor.annotation.Logic;
import com.campus.william.annotationprocessor.annotation.ParamType;
import com.campus.william.student.internal.model.StorageConstant;
import com.campus.william.student.internal.model.Student;
import com.campus.william.student.internal.storage.Storage;
import com.campus.william.student.internal.storage.StorageFactory;

import java.io.IOException;
import java.util.HashMap;

@Logic(action = "queryStudentInfo"
        , desc = "查询当前用户的学生信息"
        , params = {"studentId", "userId"}
        , paramsDesc = {"要查询用户的学生Id", "要查询用户的UserId"}
        , paramsType = {ParamType.String, ParamType.String}
        , canNull = {false, false})
public class QueryStudentInfoLogic extends ILogic{
    @Override
    public IResponse onRequest(IRequest iRequest) {
        //TODO 创建Student假数据

        HashMap data = new HashMap();
        data.put("userId", iRequest.getString("userId"));
        data.put("account", iRequest.getString("studentId"));
        data.put("schoolId", "");
        data.put("schoolName", "清华大学");
        data.put("majorId", "");
        data.put("majorName", "计算机科学与工程学院");
        data.put("head", "");
        data.put("name", "Cute Cat");
        data.put("sex", "female");
        data.put("age", "20");
        data.put("sign", "我淋过最大的雨，就是你烈日下的不回头");
        data.put("cover", "https://wx2.sinaimg.cn/mw690/006JuXM5ly1fuqppa9nqwj31hc0u0e82.jpg");
        data.put("pictures", "我淋过最大的雨，就是你烈日下的不回头");

        return new IResponse(data, null);
    }
}
