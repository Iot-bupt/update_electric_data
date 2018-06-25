package com.bupt.updateelectric.dao;


/**
 * Created by zyf on 2018/6/13.
 */
public class dbTokenImpl implements dbToken {



    //添加数据
    public void insert(String deviceName,String token){
        DAO.insert(deviceName, token);
    }

    //删除数据
    public boolean delete(String deviceName){
        DAO.delete(deviceName);
        return true;
    }

    //更新数据
    public boolean update(String deviceName,String token) {
        DAO.update(deviceName,token);
        return true;
    }

    //查询数据
    public String get(String deviceName) {
        String rs = DAO.getAll(deviceName);
        return rs;
    }

}
