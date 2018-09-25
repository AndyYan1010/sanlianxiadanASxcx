package com.bt.andy.sanlianASxcx;

/**
 * @创建者 AndyYan
 * @创建时间 2018/8/28 8:48
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class NetConfig {
    //服务器总地址
    //        public static String ROOT = "http://192.168.10.137:8081/page/";
    public static String ROOT = "http://hnyzsl.xicp.net:8081/slpdJK/page/";

    //查看详情
    public static String DETAIL       = ROOT + "detail.do";
    //查询所有订单（抢单、排单）
    public static String SELECTAPPLY  = ROOT + "selectapply.do";
    //查询所有待预约
    public static String DAIYUYUE     = ROOT + "daiyuyue.do";
    //查询所有上门服务
    public static String SMFW         = ROOT + "smfw.do";
    //查询所有服务完成
    public static String COMPLETE1    = ROOT + "complete1.do";
    //所有订单（日期）查询
    public static String WANCHENGDATE = ROOT + "wanchengdate.do";

    //用户登录
    public static String LOGINURL = ROOT + "login.do";
    //配送
    //更改状态:接单、提货
    public static String PSWC1    = ROOT + "pswc1.do";
    //确认完成
    public static String PSWC     = ROOT + "pswc.do";
    //提交图片
    public static String PSIMAGE  = ROOT + "psimage.do";

    //安装
    //更改成上门服务
    public static String UPDATETYPE1 = ROOT + "Updatetype1.do";
    //上门服务完成
    public static String WANCHENG    = ROOT + "wancheng.do";


    //抢单订单更改状态
    public static String UPDATEORDER = ROOT + "updateorder.do";
    //分配单接单
    public static String YUYUE       = ROOT + "yuyue.do";
    //拨打电话，接口
    public static String CALL        = ROOT + "call.do";

    //提交图片接口1
    public static String INSERTIMG  = ROOT + "Insertimg.do";
    //提交图片接口2
    public static String INSERTIMG1 = ROOT + "Insertimg1.do";

    //签到
    public static String SHANGMEN = ROOT + "shangmen.do";

    //待预约中，提交扫码信息
    public static String SELECTEWM  = ROOT + "selectewm.do";
    //更改上门时间
    public static String UPDATEDATE = ROOT + "UpdateDate.do";
}
