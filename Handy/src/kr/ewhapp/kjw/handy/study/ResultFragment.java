package kr.ewhapp.kjw.handy.study;


import java.sql.Date;

import kr.ewhapp.handy.R;
import kr.ewhapp.kjw.handy.timeline.TimelineFragment_;
import kr.ewhapp.lar.handy.imageprocessing.ImageProcessing;
import kr.ewhapp.lar.handy.imageprocessing.ImageProcessing.ImageProcessingListener;
import kr.kau.kjw.db.DBError;
import kr.kau.kjw.db.DBModule;
import kr.kau.kjw.db.EditDBRequest;
import kr.kau.kjw.db.EditDBRequest.OnEditDBProcessListener;
import kr.kau.kjw.db.GetDBRequest;
import kr.kau.kjw.db.GetDBRequest.OnGetDBRequestListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.app.Fragment;
import android.text.format.DateFormat;
import android.widget.TextView;

@EFragment(R.layout.layout_result)
public class ResultFragment extends Fragment implements ImageProcessingListener {

	@ViewById(R.id.subject_count_tv)
	TextView subjectCountTv;
	@ViewById(R.id.subject_name_tv)
	TextView subjectNameTv;
	@ViewById(R.id.study_name_tv)
	TextView studyNameTv;
	@ViewById(R.id.study_count_tv)
	TextView studyCountTv;

	@FragmentArg
	String subject;
	@FragmentArg
	String study;

	int subjectTotalCount;
	int subjectRightCount;

	StudyModel originalStudyModel;

	@AfterViews
	void init() {
		ImageProcessing process = new ImageProcessing();
		process.setImageProcessListener(this);
		process.execute();

		getOriginalStudyInfo();
	}

	private void getOriginalStudyInfo() {
		originalStudyModel = new StudyModel();
		originalStudyModel.name = study;
		originalStudyModel.subject = subject;
		StudyModelRequest request = new StudyModelRequest(originalStudyModel, "");
		DBModule.getInstance().enqueue(request);
		request.setOnGetDBRequest(new OnGetDBRequestListener<StudyModel>() {

			@Override
			public void onGetSuccess(GetDBRequest<StudyModel> request) {
				subjectTotalCount = request.getResult().subjectTotalCount;
				subjectRightCount = request.getResult().subjectRightCount;
			}

			@Override
			public void onGetError(DBError error) {

			}
		});

	}

	private String getGrade(int right, int total) {
		if ((right / total) * 100 >= 90)
			return "A";
		else if ((right / total) * 100 >= 80)
			return "B";
		else if ((right / total) * 100 >= 70)
			return "C";
		else if ((right / total) * 100 >= 60)
			return "D";
		else
			return "E";
	}

	@Override
	public void onImageProcessingSuccess(StudyModel data) {
		setStudyScore(data);
		updateScore(data);
	}

	@UiThread
	void setStudyScore(StudyModel data) {
		studyNameTv.setText(study);
		studyCountTv.setText(data.rightCount + " / " + data.totalCount);
	}

	@UiThread
	protected void setTotalScore(StudyModel data) {
		
		subjectNameTv.setText(subject);
		subjectCountTv.setText(data.subjectRightCount + " / " + data.subjectTotalCount);
	}

	@Override
	public void onError() {

	}

	private void updateScore(final StudyModel updateModel) {
		float ratio = ((float)updateModel.rightCount/(float)updateModel.totalCount) * 100;
		updateModel.name = study;
		updateModel.subject = subject;
		String grade = getGrade(updateModel.rightCount, updateModel.totalCount);
		updateModel.grade = grade;
		updateModel.score = (float) (Math.round( ratio * 10.0 )/ 10.0);
		updateModel.date = getDate(System.currentTimeMillis());
		StudyScoreUpdateRequest request = new StudyScoreUpdateRequest(updateModel);
		DBModule.getInstance().enqueue(request);
		request.setOnEditDBRequestListener(new OnEditDBProcessListener() {

			@Override
			public void onEditSuccess(EditDBRequest request) {

			}

			@Override
			public void onEditError(DBError error) {

			}
		});
		updateModel.subjectRightCount = updateModel.rightCount + subjectRightCount;
		updateModel.subjectTotalCount = updateModel.totalCount + subjectTotalCount;
		SubjectScoreInsertRequest subjectInsertRequest = new SubjectScoreInsertRequest(updateModel);
		DBModule.getInstance().enqueue(subjectInsertRequest);
		subjectInsertRequest.setOnEditDBRequestListener(new OnEditDBProcessListener() {

			@Override
			public void onEditSuccess(EditDBRequest request) {
				setTotalScore(updateModel);
			}

			@Override
			public void onEditError(DBError error) {

			}
		});
	}

	private String getDate(long currentTimeMillis) {
		return DateFormat.format("yy-MM-dd", new Date(currentTimeMillis)).toString();
	}

	@Click(R.id.result_main_btn)
	void replaceTimeline() {
		getFragmentManager().beginTransaction().replace(R.id.container, new TimelineFragment_()).commit();
	}

	@Click(R.id.result_ok_btn)
	void replaceSubject() {
		getFragmentManager().beginTransaction().replace(R.id.container, new SubjectFragment_()).commit();
	}
}
