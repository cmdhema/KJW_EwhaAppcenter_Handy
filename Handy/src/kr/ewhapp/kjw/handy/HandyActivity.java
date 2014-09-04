package kr.ewhapp.kjw.handy;

import kr.ewhapp.handy.R;
import kr.ewhapp.kjw.handy.setting.*;
import kr.ewhapp.kjw.handy.study.*;
import kr.ewhapp.kjw.handy.timeline.*;
import kr.ewhapp.kjw.handy.timer.*;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.capricorn.ArcMenu;

@WindowFeature(Window.FEATURE_NO_TITLE)
@EActivity(R.layout.layout_main)
public class HandyActivity extends Activity {

	private int menuItems[] = { R.drawable.menu_timeline, R.drawable.menu_timer, R.drawable.menu_study, R.drawable.menu_setting };
	private TimelineFragment timelineFragment;
	private TimerFragment timerFragment;
	private SubjectFragment subjectFragment;
	private SettingFragment settingFragment;

	@ViewById(R.id.title_tv)
	public static TextView titleTv;
	@ViewById(R.id.arc_menu)
	public static ArcMenu menu;

	private long m_startTime;
	private long m_endTime;
	private boolean m_isPressedBackButton;

	@AfterViews
	void init() {

		initArcMenu();
		initFragment();

		getFragmentManager().beginTransaction().replace(R.id.container, timelineFragment).commit();

	}

	private void initFragment() {
		if (timelineFragment == null)
			timelineFragment = new TimelineFragment_();
		if (timerFragment == null)
			timerFragment = new TimerFragment_();
		if (subjectFragment == null)
			subjectFragment = new SubjectFragment_();
		if (settingFragment == null)
			settingFragment = new SettingFragment_();
	}

	private void initArcMenu() {
		final int itemCount = menuItems.length;
		for (int i = 0; i < itemCount; i++) {
			ImageView item = new ImageView(this);
			item.setImageResource(menuItems[i]);

			final int position = i;
			menu.addItem(item, new OnClickListener() {

				@Override
				public void onClick(View v) {
					switch (position) {
					case 0:
						if ( !isTimerProcessing() ) {
							titleTv.setText("타임라인");
							getFragmentManager().beginTransaction().replace(R.id.container, timelineFragment).commit();
						} else
							showStopDialog();
						break;
					case 1:
						if ( !isTimerProcessing() ) {
							titleTv.setText("시험시간관리");
							getFragmentManager().beginTransaction().replace(R.id.container, timerFragment).commit();
						} else
							showStopDialog();
						break;
					case 2:
						if ( !isTimerProcessing() ) {
							titleTv.setText("시험점수관리");
							getFragmentManager().beginTransaction().replace(R.id.container, subjectFragment).commit();
						} else
							showStopDialog();
						break;
					case 3:
						if ( !isTimerProcessing() ) {
							titleTv.setText("설정");
							getFragmentManager().beginTransaction().replace(R.id.container, settingFragment).commit();
						} else
							showStopDialog();
						break;
					}

				}
			});
		}
	}

	protected void showStopDialog() {
		new AlertDialog.Builder(this, Window.FEATURE_NO_TITLE).setMessage("타이머 진행 중엔 메뉴 이동이 불가능합니다.").setPositiveButton("확인", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		}).show();
	}

	protected boolean isTimerProcessing() {
		if ( TimerFragment.isTimerProgress )
			return true;
		else
			return false;
	}

	@Override
	public void onBackPressed() {

		if (getFragmentManager().getBackStackEntryCount() == 0) {
			m_endTime = System.currentTimeMillis();

			if (m_endTime - m_startTime > 2000)
				m_isPressedBackButton = false;
			if (m_isPressedBackButton == false) {
				m_isPressedBackButton = true;
				m_startTime = System.currentTimeMillis();
				Toast.makeText(this, "'뒤로'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
			} else {
				finish();
				System.exit(0);
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		} else
			super.onBackPressed();
	}
}
