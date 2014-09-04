package kr.ewhapp.kjw.handy.study;

import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import kr.ewhapp.kjw.handy.db.DBScheme;
import kr.kau.kjw.db.EditDBRequest;

public class StudyInsertRequest extends EditDBRequest{

	private StudyModel model;
	private String flag;
	
	public StudyInsertRequest(StudyModel model, String flag) {
		this.model = model;
		this.flag = flag;
	}

	@Override
	public boolean onExecuteSQL(SQLiteDatabase database) {
		
		try {
			if (flag.equals("Subject"))
				database.execSQL(DBScheme.insertSubject(model.name));
			else if ( flag.equals("Study"))
				database.execSQL(DBScheme.insertStudy(model.subject, model.name));
			return true;
		} catch (SQLiteConstraintException e) { 
			return false;
		}

	}

}
