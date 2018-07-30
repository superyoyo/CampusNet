package com.campus.william.net.model;

import java.util.List;

public class IQuery {
    private String mName;

    public IQuery(String objectName) {
        mName = objectName;
    }

    public IQuery whereEqualTo(String key, int value){
        //TODO =
        return this;
    }

    public IQuery whereNotEqualTo(String key, int value){
        //TODO !=
        return this;
    }

    public IQuery whereGreaterThan(String key, int value){
        //TODO >
        return this;
    }

    public IQuery whereGreaterThanOrEqualTo(String key, int value){
        //TODO >=
        return this;
    }

    public IQuery whereLessThan(String key, int value){
        //TODO <
        return this;
    }

    public IQuery whereLessThanOrEqualTo(String key, int value){
        //TODO <=
        return this;
    }

    public IQuery whereEqualTo(String key, String value){
        //TODO key = 'value'
        return this;
    }

    public IQuery whereStartsWith(String key, String value){
        return this;
    }

    public IQuery whereContains(String key, String value){
        return this;
    }

    public IQuery whereMatches(String key, String value){
        //TODO 基于正则表达式的查询
        return this;
    }

    public IQuery whereExists(String key){
        //TODO 查询该字段有值的数据
        return this;
    }

    public IQuery whereNear(String key, double lat, double lng){
        return this;
    }

    public IQuery whereWithinKilometers(String key, double lat, double lng, int kilometers){
        return this;
    }

    public IQuery limit(int limit){
        return this;
    }

    public IQuery orderByAscending(){
        return this;
    }

    public IQuery orderByDescending(){
        return this;
    }

    public List<IObject> find(){
        return null;
    }
}
