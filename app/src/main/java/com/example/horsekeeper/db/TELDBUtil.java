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
 * �����绰���Ĺ�����
 * 
 * @author lenovo
 * 
 */
public class TELDBUtil {

	/**
	 * �ļ�
	 */
	static File mFile;

	/**
	 * ·��
	 */
	public static final String DB_PATH = "data/data/com.example.horsekeeper/databases/commonnum.db";

	/**
	 * ����
	 */
	public static final String PACKAGE = "com.example.horsekeeper";

	/**
	 * ��assest��������ݿ� ������ data/data/����/databases
	 * 
	 * @throws IOException
	 */
	public static void copeDB(Context context) throws IOException {

		// ��assest�������
		// ������(����/���ݿ���)
		// �õ�AssetManager����
		AssetManager aManager = context.getAssets();

		InputStream in = aManager.open("db/commonnum.db");

		// ��һ���µ��ļ�
		// �ж��ļ��Ƿ����
		mFile = new File(DB_PATH);
		if (!mFile.exists()) {
			// ��������ʱ�Ƚ�������·��
			// getParentFile�õ��ļ�֮ǰ���ļ���
			// file.mkdirs()�Ὣ�ļ�Ҳ����Ϊ�ļ���
			mFile.getParentFile().mkdirs();
			// �����ļ�
			mFile.createNewFile();
		}
		// д
		OutputStream out = new FileOutputStream(mFile);
		// ����
		// д���ٶ�
		byte[] b = new byte[1024];
		int len;
		// �� û���� д��
		while ((len = in.read(b)) != -1) {
			out.write(b, 0, len);
		}
		// ���� ����дfinally(main?) Ӧ���ж�null
		out.flush();
		in.close();
		out.close();
		// file.length()
	}

	/**
	 * �õ����ݿ���� ��
	 * 
	 * @return
	 */
	public static ArrayList<String> selectClasslist() {

		ArrayList<String> arr = new ArrayList<String>();

		// SQLiteDatabase�������ݿ� ע��ʹ�õķ���
		// ������һ����·�� Ҳ�������ļ�
		SQLiteDatabase base = SQLiteDatabase.openOrCreateDatabase(mFile, null);

		// ��"classlist"���ݿ������ query��ѯ����

		// Cursor�α�
		Cursor cursor = base.query("classlist", null, null, null, null, null,
				null);

		// cursor.moveToNext()�жϻ���������
		while (cursor.moveToNext()) {
			// �õ����� ֻҪ"name"
			// ͨ������ ��ȡ����
			String name = cursor.getString(cursor.getColumnIndex("name"));
			// ��ӵ�������
			arr.add(name);
		}
		base.close();
		return arr;
	}

	/**
	 * ��ѯ table--->table8������
	 */
	public static ArrayList<TelInfo> selectTable(String tableName) {

		ArrayList<TelInfo> arr = new ArrayList<TelInfo>();
		SQLiteDatabase base = SQLiteDatabase
				.openOrCreateDatabase(DB_PATH, null);

		// �ǵ�from�����пո� * ��ѯ������
		String sql = "select * from " + tableName;
		// execSQL(�������ڲ�ѯ,û�з���ֵ��û�н��) rawQuery
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
