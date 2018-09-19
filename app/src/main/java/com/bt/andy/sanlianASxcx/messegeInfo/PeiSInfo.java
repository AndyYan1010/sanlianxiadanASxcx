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
     * fopenid : 4028824e65e5b3630165e5b5e74b0003
     * message : 信息查找成功
     * applylist : [{"faddress":"","ftype":"1","ftel":"18234567890","special_note":"","fbstatus":"3","fpeople":"测试安装2","fgetdate":"1900-01-01 00:00:00.0000000","id":"40288a0965f082a50165f08a43780007","psstatus":"","forderno":"","ordertype":"安装单"}]
     */

    private int                 result;
    private String              fopenid;
    private String              message;
    private List<ApplylistBean> applylist;

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
         * ftype : 1
         * ftel : 18234567890
         * special_note :
         * fbstatus : 3
         * fpeople : 测试安装2
         * fgetdate : 1900-01-01 00:00:00.0000000
         * id : 40288a0965f082a50165f08a43780007
         * psstatus :
         * forderno :
         * ordertype : 安装单
         */

        private String faddress;
        private String ftype;
        private String ftel;
        private String special_note;
        private String fbstatus;
        private String fpeople;
        private String fgetdate;
        private String id;
        private String psstatus;
        private String forderno;
        private String ordertype;

        public String getFaddress() {
            return faddress;
        }

        public void setFaddress(String faddress) {
            this.faddress = faddress;
        }

        public String getFtype() {
            return ftype;
        }

        public void setFtype(String ftype) {
            this.ftype = ftype;
        }

        public String getFtel() {
            return ftel;
        }

        public void setFtel(String ftel) {
            this.ftel = ftel;
        }

        public String getSpecial_note() {
            return special_note;
        }

        public void setSpecial_note(String special_note) {
            this.special_note = special_note;
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

        public String getFgetdate() {
            return fgetdate;
        }

        public void setFgetdate(String fgetdate) {
            this.fgetdate = fgetdate;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPsstatus() {
            return psstatus;
        }

        public void setPsstatus(String psstatus) {
            this.psstatus = psstatus;
        }

        public String getForderno() {
            return forderno;
        }

        public void setForderno(String forderno) {
            this.forderno = forderno;
        }

        public String getOrdertype() {
            return ordertype;
        }

        public void setOrdertype(String ordertype) {
            this.ordertype = ordertype;
        }
    }
}
