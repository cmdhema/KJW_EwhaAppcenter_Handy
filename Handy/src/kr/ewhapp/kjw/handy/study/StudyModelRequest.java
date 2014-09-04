package kr.ewhapp.kjw.handy.study;

import kr.ewhapp.kjw.handy.db.DBScheme;
import kr.kau.kjw.db.GetDBRequest;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class StudyModelRequest extends GetDBRequest<StudyModel>{

	StudyModel subject;
	public StudyModelRequest(StudyModel list, String flag) {
		super(list, flag);
		
		subject = list;
	}

	@Override
	public boolean onExecuteQuery(SQLiteDatabase database, String query, StudyModel result) {
		
		Cursor cursor = null;
		Cursor cursor2 = null;
		cursor = database.rawQuery(DBScheme.selectStudy(subject), null);
		cursor2 = database.rawQuery(DBScheme.selectScore(subject), null);
		while ( cursor.moveToNext() ) {
			result.totalCount = cursor.getInt(cursor.getColumnIndex("count_all"));
		}

		while ( cursor2.moveToNext() ) {
			result.subjectRightCount += cursor2.getInt(cursor2.getColumnIndex("right_count"));
			result.subjectTotalCount += cursor2.getInt(cursor2.getColumnIndex("total_count"));
		}
		
		return true;
	}
	
	

}
