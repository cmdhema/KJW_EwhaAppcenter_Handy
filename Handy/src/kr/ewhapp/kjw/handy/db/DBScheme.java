package kr.ewhapp.kjw.handy.db;

import kr.ewhapp.kjw.handy.study.StudyModel;

public class DBScheme {

	public static String createSubjectNameTable() {
		return "create table subject( subject text, primary key (subject) );";
	}
	
	public static String createSubjectScoreTable() {
		return "create table subject_score( subject text, right_count integer, total_count integer );";
	}
	
	public static String createStudyTable() {
		return "create table study ( idx integer, subject text, name text, date text, count_all integer, count_right integer, score real, grade text, primary key (idx, name) );";
	}

	public static String selectTimelineData() {
		return "select subject, name, date, score, grade from study;";
	}
	
	public static String selectSubject() {
		return "select subject from subject;";
	}
	
	public static String selectSubjectItem(String subject) {
		String sql = "select name from study where subject='%s';";
		return String.format(sql, subject);
	}
	
	public static String selectStudy(StudyModel subject) {
		String sql = "select * from study where subject='%s' and name='%s';";
		return String.format(sql, subject.subject, subject.name);
	}
	
	public static String selectScore(StudyModel subject) {
		String sql = "select * from subject_score where subject='%s'";
		return String.format(sql, subject.subject);
	}
	
	public static String insertSubject(String subject) {
		String sql = "insert into subject(subject) values ('%s');";
		return String.format(sql, subject);
	}
	
	public static String insertStudy(String subject, String study) {
		String sql = "insert into study(subject, name) values ('%s','%s');";
		return String.format(sql, subject, study);
	}
	
	public static String insertSubjectScore(StudyModel subject) {
		String sql = "insert into subject_score (subject, right_count, total_count) values ('%s', %d, %d);";
		return String.format(sql, subject.subject, subject.subjectRightCount, subject.subjectTotalCount );
	}
	
	public static String updateStudyScore(StudyModel study) {
		String sql = "update study set count_all = %d, count_right = %d, grade = '%s', score = %f, date = '%s' where subject = '%s' and name = '%s';";
		return String.format(sql, study.totalCount, study.rightCount, study.grade, study.score, study.date, study.subject, study.name);
	}
	
}
