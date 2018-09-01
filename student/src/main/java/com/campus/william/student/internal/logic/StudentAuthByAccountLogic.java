package com.campus.william.student.internal.logic;

import com.campus.event_filter.logic.ILogic;
import com.campus.event_filter.request.IRequest;
import com.campus.event_filter.response.IResponse;
import com.campus.william.annotationprocessor.annotation.Logic;
import com.campus.william.annotationprocessor.annotation.ParamType;
import com.campus.william.student.internal.model.Student;
import com.campus.william.student.internal.storage.Storage;
import com.campus.william.student.internal.storage.StorageFactory;

import java.io.IOException;
import java.util.HashMap;

@Logic(action = "studentAuthByAccount"
        , desc = "学生信息认证:学号密码"
        , params = {"userId", "account", "password", "schoolId"}
        , paramsDesc = {"用户的ID", "学生的学号", "校园卡的密码", "学生所在的学校ID"}
        , paramsType = {ParamType.String, ParamType.String, ParamType.String, ParamType.String}
        , canNull = {false, false, false, false})
public class StudentAuthByAccountLogic extends ILogic {
    private final String STUDENT_ID = "student_ID";
    @Override
    public IResponse onRequest(IRequest iRequest) {
        //TODO 用户认证
        try {
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }
        String userId = iRequest.getString("userId");
        String account = iRequest.getString("account");
        String schoolId = iRequest.getString("schoolId");
        String schoolName = iRequest.getString("schoolName");
        String majorId = iRequest.getString("majorId");
        String majorName = iRequest.getString("majorName");

        Student student = new Student();
        student.setUserId(userId);
        student.setAccount(account);
        student.setSchoolId(schoolId);
        student.setSchoolName(schoolName);
        student.setMajorId(majorId);
        student.setMajorName(majorName);

        Storage storage = StorageFactory.getInstance().obtainStorage(StorageFactory.Stragegy.XML);
        try {
            storage.insert(STUDENT_ID, student);
            HashMap data = new HashMap();
            data.put("student", student);
            return new IResponse(data, null);
        }catch (IOException e){
            return new IResponse(null, e);
        }
    }
}
