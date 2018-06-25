package com.bupt.updateelectric.entity;

import lombok.Data;

@Data
public class DeviceData {

    private String deviceName;
    private String dataKey;
    private String cycle;
    private Long ts;
    private Double maxData;
    private Double normalData;
    private Double minData;

    public DeviceData(String deviceName, String dataKey, String cycle, Long ts, Double maxData, Double normalData, Double minData)
    {
        this.deviceName = deviceName;
        this.dataKey = dataKey;
        this.cycle = cycle;
        this.ts = ts;
        this.maxData = maxData;
        this.normalData = normalData;
        this.minData = minData;
    }
}
