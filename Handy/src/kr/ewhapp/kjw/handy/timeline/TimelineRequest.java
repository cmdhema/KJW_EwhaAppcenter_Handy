package kr.ewhapp.kjw.handy.timeline;

import java.util.ArrayList;
import java.util.Collections;

import kr.ewhapp.kjw.handy.db.DBScheme;
import kr.kau.kjw.db.GetDBRequest;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TimelineRequest extends GetDBRequest<ArrayList<TimelineModel>>{

	public TimelineRequest(ArrayList<TimelineModel> list, String flag) {
		super(list, flag);

	}

	@Override
	public boolean onExecuteQuery(SQLiteDatabase database, String query, ArrayList<TimelineModel> result) {
		
		Cursor cursor = database.rawQuery(DBScheme.selectTimelineData(), null);
		
		while (cursor.moveToNext()) {
			TimelineModel model = new TimelineModel();
			model.date = cursor.getString(cursor.getColumnIndex("date"));
			model.score = cursor.getFloat(cursor.getColumnIndex("score"));
			model.name = cursor.getString(cursor.getColumnIndex("name"));
			model.subject = cursor.getString(cursor.getColumnIndex("subject"));
			model.grade = cursor.getString(cursor.getColumnIndex("grade"));
			result.add(model);
			
		}
		
		Collections.reverse(result);
		return true;
	}

}
