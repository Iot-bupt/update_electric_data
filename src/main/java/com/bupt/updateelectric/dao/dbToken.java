package com.bupt.updateelectric.dao;

/**
 * Created by zyf on 2018/6/13.
 */
public interface dbToken {

    public void insert(String deviceName, String token);
    public boolean delete(String deviceName);
    public boolean update(String deviceName, String token);
    public String get(String deviceName);
}
