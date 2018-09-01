package com.campus.william.student.internal.logic;

import com.campus.event_filter.logic.ILogic;
import com.campus.event_filter.request.IRequest;
import com.campus.event_filter.response.IResponse;
import com.campus.william.annotationprocessor.annotation.Logic;
import com.campus.william.annotationprocessor.annotation.ParamType;

@Logic(action = "studentAuthByPic"
        , desc = "学生信息认证"
        , params = {"userId", "authPicture"}
        , paramsDesc = {"用户的ID", "学生的学生证照片"}
        , paramsType = {ParamType.String, ParamType.String}
        , canNull = {false, false})
public class StudentAuthByPicLogic extends ILogic {
    @Override
    public IResponse onRequest(IRequest iRequest) {
        //TODO 用户认证
        try {
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }

        return new IResponse(null, null);
    }
}
