package com.campus.william.net.model;

public class IFile {
    private int mMode = 0;
    private String mServerName;
    private String mLocalPath;
    private String mUrl;
    private String mDownloadPath;

    private interface LOGIC{
        public static final int UPLOAD = 1;
        public static final int DOWNLOAD = 2;
    }

    private IFile(int mode) {
        mMode = mode;
    }

    private IFile setServerName(String serverName){
        mServerName = serverName;
        return this;
    }

    private IFile setLocalPath(String localPath){
        mLocalPath = localPath;
        return this;
    }

    public IFile setUrl(String url) {
        mUrl = url;
        return this;
    }

    public IFile setDownloadPath(String downloadPath) {
        mDownloadPath = downloadPath;
        return this;
    }

    public String getServerName() {
        return mServerName;
    }

    public String getLocalPath() {
        return mLocalPath;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getDownloadPath() {
        return mDownloadPath;
    }

    public static IFile uploadWithLocalPath(String serverName, String localPath){
        return new IFile(LOGIC.UPLOAD).setServerName(serverName).setLocalPath(localPath);
    }

    public static IFile downloadWithUrl(String url, String downloadPath){
        return new IFile(LOGIC.DOWNLOAD).setUrl(url).setDownloadPath(downloadPath);
    }

    private void save() throws Exception{
        if(mMode == LOGIC.UPLOAD){
            //TODO 上传
        }else{
            //TODO 下载
        }
    }
}
