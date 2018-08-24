package com.bt.andy.sanlianASxcx.messegeInfo;

/**
 * @创建者 AndyYan
 * @创建时间 2018/5/25 16:08
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class SubtableInfo {

    /**
     * ckName : 9527
     * startPlace : xxx
     * endPlace : xxx
     * time : 2018-10-01
     * waite : 15分钟
     * addPrice : 15.0
     * isReceipt : 0
     */

    private String ckName;//乘客名称
    private String startPlace;//起点
    private String endPlace;//终点
    private String time;//出发时间
    private String waite;//可等待时间
    private double addPrice;//加价
    private int    isReceipt;//是否被接单;0未接，1已接
    /**
     * orderId : 12345678
     */

    private String orderId;//订单号

    public String getCkName() {
        return ckName;
    }

    public void setCkName(String ckName) {
        this.ckName = ckName;
    }

    public String getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    public String getEndPlace() {
        return endPlace;
    }

    public void setEndPlace(String endPlace) {
        this.endPlace = endPlace;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWaite() {
        return waite;
    }

    public void setWaite(String waite) {
        this.waite = waite;
    }

    public double getAddPrice() {
        return addPrice;
    }

    public void setAddPrice(double addPrice) {
        this.addPrice = addPrice;
    }

    public int getIsReceipt() {
        return isReceipt;
    }

    public void setIsReceipt(int isReceipt) {
        this.isReceipt = isReceipt;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
