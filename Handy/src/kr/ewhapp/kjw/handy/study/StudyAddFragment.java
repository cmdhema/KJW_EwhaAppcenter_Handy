package kr.ewhapp.kjw.handy.study;

import kr.ewhapp.handy.R;
import kr.kau.kjw.db.DBError;
import kr.kau.kjw.db.DBModule;
import kr.kau.kjw.db.EditDBRequest;
import kr.kau.kjw.db.EditDBRequest.OnEditDBProcessListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

@EFragment(R.layout.layout_study_add)
public class StudyAddFragment extends Fragment {

	@ViewById(R.id.study_add_et)
	EditText studyNameEt;
	@ViewById(R.id.study_add_btn)
	ImageView addBtn;
	
	String flag;
	
	@AfterViews
	void init() {

	}
	
	@Click(R.id.study_add_btn)
	void insertStudy() {
		StudyModel model = new StudyModel();
		model.name = studyNameEt.getText().toString();
		
		if ( getArguments().getString("Subject") != null)
			model.subject = getArguments().getString("Subject");
		
		StudyInsertRequest request = new StudyInsertRequest(model, getArguments().getString("flag"));
		DBModule.getInstance().enqueue(request);
		request.setOnEditDBRequestListener(new OnEditDBProcessListener() {
			
			@Override
			public void onEditSuccess(EditDBRequest request) {
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				if ( getArguments().getString("flag").equals("Subject")) {
					transaction.replace(R.id.container, new SubjectFragment_());
				}
				if ( getArguments().getString("flag").equals("Study")) {
					Bundle bundle = new Bundle();
					bundle.putString("subject", getArguments().getString("Subject"));
					StudyFragment fragment = new StudyFragment_();
					fragment.setArguments(bundle);
					transaction.replace(R.id.container, fragment);
				}
				transaction.addToBackStack(null);
				transaction.commit();
			}
			
			@Override
			public void onEditError(DBError error) {
				Toast.makeText(getActivity(), "이름이 중복됩니다. 다시입력해주세요", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
//	private void clearFragmentStack() {
//		if (getFragmentManager().getBackStackEntryCount() > 0) {
//			for (int i = 0; i < getFragmentManager().getBackStackEntryCount()-1; i++)
//				getFragmentManager().popBackStack();
//		}
//	}
	
}
