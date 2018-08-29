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
    public static String ROOT = "http://192.168.10.125:8081/page/";

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
    //待接单
    public static String SELECTAPPLY = ROOT + "selectapply.do";
    //待预约
    public static String DAIYUYUE = ROOT + "daiyuyue.do";
    //上门服务
    public static String SMFW = ROOT + "smfw.do";




    //维修
    //待接单
    public static String SELECTAPPLYWX = ROOT + "selectapplywx.do";
    //待预约
    public static String DAIYUYUEWX = ROOT + "daiyuyuewx.do";
    //上门服务
    public static String SMFWWX = ROOT + "smfwwx.do";
}
