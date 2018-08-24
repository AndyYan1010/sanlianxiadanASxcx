package com.bt.andy.sanlianASxcx.messegeInfo;

/**
 * @创建者 AndyYan
 * @创建时间 2018/8/19 9:16
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class WeatherDetailInfo {


    /**
     * date : 19日星期日
     * fx : 东南风
     * low : 低温 27.0℃
     * high : 高温 33.0℃
     * type : 多云
     */

    private String date;
    private String fx;
    private String low;
    private String high;
    private String type;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFx() {
        return fx;
    }

    public void setFx(String fx) {
        this.fx = fx;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
