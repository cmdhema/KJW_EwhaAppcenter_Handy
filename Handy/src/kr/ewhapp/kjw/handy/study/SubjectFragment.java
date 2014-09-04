package kr.ewhapp.kjw.handy.study;

import java.util.ArrayList;
import java.util.Collections;

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
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

@EFragment(R.layout.layout_study)
public class SubjectFragment extends Fragment implements OnItemClickListener{

	@ViewById(R.id.study_grid)
	GridView gridView;
	
	ArrayList<StudyModel> studyList;
	StudyGridAdapter adapter;
	
	@AfterViews
	void init() {

		HandyActivity.titleTv.setText("½ºÅÍµð");
		
		gridView.setOnItemClickListener(this);
	
		clearFragmentStack();
		Log.i("SubjectFragment", "onResume");
		StudyGetRequest request = new StudyGetRequest(new ArrayList<StudyModel>(), "Subject");
		DBModule.getInstance().enqueue(request);
		request.setOnGetDBRequest(new OnGetDBRequestListener<ArrayList<StudyModel>>() {

			@Override
			public void onGetSuccess(GetDBRequest<ArrayList<StudyModel>> request) {
				StudyModel header = new StudyModel();
				header.name = "header";
				studyList = request.getResult();
				Collections.reverse(request.getResult());
				studyList.add(0, header);
				adapter = new StudyGridAdapter(getActivity(), studyList);
				gridView.setAdapter(adapter);
			}

			@Override
			public void onGetError(DBError error) {
				
			}
		});
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		
		if ( position == 0 ) {
			StudyAddFragment fragment = new StudyAddFragment_();
			Bundle bundle = new Bundle();
			bundle.putString("flag", "Subject");
			fragment.setArguments(bundle);
			transaction.replace(R.id.container, fragment);
		} else {
			StudyFragment fragment = new StudyFragment_();
			Bundle bundle = new Bundle();
			bundle.putString("subject", studyList.get(position).name);
			fragment.setArguments(bundle);
			transaction.replace(R.id.container, fragment);
		}
		transaction.addToBackStack(null);
		transaction.commit();
	}

	private void clearFragmentStack() {
		if (getFragmentManager().getBackStackEntryCount() > 0) {
			for (int i = 0; i < getFragmentManager().getBackStackEntryCount(); i++)
				getFragmentManager().popBackStack();
		}
	}

}
