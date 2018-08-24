package com.bt.andy.sanlianASxcx.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqliteOpenHelper extends SQLiteOpenHelper {

	public MySqliteOpenHelper(Context context) {
		/**
		 * @param context 上下文
	     * @param name 数据库的名字
	     * @param factory 指针工厂（信息集），默认为null
	     * @param version 数据库的版本（从1开始），版本号提升的时候调用onupgrade方法（不能降级）
	     *     
	     **/
		super(context, "taxiorder.db", null, 1);
	}
	
	//当数据库第一次创建的时候被调用（已经创建好数据库不会再执行）
	//适合创建表
	@Override
	public void onCreate(SQLiteDatabase db) {
		//创建表(小知识：_id 是Android默认的id格式) primary key not null
		db.execSQL("create table info(_id integer primary key not null, xxid varchar(20),name varchar(20),startplace varchar(100),endplace varchar(100),time varchar(20),waite varchar(20),addprice varchar(20),isaccept varchar(20))");
	}
	
	//数据库升级（当版本号发生提升的时候）
	//适合增删表，修改表结构（表的字段）
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//添加一个字段
		db.execSQL("alter table info add phone varchar(20)");
		System.out.println("数据库升级啦");
		
//		if (oldVersion == 1) {
//			db.execSQL("alter table info add phone varchar(20)");
//			db.execSQL("alter table info add address varchar(20)");
//		}
//		if (oldVersion == 2) {
//			db.execSQL("alter table info add address varchar(20)");
//		}
		
	}

}
