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
    //    public static String ROOT = "http://192.168.10.99:8081/page/";
    public static String ROOT = "https://wxgzh.0746dq.com/slpdJK/page/";

    //用户登录
    public static String LOGINURL     = ROOT + "login.do";
    //配送
    public static String SELECTAPPLY1 = ROOT + "selectapply1.do";
    //更改状态:接单、提货
    public static String PSWC1        = ROOT + "pswc1.do";
    //确认完成
    public static String PSWC         = ROOT + "pswc.do";
    //提交图片
    public static String PSIMAGE      = ROOT + "psimage.do";

    //安装
    //查询待接单
    public static String SELECTAPPLY = ROOT + "selectapply.do";
    //查询待预约
    public static String DAIYUYUE    = ROOT + "daiyuyue.do";
    //更改成上门服务
    public static String UPDATETYPE1 = ROOT + "Updatetype1.do";
    //查询上门服务
    public static String SMFW        = ROOT + "smfw.do";
    //上门服务完成
    public static String WANCHENG    = ROOT + "wancheng.do";
    //查询服务完成
    public static String FWWC        = ROOT + "fwwc.do";


    //维修
    //待接单
    public static String SELECTAPPLYWX = ROOT + "selectapplywx.do";
    //待预约
    public static String DAIYUYUEWX    = ROOT + "daiyuyuewx.do";
    //上门服务
    public static String SMFWWX        = ROOT + "smfwwx.do";
    //服务完成
    public static String FWWCWX        = ROOT + "fwwcwx.do";


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

    //所有订单查询
    public static String WANCHENGDATE = ROOT + "wanchengdate.do";
    //签到
    public static String SHANGMEN     = ROOT + "shangmen.do";
    //查看详情
    public static String DETAIL       = ROOT + "detail.do";
    //待预约中，提交扫码信息
    public static String SELECTEWM    = ROOT + "selectewm.do";
}
