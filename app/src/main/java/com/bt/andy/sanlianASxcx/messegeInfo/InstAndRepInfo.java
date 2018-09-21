package com.bt.andy.sanlianASxcx.messegeInfo;

import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2018/8/28 15:16
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class InstAndRepInfo {

    /**
     * result : 1
     * fshifuid : 4028824e65e5b3630165e5b5e74b0003
     * message : 数据查找成功
     * applylist : [{"faddress":"","ftype":"","ftel":"18234567890","special_note":"","fbstatus":"2","fpeople":"测试","ftype1":"","fgetdate":"1900-01-01 00:00:00.0000000","id":"40288a0965eab9bd0165eabd9b0e0005","psstatus":"5","forderno":"","ordertype":null},{"faddress":"","ftype":"","ftel":"18234567890","special_note":"","fbstatus":"2","fpeople":"测试","ftype1":"1","fgetdate":"1900-01-01 00:00:00.0000000","id":"40288a0965eb183f0165eb2046050004","psstatus":"1","forderno":"","ordertype":"安装单"}]
     */

    private int                 result;
    private String              fshifuid;
    private String              message;
    private List<ApplylistBean> applylist;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getFshifuid() {
        return fshifuid;
    }

    public void setFshifuid(String fshifuid) {
        this.fshifuid = fshifuid;
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
         * ftype :
         * ftel : 18234567890
         * special_note :
         * fbstatus : 2
         * fpeople : 测试
         * ftype1 :
         * fgetdate : 1900-01-01 00:00:00.0000000
         * id : 40288a0965eab9bd0165eabd9b0e0005
         * psstatus : 5
         * forderno :
         * ordertype : null
         */

        private String faddress;
        private String ftype;
        private String ftel;
        private String special_note;
        private String fbstatus;
        private String fpeople;
        private String ftype1;
        private String fgetdate;
        private String id;
        private String psstatus;
        private String forderno;
        private String ordertype;
        /**
         * warehouse : jsj
         * fgoods : 北京
         * fdate : 2018-09
         */

        private String warehouse;
        private String fgoods;
        private String fdate;

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

        public String getFtype1() {
            return ftype1;
        }

        public void setFtype1(String ftype1) {
            this.ftype1 = ftype1;
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

        public String getWarehouse() {
            return warehouse;
        }

        public void setWarehouse(String warehouse) {
            this.warehouse = warehouse;
        }

        public String getFgoods() {
            return fgoods;
        }

        public void setFgoods(String fgoods) {
            this.fgoods = fgoods;
        }

        public String getFdate() {
            return fdate;
        }

        public void setFdate(String fdate) {
            this.fdate = fdate;
        }
    }
}
