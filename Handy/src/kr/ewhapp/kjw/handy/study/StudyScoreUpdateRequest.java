package kr.ewhapp.kjw.handy.study;

import android.database.sqlite.SQLiteDatabase;
import kr.ewhapp.kjw.handy.db.DBScheme;
import kr.kau.kjw.db.EditDBRequest;

public class StudyScoreUpdateRequest extends EditDBRequest{

	StudyModel model;
	
	public StudyScoreUpdateRequest(StudyModel model) {
		this.model = model;
	}

	@Override
	public boolean onExecuteSQL(SQLiteDatabase database) {
		
		database.execSQL(DBScheme.updateStudyScore(model));
		return true;
	}

}
