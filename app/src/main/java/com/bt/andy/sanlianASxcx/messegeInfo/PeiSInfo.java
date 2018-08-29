package com.bt.andy.sanlianASxcx.messegeInfo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2018/8/28 9:35
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class PeiSInfo {

    /**
     * result : 1
     * fopenid : 389234e1369e41dc95765162133d0845
     * apply : [{"id":"40288afd657925860165794d16a30016","fpeople":"测试配送3","ftel":"3","faddress":"3","fname":null,"fsale_person":null,"foperate_task":null,"fmoney":null,"fmoney_kind":null,"fbstatus":"2","fget_time":null,"ewm":null,"fgodate":null,"fshifu":null,"fcontent":null,"special_note":"","forderno":"","fshifuid":null,"fdate":"2018-08-27 10:53:29","azfdate":"","psstatus":"0","pinming":null,"qty":null,"ordertype":null,"ftype":null,"ftype1":null}]
     * psstatus : 0
     */

    private int                   result;
    private String                fopenid;
    private String                psstatus;
    private List<ApplyBean>       apply;
    private List<OrderazlistBean> orderazlist;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getFopenid() {
        return fopenid;
    }

    public void setFopenid(String fopenid) {
        this.fopenid = fopenid;
    }

    public String getPsstatus() {
        return psstatus;
    }

    public void setPsstatus(String psstatus) {
        this.psstatus = psstatus;
    }

    public List<ApplyBean> getApply() {
        return apply;
    }

    public void setApply(List<ApplyBean> apply) {
        this.apply = apply;
    }

    public List<OrderazlistBean> getOrderazlist() {
        return orderazlist;
    }

    public void setOrderazlist(List<OrderazlistBean> orderazlist) {
        this.orderazlist = orderazlist;
    }

    public static class ApplyBean {
        /**
         * id : 40288afd657925860165794d16a30016
         * fpeople : 测试配送3
         * ftel : 3
         * faddress : 3
         * fname : null
         * fsale_person : null
         * foperate_task : null
         * fmoney : null
         * fmoney_kind : null
         * fbstatus : 2
         * fget_time : null
         * ewm : null
         * fgodate : null
         * fshifu : null
         * fcontent : null
         * special_note :
         * forderno :
         * fshifuid : null
         * fdate : 2018-08-27 10:53:29
         * azfdate :
         * psstatus : 0
         * pinming : null
         * qty : null
         * ordertype : null
         * ftype : null
         * ftype1 : null
         */

        private String id;
        private String fpeople;
        private String ftel;
        private String faddress;
        private Object fname;
        private Object fsale_person;
        private Object foperate_task;
        private Object fmoney;
        private Object fmoney_kind;
        private String fbstatus;
        private Object fget_time;
        private Object ewm;
        private Object fgodate;
        private Object fshifu;
        private Object fcontent;
        private String special_note;
        private String forderno;
        private Object fshifuid;
        private String fdate;
        private String azfdate;
        private String psstatus;
        private Object pinming;
        private Object qty;
        private Object ordertype;
        private Object ftype;
        private Object ftype1;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFpeople() {
            return fpeople;
        }

        public void setFpeople(String fpeople) {
            this.fpeople = fpeople;
        }

        public String getFtel() {
            return ftel;
        }

        public void setFtel(String ftel) {
            this.ftel = ftel;
        }

        public String getFaddress() {
            return faddress;
        }

        public void setFaddress(String faddress) {
            this.faddress = faddress;
        }

        public Object getFname() {
            return fname;
        }

        public void setFname(Object fname) {
            this.fname = fname;
        }

        public Object getFsale_person() {
            return fsale_person;
        }

        public void setFsale_person(Object fsale_person) {
            this.fsale_person = fsale_person;
        }

        public Object getFoperate_task() {
            return foperate_task;
        }

        public void setFoperate_task(Object foperate_task) {
            this.foperate_task = foperate_task;
        }

        public Object getFmoney() {
            return fmoney;
        }

        public void setFmoney(Object fmoney) {
            this.fmoney = fmoney;
        }

        public Object getFmoney_kind() {
            return fmoney_kind;
        }

        public void setFmoney_kind(Object fmoney_kind) {
            this.fmoney_kind = fmoney_kind;
        }

        public String getFbstatus() {
            return fbstatus;
        }

        public void setFbstatus(String fbstatus) {
            this.fbstatus = fbstatus;
        }

        public Object getFget_time() {
            return fget_time;
        }

        public void setFget_time(Object fget_time) {
            this.fget_time = fget_time;
        }

        public Object getEwm() {
            return ewm;
        }

        public void setEwm(Object ewm) {
            this.ewm = ewm;
        }

        public Object getFgodate() {
            return fgodate;
        }

        public void setFgodate(Object fgodate) {
            this.fgodate = fgodate;
        }

        public Object getFshifu() {
            return fshifu;
        }

        public void setFshifu(Object fshifu) {
            this.fshifu = fshifu;
        }

        public Object getFcontent() {
            return fcontent;
        }

        public void setFcontent(Object fcontent) {
            this.fcontent = fcontent;
        }

        public String getSpecial_note() {
            return special_note;
        }

        public void setSpecial_note(String special_note) {
            this.special_note = special_note;
        }

        public String getForderno() {
            return forderno;
        }

        public void setForderno(String forderno) {
            this.forderno = forderno;
        }

        public Object getFshifuid() {
            return fshifuid;
        }

        public void setFshifuid(Object fshifuid) {
            this.fshifuid = fshifuid;
        }

        public String getFdate() {
            return fdate;
        }

        public void setFdate(String fdate) {
            this.fdate = fdate;
        }

        public String getAzfdate() {
            return azfdate;
        }

        public void setAzfdate(String azfdate) {
            this.azfdate = azfdate;
        }

        public String getPsstatus() {
            return psstatus;
        }

        public void setPsstatus(String psstatus) {
            this.psstatus = psstatus;
        }

        public Object getPinming() {
            return pinming;
        }

        public void setPinming(Object pinming) {
            this.pinming = pinming;
        }

        public Object getQty() {
            return qty;
        }

        public void setQty(Object qty) {
            this.qty = qty;
        }

        public Object getOrdertype() {
            return ordertype;
        }

        public void setOrdertype(Object ordertype) {
            this.ordertype = ordertype;
        }

        public Object getFtype() {
            return ftype;
        }

        public void setFtype(Object ftype) {
            this.ftype = ftype;
        }

        public Object getFtype1() {
            return ftype1;
        }

        public void setFtype1(Object ftype1) {
            this.ftype1 = ftype1;
        }
    }

    public static class OrderazlistBean {
        /**
         * note :
         * orderno : OR18-08-2900226
         * purchase : 4
         * fname1 : 4
         * psstatus : 1
         * FPrice : 4
         * azfdate : 2018-08-29 08:44:53.0000000
         * FAmount : 4
         * fmobile : 18234567890
         * FDeliveryAddress : 4
         * FQty : 4
         * ftype :
         * fcontact : 测试安装4
         * ftype1 : 1
         * bpmstatus : q
         * id : 40288afd6583198801658323f8320002
         * chargetype : 0
         */

        private String note;
        private String orderno;
        private String purchase;
        private String fname1;
        @SerializedName("psstatus")
        private String psstatusX;
        private double FPrice;
        private String azfdate;
        private double FAmount;
        private String fmobile;
        private String FDeliveryAddress;
        private double FQty;
        private String ftype;
        private String fcontact;
        private String ftype1;
        private String bpmstatus;
        private String id;
        private String chargetype;

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getOrderno() {
            return orderno;
        }

        public void setOrderno(String orderno) {
            this.orderno = orderno;
        }

        public String getPurchase() {
            return purchase;
        }

        public void setPurchase(String purchase) {
            this.purchase = purchase;
        }

        public String getFname1() {
            return fname1;
        }

        public void setFname1(String fname1) {
            this.fname1 = fname1;
        }

        public String getPsstatusX() {
            return psstatusX;
        }

        public void setPsstatusX(String psstatusX) {
            this.psstatusX = psstatusX;
        }

        public double getFPrice() {
            return FPrice;
        }

        public void setFPrice(double FPrice) {
            this.FPrice = FPrice;
        }

        public String getAzfdate() {
            return azfdate;
        }

        public void setAzfdate(String azfdate) {
            this.azfdate = azfdate;
        }

        public double getFAmount() {
            return FAmount;
        }

        public void setFAmount(double FAmount) {
            this.FAmount = FAmount;
        }

        public String getFmobile() {
            return fmobile;
        }

        public void setFmobile(String fmobile) {
            this.fmobile = fmobile;
        }

        public String getFDeliveryAddress() {
            return FDeliveryAddress;
        }

        public void setFDeliveryAddress(String FDeliveryAddress) {
            this.FDeliveryAddress = FDeliveryAddress;
        }

        public double getFQty() {
            return FQty;
        }

        public void setFQty(double FQty) {
            this.FQty = FQty;
        }

        public String getFtype() {
            return ftype;
        }

        public void setFtype(String ftype) {
            this.ftype = ftype;
        }

        public String getFcontact() {
            return fcontact;
        }

        public void setFcontact(String fcontact) {
            this.fcontact = fcontact;
        }

        public String getFtype1() {
            return ftype1;
        }

        public void setFtype1(String ftype1) {
            this.ftype1 = ftype1;
        }

        public String getBpmstatus() {
            return bpmstatus;
        }

        public void setBpmstatus(String bpmstatus) {
            this.bpmstatus = bpmstatus;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getChargetype() {
            return chargetype;
        }

        public void setChargetype(String chargetype) {
            this.chargetype = chargetype;
        }
    }
}
