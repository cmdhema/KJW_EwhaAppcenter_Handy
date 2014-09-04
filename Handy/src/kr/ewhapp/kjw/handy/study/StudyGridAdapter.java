package kr.ewhapp.kjw.handy.study;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class StudyGridAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<StudyModel> dataList;

	public StudyGridAdapter(Context context, ArrayList<StudyModel> data) {
		this.context = context;
		this.dataList = data;
	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		StudyView view;

		view = new StudyView(context);

		view.setItem(position, dataList.get(position));

		return view;

	}

}
