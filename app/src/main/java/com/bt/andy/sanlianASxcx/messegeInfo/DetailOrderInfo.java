package com.bt.andy.sanlianASxcx.messegeInfo;

import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2018/9/6 15:48
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class DetailOrderInfo {

    /**
     * result : 1
     * id : 40288a0965f082a50165f08a43780007
     * message : 信息查找成功
     * applylist : [{"faddress":"","pinming":"","notes":"","ftel":"18234567890","fbstatus":"3","fpeople":"测试安装2","azfdate":"","fshifu":"张三","ftype":"1","fmoney_kind":"保修期之内","fdate":"","id":"40288a0965f082a50165f08a43780007","psshifu":""}]
     */

    private int                 result;
    private String              id;
    private String              message;
    private List<ApplylistBean> applylist;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ApplylistBean> getApplylist() {
        return applylist;
    }

    public void setApplylist(List<ApplylistBean> applylist) {
        this.applylist = applylist;
    }

    public static class ApplylistBean {
        /**
         * faddress :
         * pinming :
         * notes :
         * ftel : 18234567890
         * fbstatus : 3
         * fpeople : 测试安装2
         * azfdate :
         * fshifu : 张三
         * ftype : 1
         * fmoney_kind : 保修期之内
         * fdate :
         * id : 40288a0965f082a50165f08a43780007
         * psshifu :
         */

        private String faddress;
        private String pinming;
        private String notes;
        private String ftel;
        private String fbstatus;
        private String fpeople;
        private String azfdate;
        private String fshifu;
        private String ftype;
        private String fmoney_kind;
        private String fdate;
        private String id;
        private String psshifu;
        /**
         * qty : 12
         * fgodate : 2018-09-20
         */

        private String qty;
        private String fgodate;

        public String getFaddress() {
            return faddress;
        }

        public void setFaddress(String faddress) {
            this.faddress = faddress;
        }

        public String getPinming() {
            return pinming;
        }

        public void setPinming(String pinming) {
            this.pinming = pinming;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

        public String getFtel() {
            return ftel;
        }

        public void setFtel(String ftel) {
            this.ftel = ftel;
        }

        public String getFbstatus() {
            return fbstatus;
        }

        public void setFbstatus(String fbstatus) {
            this.fbstatus = fbstatus;
        }

        public String getFpeople() {
            return fpeople;
        }

        public void setFpeople(String fpeople) {
            this.fpeople = fpeople;
        }

        public String getAzfdate() {
            return azfdate;
        }

        public void setAzfdate(String azfdate) {
            this.azfdate = azfdate;
        }

        public String getFshifu() {
            return fshifu;
        }

        public void setFshifu(String fshifu) {
            this.fshifu = fshifu;
        }

        public String getFtype() {
            return ftype;
        }

        public void setFtype(String ftype) {
            this.ftype = ftype;
        }

        public String getFmoney_kind() {
            return fmoney_kind;
        }

        public void setFmoney_kind(String fmoney_kind) {
            this.fmoney_kind = fmoney_kind;
        }

        public String getFdate() {
            return fdate;
        }

        public void setFdate(String fdate) {
            this.fdate = fdate;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPsshifu() {
            return psshifu;
        }

        public void setPsshifu(String psshifu) {
            this.psshifu = psshifu;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getFgodate() {
            return fgodate;
        }

        public void setFgodate(String fgodate) {
            this.fgodate = fgodate;
        }
    }
}
