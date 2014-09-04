package kr.ewhapp.kjw.handy.study;

import kr.ewhapp.handy.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class StudyView extends FrameLayout {

	private TextView titleTv;
	
	public StudyView(Context context) {
		super(context);
	    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.study_item, this);
		
		titleTv = (TextView) findViewById(R.id.study_item_tv);
	}

	protected void setItem(int position, StudyModel data) {
		if ( data.name.equals("header") ) {
			RelativeLayout parent = (RelativeLayout) titleTv.getParent();
			parent.setBackgroundResource(R.drawable.study_add_grid);
		} else {
			titleTv.setText(data.name);
		}
	}
	
}
