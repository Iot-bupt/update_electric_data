package com.bupt.updateelectric.process;

import com.bupt.updateelectric.dao.dbTokenImpl;
import com.bupt.updateelectric.entity.DeviceData;
import com.bupt.updateelectric.http.HttpControl;

public class GetToken {
    public HttpControl hc = new HttpControl();
    public dbTokenImpl db = new dbTokenImpl();


    public String getToken(DeviceData deviceData){
        String token = null;
        String deviceName = deviceData.getDeviceName();
        if(db.get(deviceName) == null){ //数据库中customerId判断
            //SQLite里没有token
            try{
                hc.httplogin();
                String id = hc.httpcreate(deviceName);
                token = hc.httpfind(id);
            }catch (Exception e){
                e.printStackTrace();
            }//存入DB
            db.insert(deviceName,token);
            return token;
        }else{
            //SQLite里有token，从表中拿token
            token = db.get(deviceName);
            return token;
        }
    }
}
