package kr.ewhapp.kjw.handy.timeline;

import java.util.ArrayList;

import kr.ewhapp.handy.R;
import kr.ewhapp.kjw.handy.HandyActivity;
import kr.kau.kjw.db.DBError;
import kr.kau.kjw.db.DBModule;
import kr.kau.kjw.db.GetDBRequest;
import kr.kau.kjw.db.GetDBRequest.OnGetDBRequestListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.app.Fragment;
import android.widget.ListView;

@EFragment(R.layout.layout_timeline)
public class TimelineFragment extends Fragment implements OnGetDBRequestListener<ArrayList<TimelineModel>> {

	@ViewById(R.id.timeline_listview)
	ListView listView;

	TimelineAdapter adapter;

	@AfterViews
	void init() {
		clearFragmentStack();
		HandyActivity.titleTv.setText("타임라인");
		TimelineRequest request = new TimelineRequest(new ArrayList<TimelineModel>(), "");
		request.setOnGetDBRequest(this);
		DBModule.getInstance().enqueue(request);
	}

	private void clearFragmentStack() {
		if (getFragmentManager().getBackStackEntryCount() > 0) {
			for (int i = 0; i < getFragmentManager().getBackStackEntryCount(); i++)
				getFragmentManager().popBackStack();
		}
	}

	@Override
	public void onGetSuccess(GetDBRequest<ArrayList<TimelineModel>> request) {
		adapter = new TimelineAdapter(getActivity(), request.getResult());
		listView.setAdapter(adapter);
	}

	@Override
	public void onGetError(DBError error) {

	}
}
