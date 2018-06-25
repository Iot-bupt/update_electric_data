package com.bupt.updateelectric.process;

import com.bupt.updateelectric.entity.DeviceData;
import com.bupt.updateelectric.mqtt.DataMqttClient;
import com.google.gson.JsonObject;
import org.json.simple.JSONObject;

public class Publish {

    private DataMqttClient dataMqttClient;


    /*
   作为customerId的属性发送
    */
/*    public void postCustomerInfo(CustomerInfo customerInfo, String token) {
        // TODO Auto-generated method stub
        try{
            Gson gson = new Gson();
            String deviceDataStr = gson.toJson(customerInfo);
            //进行发送
            dataMqttClient.publishAttribute(token,deviceDataStr);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }*/

    /*
    作为customerId的血压数值发送
     */
    public void postElectricData(DeviceData deviceData, String token){
        try {
            JSONObject tsAndValue = new JSONObject();
            JsonObject data = new JsonObject();

            if(deviceData.getMaxData()!= null){
                data.addProperty("最大"+deviceData.getDataKey(),deviceData.getMaxData());
            }

            data.addProperty(deviceData.getDataKey(),deviceData.getNormalData());

            if(deviceData.getMinData()!= null){
                data.addProperty("最小"+deviceData.getDataKey(),deviceData.getMinData());
            }

            tsAndValue.put("ts", deviceData.getTs());
            tsAndValue.put("values",data);
            String sendData = tsAndValue.toString();
            dataMqttClient.publishData(token,sendData);
        } catch (Exception e){
            e.printStackTrace();
        }

    }


}
