package kr.ewhapp.kjw.handy.study;

import kr.ewhapp.handy.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ScoreCheckDialog extends Dialog implements android.view.View.OnClickListener {

	private ImageView cameraBtn;
	private ImageView galleryBtn;
	private ImageView inputBtn;
	private OnDialogClickCallback listener;

	private ImageViewId ids;
	
	public ScoreCheckDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_scorecheck_dialog);

		cameraBtn = (ImageView) findViewById(R.id.score_check_camera_btn);
		galleryBtn = (ImageView) findViewById(R.id.score_check_gallery_btn);
		inputBtn = (ImageView) findViewById(R.id.score_check_input_btn);

		cameraBtn.setOnClickListener(this);
		galleryBtn.setOnClickListener(this);
		inputBtn.setOnClickListener(this);
		
		ids = new ImageViewId();
	}

	@Override
	public void onClick(View v) {

		listener.onDialogClick(v, ids);
	}
	
	public interface OnDialogClickCallback {
		public void onDialogClick(View view, Object ids);
	}
	
	public class ImageViewId {
		int cameraBtnId = cameraBtn.getId();
		int galleryBtnId = galleryBtn.getId();
		int inputBtnId = inputBtn.getId();
	}
	
	public void setOnDialogClickListener ( OnDialogClickCallback listener ) {
		this.listener = listener;
	}

}
