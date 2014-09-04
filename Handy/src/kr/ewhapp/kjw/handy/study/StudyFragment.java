package kr.ewhapp.kjw.handy.study;

import java.util.ArrayList;
import java.util.Collections;

import kr.ewhapp.handy.R;
import kr.ewhapp.kjw.handy.HandyActivity;
import kr.ewhapp.kjw.handy.study.ScoreCheckDialog.OnDialogClickCallback;
import kr.kau.kjw.db.DBError;
import kr.kau.kjw.db.DBModule;
import kr.kau.kjw.db.GetDBRequest;
import kr.kau.kjw.db.GetDBRequest.OnGetDBRequestListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

@EFragment(R.layout.layout_study)
public class StudyFragment extends Fragment implements OnItemClickListener {

	ScoreCheckDialog dialog;

	@ViewById(R.id.study_grid)
	GridView gridView;

	ArrayList<StudyModel> studyList;
	StudyGridAdapter adapter;

	final int PICK_FROM_CAMERA = 0;
	final int PICK_FROM_ALBUM = 1;

	String subject;
	String study;

	private Uri fileUri;

	@AfterViews
	void init() {
		Log.i("StudyFragment", "init");

		gridView.setOnItemClickListener(this);

		dialog = new ScoreCheckDialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setOnDialogClickListener(dialogClick);

		subject = getArguments().getString("subject");

		HandyActivity.titleTv.setText(subject);

		StudyGetRequest request = new StudyGetRequest(new ArrayList<StudyModel>(), "Study", subject);
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

	OnDialogClickCallback dialogClick = new OnDialogClickCallback() {

		@Override
		public void onDialogClick(View view, Object ids) {

			ScoreCheckDialog.ImageViewId id = (ScoreCheckDialog.ImageViewId) ids;
			if (view.getId() == id.cameraBtnId) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, PICK_FROM_CAMERA);
			} else if (view.getId() == id.galleryBtnId) {
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
				startActivityForResult(intent, PICK_FROM_ALBUM);
			} else if (view.getId() == id.inputBtnId) {

			}
			dialog.dismiss();
		}
	};

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {

		FragmentTransaction transaction = getFragmentManager().beginTransaction();

		if (position == 0) {
			StudyAddFragment fragment = new StudyAddFragment_();
			Bundle bundle = new Bundle();
			bundle.putString("flag", "Study");
			bundle.putString("Subject", subject);
			fragment.setArguments(bundle);
			transaction.replace(R.id.container, fragment);
			transaction.addToBackStack(null);
			transaction.commit();
		} else {
			study = studyList.get(position).name;
			dialog.show();
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		String flag = null;

		getActivity();
		if (resultCode != Activity.RESULT_OK)
			return;

		switch (requestCode) {
		case PICK_FROM_ALBUM:
			fileUri = data.getData();
			flag = "Album";
		case PICK_FROM_CAMERA:
			if (fileUri != null) {
				if (flag == null)
					flag = "Camera";
				ImageSelectFragment fragment = new ImageSelectFragment_();
				Bundle bundle = new Bundle();
				bundle.putString("uri", fileUri.toString());
				bundle.putString("subject", subject);
				bundle.putString("study", study);
				bundle.putString("flag", flag);
				fragment.setArguments(bundle);
				getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
				break;
			}

		}
	}

}
