package com.example.horsekeeper.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.example.horsekeeper.R;
import com.example.horsekeeper.adapter.GuiderPagerAdapter;

/**
 * ��������
 * 
 * @author cc
 * 
 */
// �õ���SharedPreferences�洢����(�����洢һЩС����)
// �õ���ViewPager �����л���ǰView ��Ҫ������ PagerAdapter
public class GuideActivity extends BaseActivity implements
		OnPageChangeListener, OnClickListener {
	private static final String PREFRENCE_NAME = null;
	/**
	 * д��ͼƬId������
	 */
	public int[] mPicture = { R.drawable.adware_style_applist,
			R.drawable.adware_style_banner, R.drawable.adware_style_creditswall };
	/**
	 * ImageView���͵�����
	 */
	ImageView[] mImg = new ImageView[3];
	/**
	 * ViewPager
	 */
	ViewPager pager;
	/**
	 * ��һ��СԲ��
	 */
	ImageView mImFirstPoint;
	/**
	 * �ڶ���СԲ��
	 */
	ImageView mImSecondPoint;
	/**
	 * ������СԲ��
	 */
	ImageView mImThirdPoint;
	/**
	 * ֱ��������ť
	 */
	Button mBtnPass;
	/**
	 * �洢С����
	 */
	SharedPreferences preference;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 1.�ҵ��ؼ�
		// 2.����Դ
		// 3.�õ�������
		// 4.�ؼ�

		// setContentView(R.layout.ativity_guide);
		// init();

		// ���õ���BaseActivity
		setContentView(R.layout.ativity_guide);

		// �ж� ��һ����ִ��if������ĳ���,���ǵ�һ�ν������if����� ʵ����ת
		if (!getData()) {

			Intent intent = new Intent(GuideActivity.this,
					AnimationActivity.class);

			startActivity(intent);

			finish();
		}
	}

	/**
	 * ����SharedPreferences�ڵ����� ������
	 * 
	 * @return
	 */
	private boolean getData() {
		// ���ļ�(С����)

		// �õ�SharedPreferences����
		// MODE_PRIVATE(ֻ�б�Ӧ�ÿ��Է���) 0Ҳ���Դ��� ��������ֵ
		preference = getSharedPreferences(PREFRENCE_NAME, MODE_PRIVATE);

		// �ڶ�����Ĭ��ֵ ���keyд���˸ı�ΪĬ��ֵ
		boolean preferen = preference.getBoolean("first", true);

		return preferen;
	}

	/**
	 * ��ʼ���ؼ� �Լ����¼�
	 */
	@Override
	void initView() {

		// �ҵ��ؼ�id
		pager = (ViewPager) findViewById(R.id.vp_guide);
		mImFirstPoint = (ImageView) findViewById(R.id.Im_guide_first);
		mImSecondPoint = (ImageView) findViewById(R.id.Im_guide_second);
		mImThirdPoint = (ImageView) findViewById(R.id.Im_guide_third);
		mBtnPass = (Button) findViewById(R.id.btn_guide_pass);

		// ��������װ����
		mImg[0] = mImFirstPoint;
		mImg[1] = mImSecondPoint;
		mImg[2] = mImThirdPoint;

		// �õ�����Դ
		/**
		 * ����ΪImageView�ļ���
		 */
		ArrayList<ImageView> listImg = new ArrayList<ImageView>();
		for (int i = 0; i < mPicture.length; i++) {

			// ���� imageView ��������ΪĬ��ֵ
			ImageView img = new ImageView(this);

			// ͨ�������±��õ��������ͼƬid
			// ��setImageResource��ͼƬid���ͼƬ
			img.setImageResource(mPicture[i]);

			// ����ͼƬλ��
			img.setScaleType(ScaleType.FIT_END);
			// ��ͼƬ��ӵ�������
			listImg.add(img);

			// �õ�������
			GuiderPagerAdapter adapter = new GuiderPagerAdapter(listImg);
			/**
			 * ����������
			 */
			pager.setAdapter(adapter);
			/**
			 * ��������OnPageChangeListener
			 */
			pager.setOnPageChangeListener(this);
			/**
			 * ������ť
			 */
			mBtnPass.setOnClickListener(this);
		}

	}

	/**
	 * ҳ�����״̬�ĸı�
	 * 
	 * 1:���� 2:�����˶� 0:ֹͣ
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	/**
	 * arg0 ������ҳ����±�
	 * 
	 * arg1 ��������ƫ�ưٷֱ�
	 * 
	 * arg2 ��������ƫ������
	 * 
	 */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	/**
	 * ��ѡ����
	 */
	@Override
	public void onPageSelected(int arg0) {
		/**
		 * ���ı����ʱ ����ͼƬ�ı� Button��ť����(���غ�ֻ�п�ʱ���ܵ��)�����
		 */
		mBtnPass.setVisibility(View.GONE);
		for (ImageView img : mImg) {
			img.setImageResource(R.drawable.adware_style_default);
		}
		mImg[arg0].setImageResource(R.drawable.adware_style_selected);
		if (arg0 == 2) {
			mBtnPass.setVisibility(View.VISIBLE);
		}

		// if (arg0 == 0) {
		// mImFirstPoint.setImageResource(R.drawable.adware_style_selected);
		// mImSecondPoint.setImageResource(R.drawable.adware_style_default);
		// mImThirdPoint.setImageResource(R.drawable.adware_style_default);
		// mBtnPass.setVisibility(View.GONE);
		// } else if (arg0 == 1) {
		// mImFirstPoint.setImageResource(R.drawable.adware_style_default);
		// mImSecondPoint.setImageResource(R.drawable.adware_style_selected);
		// mImThirdPoint.setImageResource(R.drawable.adware_style_default);
		// mBtnPass.setVisibility(View.GONE);
		// } else {
		// mImFirstPoint.setImageResource(R.drawable.adware_style_default);
		// mImSecondPoint.setImageResource(R.drawable.adware_style_default);
		// mImThirdPoint.setImageResource(R.drawable.adware_style_selected);
		// mBtnPass.setVisibility(View.VISIBLE);
		// }
	}

	@Override
	public void onClick(View v) {

		// �ȵ�������ȡEditor�Ķ���
		Editor edit = preference.edit();
		// д
		edit.putBoolean("first", false);
		// �ύ
		edit.commit();

		/**
		 * ��ת����һ��Activity
		 */
		Intent intent = new Intent(GuideActivity.this, AnimationActivity.class);

		startActivity(intent);

		finish();
	}

}
