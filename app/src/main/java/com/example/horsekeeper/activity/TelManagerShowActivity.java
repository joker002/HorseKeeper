package com.example.horsekeeper.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.horsekeeper.R;
import com.example.horsekeeper.adapter.TelManagerShowListAdapter;
import com.example.horsekeeper.db.TELDBUtil;
import com.example.horsekeeper.entity.TelInfo;
import com.example.horsekeeper.util.Constants;

/**
 * ͨѶ��ȫ��������
 * 
 * @author lenovo
 * 
 */
// Intent ��ת ������ͼ����ʾ��ͼ
// ��һ��View���ص���һ��View����
public class TelManagerShowActivity extends BaseActivity implements
		OnItemClickListener, OnClickListener {

	/**
	 * �����ListView
	 */
	ListView listView;

	/**
	 * �����ͨѶ¼�������� ListView������
	 */
	TelManagerShowListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// ����Ĭ�ϵĿ��
		listView = new ListView(this);

		// ����BaseActivity�ķ���

		setContentView(listView);

	}

	@Override
	void initView() {
		mImgLeft.setImageResource(R.drawable.btn_homeasup_default);
		mImgRight.setVisibility(View.INVISIBLE);
		mTvTittle.setText("��Ӫ��");

		// ��һ�ִ�ֵ ʹ��Bundle

		// Intent intent = getIntent();
		// Bundle bun = intent.getExtras();
		// int position = bun.getInt(Constants.PUT_POSITION);

		// �ڶ��ִ�ֵ(ʹ�ù㷺,��)
		// getIntent() activity�ķ���
		Intent intent = getIntent();

		// �õ���TelManagerActivity��������ֵ
		// û��ֵ ���Զ���ֵ-1
		int position = intent.getIntExtra(Constants.PUT_POSITION, -1);
		// LogWapper.e("aaa", "-----" + position);

		// ��Ҫreturn
		// finish��ҪonDestroy ��ִ��activity�е��������� ִ�к���Ĵ��� Ӧ�ü���return
		if (position == -1) {
			Toast.makeText(this, "�����쳣", Toast.LENGTH_LONG).show();
			finish();
			return;
		}

		// ����������
		adapter = new TelManagerShowListAdapter(this,
				TELDBUtil.selectTable("table" + (position + 1)));

		listView.setAdapter(adapter);

		// listView�ĵ���¼�
		listView.setOnItemClickListener(this);
		setOnClickListers(this, mImgLeft);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		// BaseAdapter�ķ���getItem
		TelInfo info = (TelInfo) adapter.getItem(position);

		// �õ��绰����
		String number = info.getNumber();
		// �ڵ��������У�ͳһ��Դ��ʶ����Uniform Resource Identifier����URI)��һ�����ڱ�ʶĳһ��������Դ���Ƶ��ַ�����

		// ���ֱ�ʶ�����û����κΣ��������غͻ�����������Դͨ���ض���Э����н���������URI�ɰ���ȷ���﷨�����Э��ķ��������塣
		Uri uri = Uri.parse("tel:" + number);
		// �����Android_jump��
		Intent intent = new Intent(Intent.ACTION_CALL, uri);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, TelManagerActivity.class);
		startActivity(intent);
	}
}
