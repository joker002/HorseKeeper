package com.example.horsekeeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.example.horsekeeper.R;

public class AnimationActivity extends Activity {

	ImageView mAnimation;

	AnimationDrawable anim;

	Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				anim.stop();

				Intent intent = new Intent(AnimationActivity.this,
						HomeActivity.class);
				startActivity(intent);
				// ½¥±ä
				overridePendingTransition(R.anim.alpha_home,
						R.anim.alpha_animation);
				finish();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.animation);
		init();
	}

	private void init() {
		mAnimation = (ImageView) findViewById(R.id.iv_animation);
		// Ö¡¶¯»­
		mAnimation.setImageResource(R.drawable.animation);

		anim = (AnimationDrawable) mAnimation.getDrawable();
		anim.start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(2000);

					handler.sendEmptyMessage(1);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

	}
}
