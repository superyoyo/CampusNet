package com.campus.william.student.internal.logic;

import com.bluelinelabs.logansquare.LoganSquare;
import com.campus.event_filter.logic.ILogic;
import com.campus.event_filter.request.IRequest;
import com.campus.event_filter.response.IResponse;
import com.campus.william.annotationprocessor.annotation.Logic;
import com.campus.william.annotationprocessor.annotation.ParamType;
import com.campus.william.student.internal.model.StudentCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Logic(action = "queryStudentRecommend"
        , desc = "查询该用户推荐的学生"
        , params = {"userId", "studentId"}
        , paramsType = {ParamType.String, ParamType.String}
        , paramsDesc = {"当前用户的Id", "当前用户的studentId，如果此项为空，代表当前用户未认证"}
        , canNull = {false, true})
public class QueryStudentRecommendLogic extends ILogic{
    @Override
    public IResponse onRequest(IRequest iRequest) {
        String userId = iRequest.getString("userId");
        String studentId = iRequest.getString("studentId");

        //TODO 向后端发起请求
        StudentCard sc = new StudentCard();
        sc.setUserId("");
        sc.setStudentId("09211212");
        sc.setSchoolName("清华大学");
        sc.setHead("https://tvax3.sinaimg.cn/crop.584.88.392.392.180/006JuXM5ly8ff1e57jga2j31400qo46k.jpg");
        sc.setDistance("300");
        sc.setName("william.liu");
        sc.setCover("https://wx2.sinaimg.cn/mw690/006JuXM5ly1fuqppa9nqwj31hc0u0e82.jpg");

        List<StudentCard> list = new ArrayList();
        for(int i = 0; i < 20; i++){
            list.add(sc);
        }

        IResponse response = null;
        try{
            HashMap<String, Object> data = new HashMap<>();
            data.put("data", LoganSquare.serialize(list));
            response = new IResponse(data, null);
        }catch (Exception e){
            response = new IResponse(null, e);
        }
        return response;
    }
}
