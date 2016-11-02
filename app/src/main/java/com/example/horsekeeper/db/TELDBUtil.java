package com.example.horsekeeper.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.horsekeeper.entity.TelInfo;

/**
 * 操作电话本的工具类
 * 
 * @author lenovo
 * 
 */
public class TELDBUtil {

	/**
	 * 文件
	 */
	static File mFile;

	/**
	 * 路径
	 */
	public static final String DB_PATH = "data/data/com.example.horsekeeper/databases/commonnum.db";

	/**
	 * 包名
	 */
	public static final String PACKAGE = "com.example.horsekeeper";

	/**
	 * 将assest里面的数据库 拷贝到 data/data/包名/databases
	 * 
	 * @throws IOException
	 */
	public static void copeDB(Context context) throws IOException {

		// 拿assest里的数据
		// 读数据(包名/数据库名)
		// 拿到AssetManager对象
		AssetManager aManager = context.getAssets();

		InputStream in = aManager.open("db/commonnum.db");

		// 建一个新的文件
		// 判断文件是否存在
		mFile = new File(DB_PATH);
		if (!mFile.exists()) {
			// 当不存在时先建立所有路径
			// getParentFile拿到文件之前的文件夹
			// file.mkdirs()会将文件也创建为文件夹
			mFile.getParentFile().mkdirs();
			// 创建文件
			mFile.createNewFile();
		}
		// 写
		OutputStream out = new FileOutputStream(mFile);
		// 拷贝
		// 写的速度
		byte[] b = new byte[1024];
		int len;
		// 读 没有完 写入
		while ((len = in.read(b)) != -1) {
			out.write(b, 0, len);
		}
		// 关流 不能写finally(main?) 应先判断null
		out.flush();
		in.close();
		out.close();
		// file.length()
	}

	/**
	 * 拿到数据库对象 查
	 * 
	 * @return
	 */
	public static ArrayList<String> selectClasslist() {

		ArrayList<String> arr = new ArrayList<String>();

		// SQLiteDatabase操作数据库 注意使用的方法
		// 参数第一个是路径 也可以是文件
		SQLiteDatabase base = SQLiteDatabase.openOrCreateDatabase(mFile, null);

		// 查"classlist"数据库的名称 query查询方法

		// Cursor游标
		Cursor cursor = base.query("classlist", null, null, null, null, null,
				null);

		// cursor.moveToNext()判断还有无数据
		while (cursor.moveToNext()) {
			// 拿到数据 只要"name"
			// 通过列名 获取索引
			String name = cursor.getString(cursor.getColumnIndex("name"));
			// 添加到集合中
			arr.add(name);
		}
		base.close();
		return arr;
	}

	/**
	 * 查询 table--->table8的数据
	 */
	public static ArrayList<TelInfo> selectTable(String tableName) {

		ArrayList<TelInfo> arr = new ArrayList<TelInfo>();
		SQLiteDatabase base = SQLiteDatabase
				.openOrCreateDatabase(DB_PATH, null);

		// 记得from后面有空格 * 查询所有列
		String sql = "select * from " + tableName;
		// execSQL(不能用于查询,没有返回值，没有结果) rawQuery
		Cursor cursor = base.rawQuery(sql, null);

		while (cursor.moveToNext()) {

			String name = cursor.getString(cursor.getColumnIndex("name"));
			String number = cursor.getString(cursor.getColumnIndex("number"));

			TelInfo info = new TelInfo(name, number);
			arr.add(info);
		}
		return arr;
	}
}
