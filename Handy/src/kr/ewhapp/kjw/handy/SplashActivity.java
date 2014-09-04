package kr.ewhapp.kjw.handy;

import kr.ewhapp.handy.R;
import kr.ewhapp.kjw.handy.db.DBHelper;
import kr.kau.kjw.db.DBModule;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

@EActivity(R.layout.layout_splash)
public class SplashActivity extends Activity implements Callback {

	private static final int DELAY_TIME =2000;
	private int splashCount;
	
	private Handler handler;

	@ViewById(R.id.background_iv)
	ImageView backgroundIv;
	
	@AfterViews
	void splash() {
		
		handler = new Handler(this);
		DBModule.getInstance().startThread(new DBHelper(this), handler);
		
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {

				if (splashCount == 0) {
					splashCount++;
					backgroundIv.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out));
					backgroundIv.setVisibility(View.INVISIBLE);
					new Handler().postDelayed(this, DELAY_TIME);
					return;
				}
				
				Intent intent = new Intent(SplashActivity.this, HandyActivity_.class);
				startActivity(intent);
				
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				finish();

			}
		}, DELAY_TIME);
	}

	@Override
	public boolean handleMessage(Message msg) {
		return false;
	}
}
