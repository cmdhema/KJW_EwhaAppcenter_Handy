package kr.ewhapp.kjw.handy.timeline;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class TimelineAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<TimelineModel> list;
	
	public TimelineAdapter(Context context, ArrayList<TimelineModel> list){
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View v, ViewGroup arg2) {
		TimelineView view = null;
		
		if ( v == null ) 
			view = new TimelineView(context);
		else
			view = (TimelineView) v;
		
		view.setView(list.get(position));
			
		return view;
	}

	
}
