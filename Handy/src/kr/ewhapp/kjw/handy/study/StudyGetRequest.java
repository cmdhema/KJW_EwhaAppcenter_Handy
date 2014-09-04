package kr.ewhapp.kjw.handy.study;

import java.util.ArrayList;

import kr.ewhapp.kjw.handy.db.DBScheme;
import kr.kau.kjw.db.GetDBRequest;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class StudyGetRequest extends GetDBRequest<ArrayList<StudyModel>>{

	private String flag;
	private String subject;
	public StudyGetRequest(ArrayList<StudyModel> list, String flag) {
		super(list, flag);
		this.flag = flag;
	}

	public StudyGetRequest(ArrayList<StudyModel> list, String flag, String subject) {
		super(list, flag);
		this.flag = flag;
		this.subject = subject;
	}
	
	@Override
	public boolean onExecuteQuery(SQLiteDatabase database, String query, ArrayList<StudyModel> result) {
		
		Cursor cursor = null;
		
		if ( flag.equals("Study")) {
			cursor = database.rawQuery(DBScheme.selectSubjectItem(subject), null);

			while ( cursor.moveToNext() ) {
				StudyModel model = new StudyModel();
				model.name = cursor.getString(cursor.getColumnIndex("name"));
				result.add(model);
			}
		} else if ( flag.equals("Subject")) {
			cursor = database.rawQuery(DBScheme.selectSubject(), null);

			while ( cursor.moveToNext() ) {
				StudyModel model = new StudyModel();
				model.name = cursor.getString(cursor.getColumnIndex("subject"));
				result.add(model);
			}
		}
		
		return true;
	}

}
