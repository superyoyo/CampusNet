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

@Logic(action = "currentStudentInfo"
        , desc = "查询当前用户的学生信息"
        , params = {"userId"}
        , paramsDesc = {"当前用户的ID"}
        , paramsType = {ParamType.String}
        , canNull = {false})
public class QueryCurrentStudentInfoLogic extends ILogic{
    @Override
    public IResponse onRequest(IRequest iRequest) {
        //TODO 创建Student假数据
        Storage storage = StorageFactory.getInstance().obtainStorage(StorageFactory.Stragegy.XML);
        Student student = null;
        try {
            student = storage.query(Student.class, StorageConstant.STUDENT_ID);
            HashMap data = new HashMap();
            data.put("userId", student.getUserId());
            data.put("account", student.getAccount());
            data.put("schoolId", student.getSchoolId());
            data.put("schoolName", student.getSchoolName());
            data.put("majorId", student.getMajorId());
            data.put("majorName", student.getMajorName());

            return new IResponse(data, null);
        } catch (IOException e) {
            e.printStackTrace();
            return new IResponse(null, e);
        }
    }
}
