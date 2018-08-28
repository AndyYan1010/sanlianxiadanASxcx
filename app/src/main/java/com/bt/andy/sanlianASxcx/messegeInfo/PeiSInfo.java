package com.bt.andy.sanlianASxcx.messegeInfo;

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

    private int result;
    private String          fopenid;
    private String          psstatus;
    private List<ApplyBean> apply;

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
}
