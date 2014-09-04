package kr.ewhapp.kjw.handy.timeline;

import kr.ewhapp.handy.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class TimelineView extends FrameLayout{

	private ImageView stickerIv;
	private TextView subjectTv;
	private TextView dateTv;
	private TextView scoreTv;
	
	public TimelineView(Context context) {
		super(context);

		LayoutInflater.from(context).inflate(R.layout.timeline_item, this);
		
		stickerIv = (ImageView) findViewById(R.id.sticker_iv);
		subjectTv = (TextView) findViewById(R.id.subject_tv);
		dateTv = (TextView) findViewById(R.id.date_tv);
		scoreTv = (TextView) findViewById(R.id.score_tv);
		
	}

	protected void setView(TimelineModel data) {
		//TODO
//		stickerIv.setImageResource(resId)
		if ( data.grade == null )
			return;
		
		if ( data.grade.equals("A"))
			stickerIv.setImageResource(R.drawable.timeline_sticker_a);
		if ( data.grade.equals("B"))
			stickerIv.setImageResource(R.drawable.timeline_sticker_b);
		if ( data.grade.equals("C"))
			stickerIv.setImageResource(R.drawable.timeline_sticker_c);
		if ( data.grade.equals("D"))
			stickerIv.setImageResource(R.drawable.timeline_sticker_d);
		
		subjectTv.setText(data.subject+"_" + data.name);
		dateTv.setText(data.date);
		scoreTv.setText("Á¤´ä·ü : " + data.score+"%");
		
	}
	
}
