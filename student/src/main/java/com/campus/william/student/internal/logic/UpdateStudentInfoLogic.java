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

@Logic(action = "updateStudentInfo"
        , desc = "更新当前用户的学生信息"
        , params = {"userId", "account", "schoolId", "schoolName", "majorId", "majorName"}
        , paramsDesc = {"当前用户的ID", "学号", "学校的ID", "学校的名字", "专业的ID", "专业的名字"}
        , paramsType = {ParamType.String, ParamType.String, ParamType.String, ParamType.String, ParamType.String, ParamType.String}
        , canNull = {false, false, false, false, false, false})
public class UpdateStudentInfoLogic extends ILogic{
    private final String STUDENT_ID = "student_ID";
    @Override
    public IResponse onRequest(IRequest iRequest) {

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
