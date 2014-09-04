package kr.ewhapp.kjw.handy.study;

import kr.ewhapp.handy.R;
import kr.ewhapp.kjw.handy.study.ScoreCheckDialog.OnDialogClickCallback;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ImageSelectDialog extends Dialog implements android.view.View.OnClickListener {

	private ImageView continueBtn;
	private ImageView againBtn;
	private ImageView resultBtn;
	private OnDialogClickCallback listener;

	private ImageViewId ids;
	
	public ImageSelectDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_image_dialog);
		
		continueBtn = (ImageView) findViewById(R.id.photodialog_continue_btn);
		againBtn = (ImageView) findViewById(R.id.photodialog_again_btn);
		resultBtn = (ImageView) findViewById(R.id.photodialog_result_btn);

		continueBtn.setOnClickListener(this);
		againBtn.setOnClickListener(this);
		resultBtn.setOnClickListener(this);
		
		ids = new ImageViewId();
	
	}

	
	@Override
	public void onClick(View v) {

		listener.onDialogClick(v, ids);
	}
	
	public class ImageViewId {
		int continueBtnId = continueBtn.getId();
		int againBtnId = againBtn.getId();
		int resultBtnId = resultBtn.getId();
	}
	
	public void setOnDialogClickListener ( OnDialogClickCallback listener ) {
		this.listener = listener;
	}
	
	
}
