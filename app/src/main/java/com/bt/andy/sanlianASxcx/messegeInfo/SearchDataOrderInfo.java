package com.bt.andy.sanlianASxcx.messegeInfo;

import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2018/9/3 16:47
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class SearchDataOrderInfo {

    /**
     * result : 1
     * shifuid : 4d2881f563d2db940163d2dcc7080003
     * enddate : 2018-09-03
     * begindate : 2018-08-28
     * oredrpaylist : [{"FBillNo":"XOUT000125","farecost":"","begindate":"2018-08-29 09:35:24.0000000","orderno":"OR18-08-2700216","notes":"","materialcosts":"","fnote":"","psstatus":"0","FItemID":3324,"FName":"配送安装","fmobile":"13243678080","fshifu":"张三","FDeliveryAddress":"","FInterID":"1959","fpic":"1535506542252.jpg","fcontact":"蒋小英","mealscost":"","bpmstatus":"A","id":"7590B3CB-29C2-4E42-AA34-EE6D0F75154C","fgoods":"*","distancecost":"","lat":"26.249492645263672","lng":"111.62911224365234","FBillNoorder":"SEORD000579","FHeadSelfS0167":"990112","purchase":"尊敬的零售客户","pay":"","fname1":"长虹电视机55F8","FPrice":0,"fshifuid":"4d2881f563d2db940163d2dcc7080003","warehouse":"工业园仓","FAmount":0,"azcost":"","FDate":1535212800000,"othercosts":"","FQty":1,"pscost":"","enddate":"2018-08-30 14:42:49.0000000","ftype1":"1","upstairscost":"","ewm":"","FUnitID":268,"status":"1","logistics_direction":"城区"}]
     */

    private int                    result;
    private String                 shifuid;
    private String                 enddate;
    private String                 begindate;
    private List<OredrpaylistBean> oredrpaylist;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getShifuid() {
        return shifuid;
    }

    public void setShifuid(String shifuid) {
        this.shifuid = shifuid;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getBegindate() {
        return begindate;
    }

    public void setBegindate(String begindate) {
        this.begindate = begindate;
    }

    public List<OredrpaylistBean> getOredrpaylist() {
        return oredrpaylist;
    }

    public void setOredrpaylist(List<OredrpaylistBean> oredrpaylist) {
        this.oredrpaylist = oredrpaylist;
    }

    public static class OredrpaylistBean {
        /**
         * FBillNo : XOUT000125
         * farecost :
         * begindate : 2018-08-29 09:35:24.0000000
         * orderno : OR18-08-2700216
         * notes :
         * materialcosts :
         * fnote :
         * psstatus : 0
         * FItemID : 3324
         * FName : 配送安装
         * fmobile : 13243678080
         * fshifu : 张三
         * FDeliveryAddress :
         * FInterID : 1959
         * fpic : 1535506542252.jpg
         * fcontact : 蒋小英
         * mealscost :
         * bpmstatus : A
         * id : 7590B3CB-29C2-4E42-AA34-EE6D0F75154C
         * fgoods : *
         * distancecost :
         * lat : 26.249492645263672
         * lng : 111.62911224365234
         * FBillNoorder : SEORD000579
         * FHeadSelfS0167 : 990112
         * purchase : 尊敬的零售客户
         * pay :
         * fname1 : 长虹电视机55F8
         * FPrice : 0
         * fshifuid : 4d2881f563d2db940163d2dcc7080003
         * warehouse : 工业园仓
         * FAmount : 0
         * azcost :
         * FDate : 1535212800000
         * othercosts :
         * FQty : 1
         * pscost :
         * enddate : 2018-08-30 14:42:49.0000000
         * ftype1 : 1
         * upstairscost :
         * ewm :
         * FUnitID : 268
         * status : 1
         * logistics_direction : 城区
         */

        private String FBillNo;
        private String farecost;
        private String begindate;
        private String orderno;
        private String notes;
        private String materialcosts;
        private String fnote;
        private String psstatus;
        private int    FItemID;
        private String FName;
        private String fmobile;
        private String fshifu;
        private String FDeliveryAddress;
        private String FInterID;
        private String fpic;
        private String fcontact;
        private String mealscost;
        private String bpmstatus;
        private String id;
        private String fgoods;
        private String distancecost;
        private String lat;
        private String lng;
        private String FBillNoorder;
        private String FHeadSelfS0167;
        private String purchase;
        private String pay;
        private String fname1;
        private int    FPrice;
        private String fshifuid;
        private String warehouse;
        private int    FAmount;
        private String azcost;
        private long   FDate;
        private String othercosts;
        private int    FQty;
        private String pscost;
        private String enddate;
        private String ftype1;
        private String upstairscost;
        private String ewm;
        private int    FUnitID;
        private String status;
        private String logistics_direction;

        public String getFBillNo() {
            return FBillNo;
        }

        public void setFBillNo(String FBillNo) {
            this.FBillNo = FBillNo;
        }

        public String getFarecost() {
            return farecost;
        }

        public void setFarecost(String farecost) {
            this.farecost = farecost;
        }

        public String getBegindate() {
            return begindate;
        }

        public void setBegindate(String begindate) {
            this.begindate = begindate;
        }

        public String getOrderno() {
            return orderno;
        }

        public void setOrderno(String orderno) {
            this.orderno = orderno;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

        public String getMaterialcosts() {
            return materialcosts;
        }

        public void setMaterialcosts(String materialcosts) {
            this.materialcosts = materialcosts;
        }

        public String getFnote() {
            return fnote;
        }

        public void setFnote(String fnote) {
            this.fnote = fnote;
        }

        public String getPsstatus() {
            return psstatus;
        }

        public void setPsstatus(String psstatus) {
            this.psstatus = psstatus;
        }

        public int getFItemID() {
            return FItemID;
        }

        public void setFItemID(int FItemID) {
            this.FItemID = FItemID;
        }

        public String getFName() {
            return FName;
        }

        public void setFName(String FName) {
            this.FName = FName;
        }

        public String getFmobile() {
            return fmobile;
        }

        public void setFmobile(String fmobile) {
            this.fmobile = fmobile;
        }

        public String getFshifu() {
            return fshifu;
        }

        public void setFshifu(String fshifu) {
            this.fshifu = fshifu;
        }

        public String getFDeliveryAddress() {
            return FDeliveryAddress;
        }

        public void setFDeliveryAddress(String FDeliveryAddress) {
            this.FDeliveryAddress = FDeliveryAddress;
        }

        public String getFInterID() {
            return FInterID;
        }

        public void setFInterID(String FInterID) {
            this.FInterID = FInterID;
        }

        public String getFpic() {
            return fpic;
        }

        public void setFpic(String fpic) {
            this.fpic = fpic;
        }

        public String getFcontact() {
            return fcontact;
        }

        public void setFcontact(String fcontact) {
            this.fcontact = fcontact;
        }

        public String getMealscost() {
            return mealscost;
        }

        public void setMealscost(String mealscost) {
            this.mealscost = mealscost;
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

        public String getFgoods() {
            return fgoods;
        }

        public void setFgoods(String fgoods) {
            this.fgoods = fgoods;
        }

        public String getDistancecost() {
            return distancecost;
        }

        public void setDistancecost(String distancecost) {
            this.distancecost = distancecost;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getFBillNoorder() {
            return FBillNoorder;
        }

        public void setFBillNoorder(String FBillNoorder) {
            this.FBillNoorder = FBillNoorder;
        }

        public String getFHeadSelfS0167() {
            return FHeadSelfS0167;
        }

        public void setFHeadSelfS0167(String FHeadSelfS0167) {
            this.FHeadSelfS0167 = FHeadSelfS0167;
        }

        public String getPurchase() {
            return purchase;
        }

        public void setPurchase(String purchase) {
            this.purchase = purchase;
        }

        public String getPay() {
            return pay;
        }

        public void setPay(String pay) {
            this.pay = pay;
        }

        public String getFname1() {
            return fname1;
        }

        public void setFname1(String fname1) {
            this.fname1 = fname1;
        }

        public int getFPrice() {
            return FPrice;
        }

        public void setFPrice(int FPrice) {
            this.FPrice = FPrice;
        }

        public String getFshifuid() {
            return fshifuid;
        }

        public void setFshifuid(String fshifuid) {
            this.fshifuid = fshifuid;
        }

        public String getWarehouse() {
            return warehouse;
        }

        public void setWarehouse(String warehouse) {
            this.warehouse = warehouse;
        }

        public int getFAmount() {
            return FAmount;
        }

        public void setFAmount(int FAmount) {
            this.FAmount = FAmount;
        }

        public String getAzcost() {
            return azcost;
        }

        public void setAzcost(String azcost) {
            this.azcost = azcost;
        }

        public long getFDate() {
            return FDate;
        }

        public void setFDate(long FDate) {
            this.FDate = FDate;
        }

        public String getOthercosts() {
            return othercosts;
        }

        public void setOthercosts(String othercosts) {
            this.othercosts = othercosts;
        }

        public int getFQty() {
            return FQty;
        }

        public void setFQty(int FQty) {
            this.FQty = FQty;
        }

        public String getPscost() {
            return pscost;
        }

        public void setPscost(String pscost) {
            this.pscost = pscost;
        }

        public String getEnddate() {
            return enddate;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }

        public String getFtype1() {
            return ftype1;
        }

        public void setFtype1(String ftype1) {
            this.ftype1 = ftype1;
        }

        public String getUpstairscost() {
            return upstairscost;
        }

        public void setUpstairscost(String upstairscost) {
            this.upstairscost = upstairscost;
        }

        public String getEwm() {
            return ewm;
        }

        public void setEwm(String ewm) {
            this.ewm = ewm;
        }

        public int getFUnitID() {
            return FUnitID;
        }

        public void setFUnitID(int FUnitID) {
            this.FUnitID = FUnitID;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLogistics_direction() {
            return logistics_direction;
        }

        public void setLogistics_direction(String logistics_direction) {
            this.logistics_direction = logistics_direction;
        }
    }
}
