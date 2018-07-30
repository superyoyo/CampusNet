package com.campus.william.net.model;

public class IUser{
    private IObject mIObject;

    public IUser() {
        mIObject = IObject.obtain("_User");
        ACL acl = new ACL();
        acl.setPublicWriteAccess(false);
        mIObject.setACL(acl);
    }

    public void setPassword(String password){
        mIObject.put("password", password);
    }

    public void setPhoneNumber(String phoneNumber){
        mIObject.put("phoneNumber", phoneNumber);
    }

    public void setEmail(String email){
        mIObject.put("email", email);
    }

    public void signUpByMobilePhone(){

    }

    public void signUpByEmail(){

    }

    public void signUpByWX(){

    }

    public void signUpByQQ(){

    }

    public static IUser signInByMobilePhone(String phone, String password) throws Exception{
        //TODO 用户登录
        return new IUser();
    }

    public static void signInByEmail(String email, String password){

    }

    public static void signInByWX(String wechatId){

    }

    public static void signInByQQ(String qqId){

    }

    public static void signInBySMSCode(String phone, String code){

    }

    public static IUser getCurrentUser(){
        //TODO 从本地文件获取已登录用户
        return new IUser();
    }

    public boolean logOut(){
        //TODO 退出登录
        return true;
    }
}
