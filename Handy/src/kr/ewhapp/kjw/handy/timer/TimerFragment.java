package kr.ewhapp.kjw.handy.timer;

import java.util.Timer;
import java.util.TimerTask;

import kr.ewhapp.handy.R;
import kr.ewhapp.kjw.handy.HandyActivity;
import kr.ewhapp.kjw.handy.study.*;
import kr.ewhapp.kjw.handy.timeline.*;
import kr.ewhapp.kjw.handy.timer.TimerDialog.OnTimerDialogClickListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

@EFragment(R.layout.layout_timer)
public class TimerFragment extends Fragment {

	@ViewById(R.id.timer_hour_et)
	EditText hourEt;
	@ViewById(R.id.timer_min_et)
	EditText minEt;
	@ViewById(R.id.timer_sec_et)
	EditText secEt;
	@ViewById(R.id.timer_pause_btn)
	ImageView pauseBtn;
	@ViewById(R.id.timer_start_btn)
	ImageView startBtn;
	@ViewById(R.id.timer_restart_btn)
	ImageView restartBtn;
	@ViewById(R.id.timer_init_btn)
	ImageView initBtn;
	@ViewById(R.id.timer_stop_layout)
	LinearLayout stopLayout;

	int hour;
	int min;
	int sec;
	
	MyTimerTask timerTask;
	
	public static boolean isTimerProgress;

	@AfterViews
	void init() {
		clearFragmentStack();
	}
	
	private void clearFragmentStack() {
		if ( getFragmentManager().getBackStackEntryCount() > 0 ) {
			for ( int i = 0; i < getFragmentManager().getBackStackEntryCount(); i ++ )
				getFragmentManager().popBackStack();
		}		
	}


	@Click(R.id.timer_start_btn)
	void startTimer() {
		if (hourEt.getText().length() != 0)
			hour = Integer.parseInt(hourEt.getText().toString());
		else
			hour = 0;
		if (minEt.getText().length() != 0)
			min = Integer.parseInt(minEt.getText().toString());
		else
			min = 0;
		if (secEt.getText().length() != 0)
			sec = Integer.parseInt(secEt.getText().toString());
		else
			sec = 0;

		if ( hour > 0 || min > 0 || sec > 0 ) {
			startBtn.setVisibility(View.GONE);
			pauseBtn.setVisibility(View.VISIBLE);

			setTimer();
			
			isTimerProgress = true;
		}
	}

	private void setTimer() {
		Timer timer = new Timer();
		timerTask = new MyTimerTask();
		timer.schedule(timerTask, 1000, 1000);

	}

	@Click(R.id.timer_pause_btn)
	void pauseTimer() {
		isTimerProgress = false;
		timerTask.cancel();
		pauseBtn.setVisibility(View.GONE);
		stopLayout.setVisibility(View.VISIBLE);
	}

	@Click(R.id.timer_init_btn)
	void initTimer() {
		hour = 0;
		min = 0;
		sec = 0;
		hourEt.setText("");
		minEt.setText("");
		secEt.setText("");

		startBtn.setVisibility(View.VISIBLE);
		pauseBtn.setVisibility(View.GONE);
		stopLayout.setVisibility(View.GONE);
	}

	@Click(R.id.timer_restart_btn)
	void restartTimer() {
		isTimerProgress = true;
		Timer timer = new Timer();
		timerTask = new MyTimerTask();
		timer.schedule(timerTask, 0, 1000);
		
		stopLayout.setVisibility(View.GONE);
		pauseBtn.setVisibility(View.VISIBLE);
	}

	class MyTimerTask extends TimerTask {

		@Override
		public void run() {
			changeTimerView();
		}
	}

	@UiThread
	void changeTimerView() {
	
		sec--;

		if (sec == -1) {
			if ( min > 0 || hour > 0 ) {
				sec = 59;
				min--;
			}
		}

		if (min == -1) {
			if (hour > 0) {
				min = 59;
				hour--;
			}
		}

		if (hour >= 0 && hour < 10)
			hourEt.setText("0" + hour);
		else
			hourEt.setText(hour + "");
		if (min >= 0 && min < 10)
			minEt.setText("0" + min);
		else
			minEt.setText(min + "");
		if (sec >= 0 && sec < 10)
			secEt.setText("0" + sec);
		else
			secEt.setText(sec + "");

		if (hour == 0 && min == 0 && sec == 0) {
			timerTask.cancel();
			setTimerOff();
		}
		Log.i("TimerFragment", hour + ", " + min + ", " + sec);
	}

	private void setTimerOff() {
		initTimer();
		
		final TimerDialog dialog = new TimerDialog(getActivity());
		dialog.show();
		
		dialog.setOnTimerDialogListener(new OnTimerDialogClickListener() {
			
			@Override
			public void onDialogClick(View v) {
				if ( v.getId() == dialog.getMainBtnId() ) {
					Log.i("TimerFragment", "main");
					getFragmentManager().beginTransaction().replace(R.id.container, new TimelineFragment_()).commit();
					HandyActivity.titleTv.setText("타임라인");
					dialog.dismiss();
				} else if ( v.getId() == dialog.getNextBtnId() ) {
					Log.i("TimerFragment", "next");
					SubjectFragment studyFragment = new SubjectFragment_();
					getFragmentManager().beginTransaction().replace(R.id.container, studyFragment).commit();
					dialog.dismiss();
				}
			}
		});
	}
}
