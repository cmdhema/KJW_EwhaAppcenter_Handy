package kr.ewhapp.kjw.handy.timer;

import kr.ewhapp.handy.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class TimerDialog extends Dialog implements android.view.View.OnClickListener {

	private ImageView mainBtn;
	private ImageView nextBtn;

	private OnTimerDialogClickListener listener;

	public TimerDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_timer_dialog);

		mainBtn = (ImageView) findViewById(R.id.timer_dialog_gomain_iv);
		nextBtn = (ImageView) findViewById(R.id.timer_dialog_next_iv);

		mainBtn.setOnClickListener(this);
		nextBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		listener.onDialogClick(v);
	}

	public interface OnTimerDialogClickListener {
		public void onDialogClick(View v);
	}

	public void setOnTimerDialogListener(OnTimerDialogClickListener listener) {
		this.listener = listener;
	}
	
	public int getMainBtnId() {
		return mainBtn.getId();
	}
	
	public int getNextBtnId() {
		return nextBtn.getId();
	}
	
}
